package com.example.startproject.data

import com.example.startproject.data.model.Repo
import com.example.startproject.data.model.User
import com.example.startproject.data.model.UserDetail

interface GithubRepository {
    suspend fun getUsers(): Result<List<User>>
    suspend fun getUser(userId: String): Result<UserDetail?>
    suspend fun getRepos(userId: String): Result<List<Repo>>
}