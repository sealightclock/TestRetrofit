package com.example.jonathan.testretrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import kotlin.properties.Delegates

/**
 * In the MVVM Clean architecture, the Activity contains only a View with a ViewModel.
 */

private const val TAG = "TR: MainActivity"

class MainActivity : ComponentActivity() {
    // Always late-init the ViewModel:
    private lateinit var viewModel: PostViewModel
    // This is to switch data source type:
    private var dataSourceType by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate")

        super.onCreate(savedInstanceState)

        // Create ViewModel:
        viewModel = ViewModelProvider(this)[PostViewModel::class.java]
        // Update ViewModel using the selected data source:
        viewModel.getData(DataSourceType.WebByHttpUrlConnection)
        dataSourceType = DataSourceType.WebByHttpUrlConnection.ordinal
        // Observe ViewModel (for debugging purposes only):
        viewModel.posts.observe(this) {
            Log.v(TAG, "onCreate: viewModel.posts.observe")
        }

        setContent {
            PostListView(viewModel)
        }
    }

    override fun onResume() {
        Log.d(TAG, "onResume")

        super.onResume()

        // Refresh the UI, and try another way of getting data:
        val numDataSourceTypes = DataSourceType.entries.size
        dataSourceType = (dataSourceType + 1) % numDataSourceTypes
        viewModel.getData(enumValues<DataSourceType>()[dataSourceType])
    }
}
