package com.example.startproject.ui.feature.users

import androidx.lifecycle.viewModelScope
import com.example.startproject.data.GithubRepository
import com.example.startproject.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class UsersViewModel(
    private val githubRepository: GithubRepository
) : BaseViewModel<UsersContract.Event, UsersContract.State, UsersContract.Effect>() {

    init {
        getUsers()
    }

    override fun setInitialState(): UsersContract.State = UsersContract.State(
        users = emptyList(),
        isLoading = true,
        isError = false,
    )

    override fun handleEvents(event: UsersContract.Event) {
        when (event) {
            is UsersContract.Event.UserSelection -> setEffect {
                UsersContract.Effect.Navigation.ToRepos(
                    event.user.userId
                )
            }

            is UsersContract.Event.Retry -> getUsers()
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            setState { copy(isLoading = true, isError = false) }

            githubRepository.getUsers()
                .onSuccess { users ->
                    setState { copy(users = users, isLoading = false) }
                    setEffect { UsersContract.Effect.DataWasLoaded }
                }
                .onFailure {
                    setState { copy(isError = true, isLoading = false) }
                }
        }
    }
}
