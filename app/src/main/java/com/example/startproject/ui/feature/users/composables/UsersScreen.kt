package com.example.startproject.ui.feature.users.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.startproject.R
import com.example.startproject.data.model.buildUserPreview
import com.example.startproject.ui.base.SIDE_EFFECTS_KEY
import com.example.startproject.ui.feature.common.NetworkError
import com.example.startproject.ui.feature.common.Progress
import com.example.startproject.ui.feature.users.UsersContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
fun UsersScreen(
    state: UsersContract.State,
    effectFlow: Flow<UsersContract.Effect>?,
    onEventSent: (event: UsersContract.Event) -> Unit,
    onNavigationRequested: (navigationEffect: UsersContract.Effect.Navigation) -> Unit
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val snackBarMessage = stringResource(R.string.users_screen_snackbar_loaded_message)

    LaunchedEffect(SIDE_EFFECTS_KEY) {
        effectFlow?.onEach { effect ->
            when (effect) {
                is UsersContract.Effect.DataWasLoaded -> {
                    snackBarHostState.showSnackbar(
                        message = snackBarMessage,
                        duration = SnackbarDuration.Short
                    )
                }

                is UsersContract.Effect.Navigation.ToRepos -> onNavigationRequested(effect)
            }
        }?.collect()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = { UsersTopBar() }
    ) { paddingValues ->
        when {
            state.isLoading -> Progress(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            )

            state.isError -> NetworkError(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
            ) { onEventSent(UsersContract.Event.Retry) }

            else -> UsersList(users = state.users) {
                onEventSent(
                    UsersContract.Event.UserSelection(
                        it
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsersScreenSuccessPreview() {
    val users = List(3) { buildUserPreview() }
    UsersScreen(
        state = UsersContract.State(
            users = users,
            isLoading = false,
            isError = false,
        ),
        effectFlow = null,
        onEventSent = {},
        onNavigationRequested = {},
    )
}

@Preview(showBackground = true)
@Composable
fun UsersScreenErrorPreview() {
    UsersScreen(
        state = UsersContract.State(
            users = emptyList(),
            isLoading = false,
            isError = true,
        ),
        effectFlow = null,
        onEventSent = {},
        onNavigationRequested = {},
    )
}
