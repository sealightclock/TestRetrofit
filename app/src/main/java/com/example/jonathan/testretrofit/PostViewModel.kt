package com.example.jonathan.testretrofit

import androidx.lifecycle.ViewModel

class PostViewModel : ViewModel() {
    private val repository = PostRepository()

    val posts: List<Post>
        get() {
            return repository.getPosts()
        }
}