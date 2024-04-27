package com.example.mysampleproject.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<in Event, State> : ViewModel() {
    protected val _state: MutableLiveData<ScreenState<State>> = MutableLiveData()

    val state: LiveData<ScreenState<State>> = _state

    fun dispatchEvent(event: Event) {
        handleEvent(event)
    }

    protected fun setState(state: State) {
        _state.value = ScreenState.Render(state)
    }

    protected fun postState(state: State) {
        _state.postValue(ScreenState.Render(state))
    }

    protected fun setLoadingState() {
        _state.value = ScreenState.Loading
    }

    protected fun postLoadingState() {
        _state.postValue(ScreenState.Loading)
    }

    protected abstract fun handleEvent(publishedEvent: Event)
}