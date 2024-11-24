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
        viewModelScope.launch(Dispatchers.IO) {
            val posts =
                when (dataSourceType) {
                    DataSourceType.Test -> repository.getDataByTest()
                    DataSourceType.WebByRetrofit -> {
                        Log.e(TAG, "getData: Not yet supported dataSourceType !")

                        emptyList()
                    }
                }

            _posts.postValue(posts)
        }
    }
}
