package com.example.jonathan.testretrofit

data class Post(
    val id: Int,
    val title: String,
    val body: String
)

class PostRepository {
    fun getPosts(): List<Post> {
        // TODO: Replace this test list:
        return listOf(
            Post(1, "Title 1", "Body 1"),
            Post(2, "Title 2", "Body 2")
        )
    }
}


