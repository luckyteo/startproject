package com.example.startproject.di

import com.example.startproject.ui.feature.repos.ReposViewModel
import com.example.startproject.ui.feature.users.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        UsersViewModel(githubRepository = get())
    }

    viewModel { parameters ->
        ReposViewModel(
            userId = parameters.get(), githubRepository = get()
        )
    }
}