package com.example.quakesdemo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TemplateAdapter extends ArrayAdapter<DataContainer> {
    public TemplateAdapter(Activity Contex, ArrayList<DataContainer> data) {
        super(Contex, 0, data);
    }

    //Override the getView method


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListViewItem = convertView;
        if (ListViewItem == null) {
            ListViewItem = LayoutInflater.from(getContext()).inflate(R.layout.template, parent, false);
        }
        DataContainer currentData = getItem(position);

        //get the data from container and put it in the sample

        TextView MagStrength = ListViewItem.findViewById(R.id.magStrength);
        MagStrength.setText(currentData.getMagStrength());

        TextView PlaceWhereItHappen = ListViewItem.findViewById(R.id.PlaceWhereOccurance);
        PlaceWhereItHappen.setText(currentData.getPlace());

        //convert the long data to simple date of date
        Date ObjectDate = new Date(currentData.getTime());
        TextView DateOfOccurance = ListViewItem.findViewById(R.id.dateFullTime);
        DateOfOccurance.setText(FormattedDate(ObjectDate));
        //we done with this problem for now


        return ListViewItem;
    }

    //section of special function to perform some operations of the time
    public String FormattedDate(Date data) {
        SimpleDateFormat formattedDate = new SimpleDateFormat("LLL dd ,yyy 'at' h:mm a");
        return formattedDate.format(data);
    }

}
