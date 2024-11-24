package com.example.jonathan.testretrofit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {
    private val repository = PostRepository()

    private var _posts = MutableLiveData<List<Post>>()
    var posts: LiveData<List<Post>> = _posts

    fun getData() {
        _posts.postValue(repository.getDataByTest())
    }
}