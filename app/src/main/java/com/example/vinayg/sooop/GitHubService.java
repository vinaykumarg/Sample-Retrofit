package com.example.vinayg.sooop;

import com.example.vinayg.sooop.model.GithubIssue;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by vinay.g.
 */

public interface GitHubService {
    String ENDPOINT = "https://api.github.com";

    @GET("repos/vinaykumarg/TMDB/issues")
    Call<List<GithubIssue>> getIssues();

    @POST
    Call<ResponseBody> postComment(@Url String url, @Body GithubIssue issue);
}
