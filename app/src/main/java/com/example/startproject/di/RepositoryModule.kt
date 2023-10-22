package com.example.startproject.di

import com.example.startproject.data.GithubRepository
import com.example.startproject.data.GithubRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    factory<GithubRepository> {
        GithubRepositoryImpl(
            githubApi = get()
        )
    }

}