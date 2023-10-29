package com.example.startproject.ui.feature.repos.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.startproject.data.model.Repo
import com.example.startproject.data.model.RepoPreview

@Composable
fun ReposList(
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit,
    reposList: List<Repo>,
) {
    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            item { header() }
            items(count = reposList.size) { position ->
                ReposListItem(repo = reposList[position])
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReposListPreview() {
    val repos = List(3) { RepoPreview.repo }
    ReposList(header = {}, reposList = repos)
}