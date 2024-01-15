package io.ganguo.incubator.service;

import java.util.List;

import io.ganguo.incubator.entity.Contributor;
import io.ganguo.incubator.entity.Issue;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Tony on 10/14/15.
 */
public interface GitHubService {

    @GET("/repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributors(
            @Path("owner") String owner,
            @Path("repo") String repo
    );

    @GET("/repos/square/retrofit/issues")
    Call<List<Issue>> issues();

}
