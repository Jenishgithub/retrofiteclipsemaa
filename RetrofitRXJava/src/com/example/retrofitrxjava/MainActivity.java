package com.example.retrofitrxjava;

import java.util.ArrayList;
import java.util.List;

import com.example.modellingclass.Response;
import com.google.android.gms.maps.model.LatLng;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	List<Github> mItems = new ArrayList<>();
	TextView tvResults;
	Location l1, l2;
	String origin, destination;
	String sourcelat, sourcelong, destlat, destlong;
	List<String> distances = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bClear = (Button) findViewById(R.id.button_clear);
		Button bFetch = (Button) findViewById(R.id.button_fetch);
		Button button_fetch_googleapi = (Button) findViewById(R.id.button_fetch_googleapi);
		tvResults = (TextView) findViewById(R.id.tvResults);
		l1 = new Location("source");
		l2 = new Location("destination");

		l1.setLatitude(27.667024);
		l1.setLongitude(85.322474);
		l2.setLatitude(27.688041);
		l2.setLongitude(85.316212);

		sourcelat = Double.toString(l1.getLatitude());
		sourcelong = Double.toString(l1.getLongitude());
		destlat = Double.toString(l2.getLatitude());
		destlong = Double.toString(l2.getLongitude());

		origin = l1.getLatitude() + "," + l1.getLongitude();
		destination = l2.getLatitude() + "," + l2.getLongitude();

		bFetch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				GithubService service = ServiceFactory.createRetrofitService(
						GithubService.class, GithubService.SERVICE_ENDPOINT);
				for (String login : Data.githubList) {
					service.getUser(login)
							// method getUser() returns Observable
							.subscribeOn(Schedulers.newThread())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribe(new Subscriber<Github>() {
								@Override
								public final void onCompleted() {
									// do nothing
									Toast.makeText(getApplicationContext(),
											"finished", Toast.LENGTH_SHORT)
											.show();
									Log.d("crossover", "the fetched items are:"
											+ mItems);
									updateUI();
								}

								@Override
								public final void onError(Throwable e) {
									Log.e("KhithubDemoerror", e.getMessage());
									System.out.println(e.getMessage());
								}

								@Override
								public final void onNext(Github response) {
									// mCardAdapter.addData(response);
									mItems.add(response);
								}
							});
				}
			}
		});

		button_fetch_googleapi.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GithubServiceGoogle service = ServiceFactory
						.createRetrofitService(GithubServiceGoogle.class,
								GithubServiceGoogle.SERVICE_GOOGLE_MATRIX_URL);

				service.getDistanceMatrix(sourcelat + "," + sourcelong,
						destlat + "," + destlong, "driving")
						// method getUser() returns Observable
						.subscribeOn(Schedulers.newThread())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Subscriber<Response>() {
							@Override
							public final void onCompleted() {
								// do nothing
								Toast.makeText(getApplicationContext(),
										"finished", Toast.LENGTH_SHORT).show();
								Log.d("crossover", "the fetched items are:"
										+ mItems);
								updateUI2();
							}

							@Override
							public final void onError(Throwable e) {
								Log.e("KhithubDemoerror", e.getMessage());
								System.out.println(e.getMessage());
							}

							@Override
							public final void onNext(Response response) {
								Log.d("crossover",
										"the reponse obtained are:"
												+ response.rows.get(0).elements
														.get(0).distance.text);
								Log.d("crossover",
										"duration is:"
												+ response.rows.get(0).elements
														.get(0).duration
														.getText());
								distances.add(response.rows.get(0).elements
										.get(0).distance.text);
							}
						});

			}
		});

	}

	public void updateUI() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < mItems.size(); i++) {
			builder.append(mItems.get(i).getBlog());
			builder.append("\n");
		}
		String result = builder.toString();
		tvResults.setText(result);
	}

	public void updateUI2() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < distances.size(); i++) {
			builder.append(distances.get(i));
			builder.append("\n");
		}
		String result = builder.toString();
		tvResults.setText(result);
	}

}
