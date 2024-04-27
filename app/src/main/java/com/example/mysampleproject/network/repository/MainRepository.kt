package com.example.mysampleproject.network.repository

import com.example.mysampleproject.network.response.ListViewResponse
import com.example.mysampleproject.network.retrofit.wrapper.ResponseWrapper

interface MainRepository {
    suspend fun fetchResults(): ResponseWrapper<ListViewResponse>

}