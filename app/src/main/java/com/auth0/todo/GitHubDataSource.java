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
                Log.d("IDEE Load Initial",String.valueOf(response.body().size()));
                callback.onResult(response.body(),String.valueOf(pageCount),String.valueOf(pageCount+1));
            }

            @Override
            public void onFailure(Call<List<JobModel>> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, JobModel> callback) {

        gitHubApi.getAndroidJobs(pageCount).enqueue(new Callback<List<JobModel>>() {
            @Override
            public void onResponse(Call<List<JobModel>> call, Response<List<JobModel>> response) {
                if(pageCount > 1) {
                    pageCount = pageCount-1;
                } else {
                    pageCount = 1;
                }

                Log.d("IDEE Load Before",String.valueOf(response.body().size()));
                callback.onResult(response.body(),String.valueOf(pageCount));
            }

            @Override
            public void onFailure(Call<List<JobModel>> call, Throwable t) {

            }
        });

    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, JobModel> callback) {
        gitHubApi.getAndroidJobs(pageCount).enqueue(new Callback<List<JobModel>>() {
            @Override
            public void onResponse(Call<List<JobModel>> call, Response<List<JobModel>> response) {
                pageCount++;
                callback.onResult(response.body(),String.valueOf(pageCount));
                Log.d("IDEE Load after",String.valueOf(response.body().size()));

            }

            @Override
            public void onFailure(Call<List<JobModel>> call, Throwable t) {

            }
        });
    }

}
