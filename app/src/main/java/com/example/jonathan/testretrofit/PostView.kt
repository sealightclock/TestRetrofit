package com.example.jonathan.testretrofit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.jonathan.testretrofit.model.Post

/**
 * This is the View part of MVVM Clean.
 */

@Composable
fun PostItemView(post: Post) {
    Column {
        Text(
            text = "ID: ${post.id}"
        )

        Text(
            text = "Title: ${post.title}"
        )

        Text(
            text = "Body: ${post.body}"
        )
    }
}

@Composable
fun PostListView(viewModel: PostViewModel) {
    // Convert LiveData to State for view recomposability:
    val posts = viewModel.posts.observeAsState(emptyList())

    // Use LazyColumn for better memory and performance and scrollability:
    LazyColumn {
        items(posts.value) { post ->
            PostItemView(post)
        }
    }
}
