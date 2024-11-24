package com.example.jonathan.testretrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.jonathan.testretrofit.ui.theme.TestRetrofitTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "TR: MainActivity"

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: PostViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()

        // Create ViewModel:
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
        // Update ViewModel:
        viewModel.getData()

        setContent {
            /*TestRetrofitTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }*/

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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestRetrofitTheme {
        Greeting("Android")
    }
}
