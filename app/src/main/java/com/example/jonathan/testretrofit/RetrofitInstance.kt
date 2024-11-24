package com.example.jonathan.testretrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

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
