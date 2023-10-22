package com.example.startproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ReposScreenDestination(UserId: String, navController: NavController) {
    val viewModel = getViewModel<ReposViewModel> { parametersOf(UserId) }
    ReposScreen(
        state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is ReposContract.Effect.Navigation.Back) {
                navController.popBackStack()
            }
        },
    )
}