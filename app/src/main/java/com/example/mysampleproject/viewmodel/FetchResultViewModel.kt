package com.example.mysampleproject.viewmodel

import com.example.mysampleproject.network.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class FetchListViewModel(
    private val mainRepository: MainRepository,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) {
}