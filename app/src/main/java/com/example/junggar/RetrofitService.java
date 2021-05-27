package com.example.junggar;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitService {

    // @GET( EndPoint-자원위치(URI) )
    @GET("posts/{post}")
    Call<PostResult> getPosts(@Path("post") String post);

}
