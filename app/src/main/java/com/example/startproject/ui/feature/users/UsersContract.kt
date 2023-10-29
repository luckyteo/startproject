package com.example.startproject.ui.feature.users

import com.example.startproject.data.model.User
import com.example.startproject.ui.base.ViewEvent
import com.example.startproject.ui.base.ViewSideEffect
import com.example.startproject.ui.base.ViewState

class UsersContract {

    sealed class Event : ViewEvent {
        data object Retry : Event()
        data class UserSelection(val user: User) : Event()
    }

    data class State(
        val users: List<User>,
        val isLoading: Boolean,
        val isError: Boolean,
    ) : ViewState

    sealed class Effect : ViewSideEffect {
        data object DataWasLoaded : Effect()

        sealed class Navigation : Effect() {
            data class ToRepos(val userId: String) : Navigation()
        }
    }

}
