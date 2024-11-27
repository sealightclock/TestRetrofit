package com.example.jonathan.testretrofit.model

import android.util.Log
import retrofit2.awaitResponse

private const val TAG = "TR: PostModel"

/**
 * This repository handles fetching data from various sources.
 */
class PostRepository {
    // This fetches test data without calling RESTful API, useful sometimes:
    @Suppress("RedundantSuspendModifier")
    suspend fun fetchDataByTest(): List<Post> {
        Log.d(TAG, "PostRepository: fetchDataByTest")

        return listOf(
            Post(1, "Title 1", "Body 1"),
            Post(2, "Title 2", "Body 2")
        )
    }

    // This fetchs data from the web by Retrofit RESTful API:
    suspend fun fetchDataFromWebByRetrofit(): List<Post> {
        Log.d(TAG, "PostRepository: fetchDataFromWebByRetrofit")

        val response = RetrofitInstance.retrofitApi.getPosts().awaitResponse()

        val newPosts = if (response.isSuccessful) {
            Log.v(TAG, "PostRepository: fetchDataFromWebByRetrofit: response.isSuccessful")

            response.body() ?: emptyList()
        } else { // Handle error cases
            Log.e(TAG, "PostRepository: fetchDataFromWebByRetrofit: response.message=[${response.message()}]")

            emptyList()
        }

        return newPosts
    }

    // This fetchs users from an internet Json file:
    fun fetchDataFromWebByHttpUrlConnection(): List<Post> {
        Log.d(TAG, "PostRepository: fetchDataFromWebByHttpUrlConnection")

        // fetch Json string from network:
        val dataFromNetwork = HttpUtil()
        val jsonString = dataFromNetwork.getData()

        // Convert Json string to data class objects using Gson:
        val gsonUtil = GsonUtil()
        val posts = gsonUtil.fromJsonToDataClass(jsonString)

        for (post in posts) {
            Log.v(TAG, "PostRepository: fetchUsersFromWebByHttpUrlConnection: post.id, title=[${post.id}, ${post.title}]")
        }

        return posts
    }
}
