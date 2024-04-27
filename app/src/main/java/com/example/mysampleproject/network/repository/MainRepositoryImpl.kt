package com.example.mysampleproject.network.repository

import com.example.mysampleproject.base.BaseRepository
import com.example.mysampleproject.network.response.ListViewResponse
import com.example.mysampleproject.network.retrofit.RetrofitBuilder
import com.example.mysampleproject.network.retrofit.wrapper.ResponseWrapper

class MainRepositoryImpl: BaseRepository(),MainRepository {
    override suspend fun fetchResults(): ResponseWrapper<ListViewResponse> {
        return safeApiCall(
            call = {
                RetrofitBuilder.apiService.fetchResults()
            }
        )
    }
}