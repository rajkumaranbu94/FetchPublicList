package com.example.mysampleproject.network.response


import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    val error: String,
    val message: String,
    val path: String,
    val status: Int,
    val timestamp: String
)