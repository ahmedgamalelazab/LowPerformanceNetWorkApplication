package com.example.quakesdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class quakes extends AppCompatActivity {

    private static final String TAG = quakes.class.getSimpleName();
    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-12-11&endtime=2019-12-12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quakes);
        TsunamiAsyncTask pleasWork = new TsunamiAsyncTask();
        pleasWork.execute();

    }//end of quakes function

    private ArrayList<DataContainer> exctractDataQuakes(String jsonResponse) {

        ArrayList<DataContainer> arr = new ArrayList<>();

        try {

            JSONObject ROOT = new JSONObject(jsonResponse);
            JSONArray RootArray = ROOT.getJSONArray("features");

            for (int i = 0; i < RootArray.length(); i++) {
                JSONObject innerObject = RootArray.getJSONObject(i);
                JSONObject properties = innerObject.getJSONObject("properties");

                long Time = properties.getLong("time");
                String mag = properties.getString("mag");
                String place = properties.getString("place");
                String URLL = properties.getString("url");
                arr.add(new DataContainer(mag, place, Time, URLL));

            }


        } catch (JSONException e) {
            Log.e(TAG, "error while parsing your fucking json", e);

        }
        return arr;
    }

    private void UpdateUi(String jsonResponse) {

        final ArrayList<DataContainer> quakesData = exctractDataQuakes(jsonResponse);
        ListView quakesList = findViewById(R.id.List);
        TemplateAdapter quakesAdapter = new TemplateAdapter(this, quakesData);
        quakesList.setAdapter(quakesAdapter);
        //NOT LETS handle this shit
        quakesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataContainer currentData = quakesData.get(position);
                //now lets build the click
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentData.getURL()));
                startActivity(intent);
            }
        });

    }

    //making function to update the ui
    private class TsunamiAsyncTask extends AsyncTask<URL, Void, String> {

        //asyncTask is a task working asyncronly without feeling about it and doInBackground will perform the task in the backgrounnd
        @Override
        protected String doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(USGS_REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                // TODO Handle the IOException
                Log.e(TAG, "no response from the server ", e);
            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return jsonResponse;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link TsunamiAsyncTask}).
         */
        @Override
        protected void onPostExecute(String jsonResponse) {
            //pleas fucking work pleas
            UpdateUi(jsonResponse);

        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                }
            } catch (IOException e) {
                // TODO: Handle the exception
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }


        private String readFromStream(InputStream inputStream) throws IOException {
            //string builder is used to deal with mutable data of strings
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

    }


}
