package com.example.jonathan.testretrofit

import android.util.Log

private const val TAG = "TR: PostModel"

// This needs to  match with the web JSON file:
data class Post(
    val id: Int,
    val title: String,
    val body: String
)

// TODO: Implement different ways of getting data:
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
}
