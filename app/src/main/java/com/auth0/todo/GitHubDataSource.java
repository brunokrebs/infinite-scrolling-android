package com.auth0.todo;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GitHubDataSource extends PageKeyedDataSource<String, JobModel> {

    private int pageCount = 1;
    private GitHubApi gitHubApi = new Retrofit.Builder().baseUrl("https://jobs.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubApi.class);

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, JobModel> callback) {

        gitHubApi.getAndroidJobs(pageCount).enqueue(new Callback<List<JobModel>>() {
            @Override
            public void onResponse(Call<List<JobModel>> call, Response<List<JobModel>> response) {
                Log.d("IDEE -- loadInitial", String.valueOf(pageCount));
                pageCount++;
                callback.onResult(response.body(),String.valueOf(pageCount),String.valueOf(pageCount));
            }

            @Override
            public void onFailure(Call<List<JobModel>> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, JobModel> callback) { }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, JobModel> callback) {
        gitHubApi.getAndroidJobs(pageCount).enqueue(new Callback<List<JobModel>>() {
            @Override
            public void onResponse(Call<List<JobModel>> call, Response<List<JobModel>> response) {
                pageCount++;
                Log.d("IDEE -- LoadAfter", String.valueOf(pageCount));
                callback.onResult(response.body(),String.valueOf(pageCount));
            }

            @Override
            public void onFailure(Call<List<JobModel>> call, Throwable t) {

            }
        });
    }

}
