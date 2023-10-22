package com.example.startproject.data

import com.example.startproject.data.model.Repo
import com.example.startproject.data.model.User
import com.example.startproject.data.model.UserDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {
    @GET(Endpoints.GET_USERS)
    suspend fun getUsers(): List<User>

    @GET(Endpoints.GET_USER)
    suspend fun getUser(@Path("userLogin") userId: String): UserDetail?

    @GET(Endpoints.GET_REPOS_BY_USER)
    suspend fun getRepos(@Path("userLogin") userId: String): List<Repo>
}