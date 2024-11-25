package com.example.jonathan.testretrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider

/**
 * In the MVVM Clean architecture, the Activity contains only a View with a ViewModel.
 */

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
        viewModel.getData(DataSourceType.WebByRetrofit)
        // Observe ViewModel (for debugging purposes only):
        viewModel.posts.observe(this) {
            Log.v(TAG, "onCreate: viewModel.posts.observe: $it")
        }

        setContent {
            PostListView(viewModel)
        }
    }
}
