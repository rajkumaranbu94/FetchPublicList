package com.example.mysampleproject.viewmodel

import ListViewResponseItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mysampleproject.FetchDataApplication
import com.example.mysampleproject.base.BaseViewModel
import com.example.mysampleproject.base.ScreenState
import com.example.mysampleproject.network.repository.MainRepository
import com.example.mysampleproject.network.repository.MainRepositoryImpl
import com.example.mysampleproject.network.retrofit.wrapper.ErrorWrapper
import com.example.mysampleproject.network.retrofit.wrapper.ResponseWrapper
import com.example.mysampleproject.utils.NetworkCheckUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class FetchResultViewModel(
    private val mainRepository: MainRepository,
    private val dispatcher: CoroutineContext = Dispatchers.IO
) : BaseViewModel<FetchResultFragmentEvent, FetchResultFragmentState>() {

    override fun handleEvent(publishedEvent: FetchResultFragmentEvent) {
        when (publishedEvent) {
            is FetchResultFragmentEvent.GetResult -> {
                fetchResult()
            }
        }
    }

    private fun fetchResult() {
        if (state.value == ScreenState.Loading) return
        setLoadingState()
        viewModelScope.launch(dispatcher) {
            when (val apiResponse = mainRepository.fetchResults()) {
                is ResponseWrapper.Success -> {
                    apiResponse.data?.let {
                        val response = apiResponse.data
                        postState(FetchResultFragmentState.FetchSearchResponseSuccess(response))
                    }
                }

                is ResponseWrapper.Error -> {
                    postState(
                        FetchResultFragmentState.OnErrorResponse(
                            apiResponse.errorWrapper
                        )
                    )
                }
            }
        }
    }
}

class FetchResultViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FetchResultViewModel(MainRepositoryImpl()) as T
    }
}

sealed class FetchResultFragmentEvent {
    class GetResult() : FetchResultFragmentEvent()

    override fun toString(): String {
        return this::class.java.simpleName
    }
}

sealed class FetchResultFragmentState {
    class FetchSearchResponseSuccess(val fetchList: MutableList<ListViewResponseItem>) :
        FetchResultFragmentState()

    class OnErrorResponse(val errorWrapper: ErrorWrapper) : FetchResultFragmentState()

    override fun toString(): String {
        return this::class.java.simpleName
    }
}