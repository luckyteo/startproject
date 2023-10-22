package com.example.startproject.ui.base

sealed class ViewState<out E> {
    object Loading: ViewState<Nothing>()
    data class Message<E>(val message: String) : ViewState<E>()
}