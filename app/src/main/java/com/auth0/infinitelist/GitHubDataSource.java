package com.auth0.infinitelist;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.util.Consumer;
import androidx.lifecycle.MutableLiveData;
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

    MutableLiveData<NetworkState> status = new MutableLiveData<>();
    private Consumer retry;

    void retryFailedRequest(){
        Consumer previousRetry = retry;
        retry = null;
        if (previousRetry!=null){
            previousRetry.accept(null);
        }

    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, JobModel> callback) {

        status.postValue(new NetworkState(Status.LOADING));
        gitHubApi.getAndroidJobs(pageCount).enqueue(new Callback<List<JobModel>>() {
            @Override
            public void onResponse(Call<List<JobModel>> call, Response<List<JobModel>> response) {
                callback.onResult(response.body(),String.valueOf(pageCount),String.valueOf(pageCount+1));
                status.postValue(new NetworkState(Status.SUCCESS));
                pageCount++;
            }

            @Override
            public void onFailure(Call<List<JobModel>> call, Throwable t) {
                status.postValue(new NetworkState(Status.FAILED));
                retry = x -> loadInitial(params, callback);
            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, JobModel> callback) { }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, @NonNull final LoadCallback<String, JobModel> callback) {
        status.postValue(new NetworkState(Status.LOADING));
        gitHubApi.getAndroidJobs(pageCount).enqueue(new Callback<List<JobModel>>() {
            @Override
            public void onResponse(Call<List<JobModel>> call, Response<List<JobModel>> response) {
                pageCount++;
                callback.onResult(response.body(),String.valueOf(pageCount));
                status.postValue(new NetworkState(Status.SUCCESS));
            }

            @Override
            public void onFailure(Call<List<JobModel>> call, Throwable t) {
                status.postValue(new NetworkState(Status.FAILED));
                retry = x -> loadAfter(params, callback);

            }
        });
    }

}
