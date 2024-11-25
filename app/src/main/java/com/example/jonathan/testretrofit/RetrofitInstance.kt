package com.example.jonathan.testretrofit

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * This handles getting data from the web by Retrofit RESTful API.
 */

// The complete Url is: BASE_URL + RELATIVE_URL:
private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
private const val RELATIVE_URL = "posts"

// Define Retrofit API:
interface RetrofitApi {
    @GET(RELATIVE_URL)
    fun getPosts(): Call<List<Post>>
}

// Implement Retrofit API in a singleton:
object RetrofitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson for JSON parsing
            .build()
    }

    val retrofitApi: RetrofitApi by lazy {
        retrofit.create(RetrofitApi::class.java)
    }
}
