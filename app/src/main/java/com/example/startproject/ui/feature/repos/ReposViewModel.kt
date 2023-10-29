package com.example.startproject.ui.feature.repos

import androidx.lifecycle.viewModelScope
import com.example.startproject.data.GithubRepository
import com.example.startproject.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class ReposViewModel(
    private val userId: String,
    private val githubRepository: GithubRepository
) : BaseViewModel<ReposContract.Event, ReposContract.State, ReposContract.Effect>() {

    override fun setInitialState(): ReposContract.State = ReposContract.State(
        user = null,
        reposList = emptyList(),
        isUserLoading = true,
        isReposLoading = true,
        isError = false
    )

    override fun handleEvents(event: ReposContract.Event) {
        when (event) {
            ReposContract.Event.BackButtonClicked -> {}
            ReposContract.Event.Retry -> {}
        }
    }

    private fun getAll() {
        viewModelScope.launch {
            getUser()
            getRepos()
        }
    }

    private suspend fun getUser() {
        setState { copy(isUserLoading = true, isError = false) }
        githubRepository.getUser(userId)
            .onSuccess { userDetail ->
                setState { copy(user = userDetail, isUserLoading = false) }
            }
            .onFailure { }
    }

    private suspend fun getRepos() {
        setState { copy(isReposLoading = true, isError = false) }
        githubRepository.getRepos(userId)
            .onSuccess { repos ->
                setState { copy(reposList = repos, isReposLoading = false) }
            }
            .onFailure {
                setState { copy(isError = true, isReposLoading = false) }
            }
    }
}