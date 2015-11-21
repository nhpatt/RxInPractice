package com.nhpatt.androidrxinaction.retrofit;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface GitHubService {
	@GET("/users/{user}/repos")
	Observable<List<Repo>> listRepos(@Path("user") String user);

	@GET("/repos/{user}/{repo}/commits")
	Observable<List<Commit>> listCommits(@Path("user") String user, @Path("repo") String repo);

	@GET("/users/{user}/repos")
	Call<List<Repo>> listReposSync(@Path("user") String user);
}