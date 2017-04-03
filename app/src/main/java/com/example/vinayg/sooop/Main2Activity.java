
package com.example.vinayg.sooop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vinayg.sooop.model.GithubIssue;
import com.example.vinayg.sooop.model.GsonAdaptersModel;
import com.example.vinayg.sooop.model.ImmutableGithubIssue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main2Activity extends AppCompatActivity {

    GitHubService mGitHubService;


    Spinner issuesSpinner;
    EditText commentEditText;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sendButton = (Button) findViewById(R.id.send_comment_button);

        issuesSpinner = (Spinner) findViewById(R.id.issues_spinner);
        issuesSpinner.setEnabled(false);

        commentEditText = (EditText) findViewById(R.id.comment_edittext);

        createGithubAPI();
    }

    private void createGithubAPI() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapterFactory(new GsonAdaptersModel())
                .create();
        final String credentials = Credentials.basic("username", "password");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();

                        Request.Builder builder = originalRequest.newBuilder().header("Authorization",
                                credentials);

                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GitHubService.ENDPOINT)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mGitHubService = retrofit.create(GitHubService.class);
    }

    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.loadIssues_button:

                Call<List<GithubIssue>> callIssues = mGitHubService.getIssues();
                callIssues.enqueue(issues);
                break;
            case R.id.send_comment_button:
                String newComment = commentEditText.getText().toString();
                if (!newComment.isEmpty()) {

                    GithubIssue selectedItem = (GithubIssue) issuesSpinner.getSelectedItem();
                    GithubIssue item = ImmutableGithubIssue.builder()
                                        .id(selectedItem.id())
                                        .commentsUrl(selectedItem.commentsUrl())
                                        .title(selectedItem.title())
                                        .comment(newComment)
                                        .build();
                    Call<ResponseBody> postComment = mGitHubService.postComment(item.commentsUrl(), item);
                    postComment.enqueue(comment);
                } else {
                    Toast.makeText(this, "Please enter a comment", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    Callback<List<GithubIssue>> issues = new Callback<List<GithubIssue>>() {
        @Override
        public void onResponse(Call<List<GithubIssue>> call, Response<List<GithubIssue>> response) {
            if (response.isSuccessful()) {
                List<GithubIssue> issuesList = response.body();
                GithubIssue[] idArray = issuesList.toArray(new GithubIssue[issuesList.size()]);
                Log.d("issues",idArray.length+"");
                ArrayAdapter<GithubIssue> spinnerAdapter = new ArrayAdapter<>(Main2Activity.this, android.R.layout.simple_spinner_dropdown_item, idArray);
                issuesSpinner.setAdapter(spinnerAdapter);

                issuesSpinner.setEnabled(true);
                commentEditText.setEnabled(true);
                sendButton.setEnabled(true);
            } else {
                Log.e("onResponse failure", "Code: " + response.code() + " , Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<List<GithubIssue>> call, Throwable t) {
            t.printStackTrace();
        }
    };

    Callback<ResponseBody> comment = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            if (response.isSuccessful()) {
                commentEditText.setText("");
                Toast.makeText(Main2Activity.this, "Comment created", Toast.LENGTH_LONG).show();
            } else {
                Log.e("onResponse failure", "Code: " + response.code() + " , Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            t.printStackTrace();
        }
    };
}
