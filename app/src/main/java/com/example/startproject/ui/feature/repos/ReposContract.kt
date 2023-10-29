package com.example.startproject.ui.feature.repos

import com.example.startproject.data.model.Repo
import com.example.startproject.data.model.UserDetail
import com.example.startproject.ui.base.ViewEvent
import com.example.startproject.ui.base.ViewSideEffect
import com.example.startproject.ui.base.ViewState

class ReposContract {
    sealed class Event : ViewEvent {
        object Retry : Event()
        object BackButtonClicked : Event()
    }

    data class State(
        val user: UserDetail?,
        val reposList: List<Repo>,
        val isUserLoading: Boolean,
        val isReposLoading: Boolean,
        val isError: Boolean,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        sealed class Navigation : Effect() {
            object Back : Navigation()
        }
    }

}