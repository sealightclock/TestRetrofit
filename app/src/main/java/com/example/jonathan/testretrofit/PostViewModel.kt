package com.example.jonathan.testretrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    // Repository:
    private val repository = PostRepository()

    // Data:
    private var _posts = MutableLiveData<List<Post>>()
    var posts: LiveData<List<Post>> = _posts

    // Getting data:
    fun getData() {
        // TODO: Add the ability to switch among ways of getting data:
        viewModelScope.launch(Dispatchers.IO) {
            _posts.postValue(repository.getDataByTest())
        }
    }
}
