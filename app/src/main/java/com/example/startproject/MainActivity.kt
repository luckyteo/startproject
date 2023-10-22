package com.example.startproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.startproject.ui.theme.StartProjectTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartProjectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        initObservers()
    }

    /**
     * Initialize Observers
     */
    private fun initObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // do your work here
                viewModel.uiState.collect {
                    when (it.randomNumberState) {
                        is MainContract.RandomNumberState.Idle -> {
                        }

                        is MainContract.RandomNumberState.Loading -> {
                        }

                        is MainContract.RandomNumberState.Success -> {//loading
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.effect.collect {
                    when (it) {
                        is MainContract.Effect.ShowToast -> {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    StartProjectTheme {
        Greeting("Android")
    }
}