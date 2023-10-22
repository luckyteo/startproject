package com.example.startproject.ui.feature.repos.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.startproject.R

@Composable
fun ReposTopBar(onBackButtonClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(R.string.repos_screen_top_bar_title)) },
        navigationIcon = {
            IconButton(onClick = { onBackButtonClicked() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ReposTopBarPreview() {
    ReposTopBar {}
}