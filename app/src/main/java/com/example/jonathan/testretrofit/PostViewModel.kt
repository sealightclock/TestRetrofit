package com.example.jonathan.testretrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jonathan.testretrofit.model.Post
import com.example.jonathan.testretrofit.model.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * This contains the ViewModel part of the MVVM Clean architecture.
 */

private const val TAG = "TR: PostViewModel"

enum class DataSourceType {
    Test,
    WebByHttpUrlConnection,
    WebByRetrofit
}

class PostViewModel : ViewModel() {
    // Repository:
    private val repository = PostRepository()

    // Data (private/public pair):
    private var _posts = MutableLiveData<List<Post>>()
    var posts: LiveData<List<Post>> = _posts

    // Getting data:
    fun getData(dataSourceType: DataSourceType) {
        Log.d(TAG, "getData: dataSourceType=[$dataSourceType]")

        viewModelScope.launch(Dispatchers.IO) {
            val newPosts =
                when (dataSourceType) {
                    DataSourceType.Test -> repository.getDataByTest()
                    DataSourceType.WebByRetrofit -> repository.getDataFromWebByRetrofit()
                    DataSourceType.WebByHttpUrlConnection -> repository.getDataFromWebByHttpUrlConnection()
                }

            // Logging:
            newPosts.forEach { post ->
                Log.v(TAG, "getData: dataSourceType=[$dataSourceType]: post.id=[${post.id}]")
            }

            _posts.postValue(newPosts)
        }
    }
}
