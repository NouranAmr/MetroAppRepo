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
    private String fromStation,toStation;
    private boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        status = getIntent().getBooleanExtra("flag", false);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(!status) {
            fromStation = getIntent().getStringExtra("fromStation");
            toStation = getIntent().getStringExtra("toStation");

            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<MetroStations> call = service.getAllStations();
            call.enqueue(new Callback<MetroStations>() {
                @Override
                public void onResponse(Call<MetroStations> call, Response<MetroStations> response) {
                    metroStations = response.body();
                    List<Row> rows = metroStations.getRows();
                    if (fromStation.equals(toStation)){
                        for (Row row : rows){
                            if(fromStation.equals(row.getTitle())){
                                String[] latLong = row.getDestinationLongLat().get(0).split(",");
                                LatLng stationLatLng = new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]));
                                MarkerOptions marker = new MarkerOptions().position(stationLatLng).title(row.getTitle());
                                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.metro));
                                mMap.addMarker(marker);
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(stationLatLng, 12f));
                                break;
                            }
                        }
                    }else {
                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.color(Color.RED);

                        for (int i = 0; i < rows.size(); i++) {

                            if (rows.get(i).getTitle().equals(fromStation) || rows.get(i).getTitle().equals(toStation)) {
                                String[] latLong = rows.get(i).getDestinationLongLat().get(0).split(",");
                                LatLng stationLatLng = new LatLng(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]));
                                polylineOptions.add(stationLatLng);
                                MarkerOptions marker = new MarkerOptions().position(stationLatLng).title(rows.get(i).getTitle());

                                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.metro));
                                mMap.addMarker(marker);

                                for (int j = i + 1; j < rows.size(); j++) {
                                    if (rows.get(j).getTitle().equals(fromStation) || rows.get(j).getTitle().equals(toStation)) {
                                        String[] latLongJ = rows.get(j).getDestinationLongLat().get(0).split(",");
                                        LatLng stationLatLngJ = new LatLng(Double.parseDouble(latLongJ[0]), Double.parseDouble(latLongJ[1]));
                                        polylineOptions.add(stationLatLngJ);
                                        MarkerOptions markerJ = new MarkerOptions().position(stationLatLngJ).title(rows.get(j).getTitle());

                                        markerJ.icon(BitmapDescriptorFactory.fromResource(R.drawable.metro));
                                        mMap.addMarker(markerJ);
                                        break;
                                    }
                                    String[] latLongJ = rows.get(j).getDestinationLongLat().get(0).split(",");
                                    LatLng stationLatLngJ = new LatLng(Double.parseDouble(latLongJ[0]), Double.parseDouble(latLongJ[1]));
                                    polylineOptions.add(stationLatLngJ);
                                    MarkerOptions markerJ = new MarkerOptions().position(stationLatLngJ).title(rows.get(j).getTitle());

                                    markerJ.icon(BitmapDescriptorFactory.fromResource(R.drawable.metro));
                                    mMap.addMarker(markerJ);
                                }
                                break;
                            }
                        }

                        mMap.addPolyline(polylineOptions);

                        LatLng cairoLatLng = new LatLng(30.06263, 31.24967);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cairoLatLng, 11.4f));
                    }
                }

                @Override
                public void onFailure(Call<MetroStations> call, Throwable t) {
                    Toast.makeText(MapsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_LONG).show();
                }
            });
        }else {
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
