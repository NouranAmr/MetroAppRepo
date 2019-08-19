package com.example.metroapp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/metro.json")
    Call<MetroStations> getAllStations();
}