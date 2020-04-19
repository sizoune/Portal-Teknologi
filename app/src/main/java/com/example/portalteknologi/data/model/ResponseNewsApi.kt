package com.example.portalteknologi.data.model

data class ResponseNewsApi(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)