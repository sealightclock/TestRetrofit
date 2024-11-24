package com.example.jonathan.testretrofit

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState

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
    Column {
        // Observe LiveData as a State so that the view can be recomposed:
        viewModel.posts.observeAsState().value?.forEach { post ->
            PostItemView(post)
        }
    }
}
