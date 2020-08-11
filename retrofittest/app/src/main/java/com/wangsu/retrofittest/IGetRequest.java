package com.wangsu.retrofittest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface IGetRequest {
    @GET("/ajax.php")
    Call<GetModel> getCall(@Query("a")String a,@Query("f")String f, @Query("t")String t, @Query("w")String w );
}
