package com.example.startproject.ui.feature.repos

import com.example.startproject.data.model.Repo
import com.example.startproject.data.model.UserDetail
import com.example.startproject.ui.base.UiEffect
import com.example.startproject.ui.base.UiEvent
import com.example.startproject.ui.base.UiState

class ReposContract {
    sealed class Event : UiEvent {
        object Retry : Event()
        object BackButtonClicked : Event()
    }

    data class State(
        val user: UserDetail?,
        val reposList: List<Repo>,
        val isUserLoading: Boolean,
        val isReposLoading: Boolean,
        val isError: Boolean,
    ) : UiState

    sealed class Effect : UiEffect {
        sealed class Navigation : Effect() {
            object Back : Navigation()
        }
    }

}