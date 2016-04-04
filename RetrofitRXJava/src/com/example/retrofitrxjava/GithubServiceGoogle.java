package com.example.retrofitrxjava;

import com.example.modellingclass.Response;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface GithubServiceGoogle {
	// String SERVICE_GOOGLE_MATRIX_URL =
	// "https://maps.googleapis.com/maps/api/distancematrix/json?origins=27.667024,85.322474&destinations=27.688041,85.316212&mode=driving";
	String SERVICE_GOOGLE_MATRIX_URL = "https://maps.googleapis.com";

	// @GET("/users/{logindra}")
	// @GET("/maps/api/distancematrix/json?origins={sourcelat},{sourcelong}&destinations={destlat},{destlong}&mode=driving")
	@GET("/maps/api/distancematrix/json")
	Observable<Response> getDistanceMatrix(@Query("origins") String origins,
			@Query("destinations") String destinations,
			@Query("mode") String mode);
}
