package com.example.jonathan.testretrofit

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.awaitResponse
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * This is the Model part of the MVVM Clean architecture.
 * - data classes
 * - repository to handle different data sources
 */

// The complete Url is: BASE_URL + RELATIVE_URL:
const val BASE_URL = "https://jsonplaceholder.typicode.com/"
const val RELATIVE_URL = "posts"

private const val TAG = "TR: PostModel"

// Test URL - a Json file with a list of users:
val url = URL(BASE_URL + RELATIVE_URL)

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

    // This gets users from an internet Json file:
    fun getDataFromWebByHttpUrlConnection(): List<Post> {
        Log.d(TAG, "PostRepository: getDataFromWebByHttpUrlConnection")

        // Get Json string from network:
        val dataFromNetwork = DataFromWebByHttpUrlConnection()
        val jsonString = dataFromNetwork.getData()

        // Convert Json string to data class objects using Gson:
        val gsonUtil = GsonUtil()
        val posts = gsonUtil.fromJsonToDataClass(jsonString)

        for (post in posts) {
            Log.v(TAG, "PostRepository: getUsersFromWebByHttpUrlConnection: post.id, title=[${post.id}, ${post.title}]")
        }

        return posts
    }
}

// Data from web by HttpURLConnection
class DataFromWebByHttpUrlConnection {
    // This is based on internet search results:
    fun getData(): String {
        Log.d(TAG, "DataFromWebByHttpUrlConnection: getData")

        val response = StringBuilder()

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.v(TAG, "DataFromWebByHttpUrlConnection: getData: HTTP_OK")

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            reader.close()

            Log.v(TAG, "DataFromWebByHttpUrlConnection: getData: response=\n$response")
        } else {
            Log.v(TAG, "DataFromWebByHttpUrlConnection: getData: responseCode=\n${connection.responseCode}")
        }

        connection.disconnect()

        return response.toString()
    }
}

// Gson utility class
class GsonUtil {
    // This converts Json string to a list of Data Class object of type User:
    fun fromJsonToDataClass(jsonString: String): List<Post> {
        Log.d(TAG, "GsonUtil: fromJsonToDataClass")

        val gson = Gson()

        // Define the type for the list of User objects:
        val userType = object : TypeToken<List<Post>>() {}.type

        // Deserialize the JSON string into a list of User objects:
        val users = gson.fromJson<List<Post>>(jsonString, userType)

        return users
    }
}

