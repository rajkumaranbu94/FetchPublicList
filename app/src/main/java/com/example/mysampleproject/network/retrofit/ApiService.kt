package com.example.mysampleproject.network.retrofit

import com.example.mysampleproject.network.response.ListViewResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/posts?print=pretty")
    suspend fun fetchResults(): Response<ListViewResponse>
}