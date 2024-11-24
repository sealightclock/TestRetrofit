package com.example.jonathan.testretrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TR: MainActivity"

class MainActivity : ComponentActivity() {
    // Always late-init the ViewModel:
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        // Create ViewModel:
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
        // Update ViewModel:
        viewModel.getData(DataSourceType.Test)
        // Observe ViewModel (for debugging purposes only):
        viewModel.posts.observe(this) {
            Log.v(TAG, "onCreate: viewModel.posts.observe: $it")
        }

        setContent {
            PostListView(viewModel)
        }

        // Fetch posts from the API
        RetrofitInstance.retrofitApi.getPosts().enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    // Handle the response, for example, display posts
                    val posts = response.body()
                    posts?.forEach {
                        Log.d(TAG, "Post Title: ${it.title}")
                    }
                } else {
                    Log.e(TAG, "Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e(TAG, "API request failed: ${t.message}")
            }
        })
    }
}
