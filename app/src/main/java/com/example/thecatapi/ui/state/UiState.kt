package com.example.thecatapi.ui.state


sealed class UiState {
    data object Loading : UiState()  // For the initial loading state
    data object LoadingMore : UiState()  // For when more data is being loaded during pagination
    data object Success : UiState()  // When data is successfully loaded
    data class Error(val message: String) : UiState()  // For any error during loading
}