package com.example.jonathan.testretrofit.model

import android.util.Log
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

// Data from web by HttpURLConnection
class HttpUtil {
    // This is based on internet search results:
    fun getData(): String {
        Log.d(TAG, "HttpUtil: getData")

        val response = StringBuilder()

        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"

        if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            Log.v(TAG, "HttpUtil: getData: HTTP_OK")

            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            reader.close()

            Log.v(TAG, "HttpUtil: getData: response=\n$response")
        } else {
            Log.v(TAG, "HttpUtil: getData: responseCode=\n${connection.responseCode}")
        }

        connection.disconnect()

        return response.toString()
    }
}


