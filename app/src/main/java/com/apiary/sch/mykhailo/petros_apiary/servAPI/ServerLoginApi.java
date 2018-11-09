package com.apiary.sch.mykhailo.petros_apiary.servAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ServerUser on 02.11.2018.
 */

/*
https://habr.com/post/314028/
стаття по використанню retrofit2
 */

public interface ServerLoginApi {
    @GET("/user/login")
    Call<Void> getLogin(@Query("login") String loginEmail,
                  @Query("pass") String password);
}
