package com.example.jonathan.testretrofit

import android.util.Log
import retrofit2.awaitResponse

/**
 * This is the Model part of the MVVM Clean architecture.
 * - data classes
 * - repository to handle different data sources
 */

private const val TAG = "TR: PostModel"

// This data class needs to  match with the web JSON file:
data class Post(
    val id: Int,
    val title: String,
    val body: String
)

// This repository handles getting data from various sources.
class PostRepository {
    // This gets test data without calling RESTful API, useful sometimes:
    @Suppress("RedundantSuspendModifier")
    suspend fun getDataByTest(): List<Post> {
        Log.d(TAG, "PostRepository: getDataByTest")

        return listOf(
            Post(1, "Title 1", "Body 1"),
            Post(2, "Title 2", "Body 2")
        )
    }

    // This gets data from the web by Retrofit RESTful API:
    suspend fun getDataFromWebByRetrofit(): List<Post> {
        Log.d(TAG, "PostRepository: getDataFromWebByRetrofit")

        val response = RetrofitInstance.retrofitApi.getPosts().awaitResponse()

        val newPosts = if (response.isSuccessful) {
            Log.v(TAG, "PostRepository: getDataFromWebByRetrofit: response.isSuccessful")

            response.body() ?: emptyList()
        } else { // Handle error cases
            Log.e(TAG, "PostRepository: getDataFromWebByRetrofit: response.message=[${response.message()}]")

            emptyList()
        }

        return newPosts
    }
}
