package com.example.bikechat2.data.model

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class MainViewModel : ViewModel() {
    private val _currentUsername = mutableStateOf("")
    val currentUsername: String get() = _currentUsername.value

    fun setCurrentUsername(username: String) {
        _currentUsername.value = username
    }
}
