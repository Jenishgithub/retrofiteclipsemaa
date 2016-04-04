package com.example.retrofitrxjava;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GithubService {
	String SERVICE_ENDPOINT = "https://api.github.com";
	String SERVICE_GOOGLE_MATRIX_URL = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=27.667024,85.322474&destinations=27.688041,85.316212&mode=driving";

	@GET("/users/{logindra}")
	Observable<Github> getUser(@Path("logindra") String login);

}
