package com.example.quakesdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button QuakesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lets make the button in the main activity do the intent to open the quakes.java
        QuakesButton = findViewById(R.id.Button);

        QuakesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating an intent to open the another activity which btw will display the quakes report
                Intent intent = new Intent(MainActivity.this, quakes.class);
                startActivity(intent);
            }
        });

    }

}
