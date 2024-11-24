package com.example.jonathan.testretrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "TR: PostViewModel"

enum class DataSourceType {
    Test,
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
                }

            Log.v(TAG, "getData: newPosts=[$newPosts]")

            _posts.postValue(newPosts)
        }
    }
}
