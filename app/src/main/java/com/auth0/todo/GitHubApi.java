package com.auth0.todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GitHubApi {

    @GET("/positions.json?description=web")
    Call<List<JobModel>> getAndroidJobs(/*@Query("description") String jobDescription,*/@Query("page") int page);

}
