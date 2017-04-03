package com.example.vinayg.sooop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.vinayg.sooop.model.GsonAdaptersModel;
import com.example.vinayg.sooop.model.Movie;
import com.example.vinayg.sooop.model.MoviesResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    // TODO - insert your themoviedb.org API KEY here
    private TextView userTextView;
    private String[] movie_filter;
    private String BASE_URL;
    private Retrofit retrofit;
    private String API_KEY;
    private OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userTextView = (TextView) findViewById(R.id.html);
        BASE_URL = "https://api.themoviedb.org/3/";
        API_KEY = getString(R.string.apikey);
        movie_filter = new String[]{"popular","top_rated","favourite"};
        API api = getretrofit().create(API.class);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Call<MoviesResponse> call = api.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString()+"  jjj   ");
            }
        });
    }
    public Retrofit getretrofit() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(provideGson()))
                    .build();
        }
        return retrofit;
    }
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new GsonAdaptersModel())
                .create();
    }
    void useOkhttp() throws IOException {
        client = new OkHttpClient();
        String html = run("http://www.androidrank.org/");
        userTextView.setText(html);
        }
    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
