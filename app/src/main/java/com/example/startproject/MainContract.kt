package com.example.startproject

import com.example.startproject.ui.base.UiEffect
import com.example.startproject.ui.base.UiEvent
import com.example.startproject.ui.base.UiState

class MainContract {
    sealed class Event : UiEvent {
        object OnRandomNumberClicked : Event()
        object OnShowToastClicked : Event()
    }

    data class State(
        val randomNumberState: RandomNumberState
    ) : UiState

    sealed class RandomNumberState {
        object Idle : RandomNumberState()
        object Loading : RandomNumberState()
        data class Success(val number : Int) : RandomNumberState()
    }

    sealed class Effect : UiEffect {

        object ShowToast : Effect()

    }
}