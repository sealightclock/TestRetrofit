package com.example.jonathan.testretrofit.model

/**
 * This data class needs to match with the web JSON file format.
 */
data class Post(
    val id: Int,
    val title: String,
    val body: String
)