package com.example.metroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    private Spinner fromSpinner,toSpinner;
    private Button goButton,showAllStationsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fromSpinner = findViewById(R.id.fromStationSpinner);
        toSpinner = findViewById(R.id.toStationSpinner);
        goButton = findViewById(R.id.fromToButton);
        showAllStationsButton = findViewById(R.id.showAllStationsButton);

        String[] fromArraySpinner = new String[] {
                "المرج", "المطرية", "غمرة", "عرابي", "السادات", "الزهراء"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, fromArraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromSpinner.setAdapter(adapter);

        toSpinner.setAdapter(adapter);

        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String fromStation =  fromSpinner.getSelectedItem().toString();
               String toStation = toSpinner.getSelectedItem().toString();
                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("flag", false);
                intent.putExtra("fromStation", fromStation);
                intent.putExtra("toStation",toStation);
                startActivity(intent);
            }
        });

        showAllStationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                intent.putExtra("flag", true);
                startActivity(intent);

            }
        });

    }
}
