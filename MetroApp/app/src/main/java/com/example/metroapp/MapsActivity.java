package com.example.metroapp;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MetroStations metroStations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<MetroStations> call = service.getAllStations();
        call.enqueue(new Callback<MetroStations>() {
            @Override
            public void onResponse(Call<MetroStations> call, Response<MetroStations> response) {
                metroStations = response.body();
                List<Row> rows = metroStations.getRows();

                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(Color.RED);

                for (Row row : rows){
                    String[] latLong = row.getDestinationLongLat().get(0).split(",");
                    LatLng stationLatLng = new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]));
                    polylineOptions.add(stationLatLng);
                    MarkerOptions marker = new MarkerOptions().position(stationLatLng).title(row.getTitle());

                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.metro));
                    mMap.addMarker(marker);
                    
                }

                mMap.addPolyline(polylineOptions);
                LatLng cairoLatLng = new LatLng(30.06263,31.24967 );
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cairoLatLng,11.4f) );
                // Add a marker in Sydney and move the camera


            }

            @Override
            public void onFailure(Call<MetroStations> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
            }
        });

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;



    }


}
