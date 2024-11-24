package com.example.jonathan.testretrofit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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
    LazyColumn {
        items(viewModel.posts) { post ->
            PostItemView(post)
        }
    }
}
