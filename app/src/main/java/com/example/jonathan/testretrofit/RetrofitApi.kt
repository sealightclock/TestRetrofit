package com.example.jonathan.testretrofit

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitApi {
    @GET("posts")
    fun getPosts(): Call<List<Post>>
}
