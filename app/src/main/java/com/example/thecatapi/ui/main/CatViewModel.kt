package com.example.thecatapi.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.MutableLiveData
import com.example.thecatapi.model.BreedWithImage
import com.example.thecatapi.network.CatApiService
import com.example.thecatapi.ui.state.UiState

class CatViewModel : ViewModel() {

    val uiState = MutableLiveData<UiState>()
    val breedData = MutableLiveData<List<BreedWithImage>>()  // Holds the current page's breed data
    private val apiService = CatApiService.create()

    var currentPage = 0    // Track the current page
    val pageSize = 10      // Number of items per page
    private var isLoading = false  // Prevent multiple simultaneous requests
    val isPreviousPageAvailable = MutableLiveData(false)  // Tracks if there's a previous page
    val isNextPageAvailable = MutableLiveData(true)      // Tracks if there's a next page

    // Function to fetch breeds and their images for a specific page
    fun fetchBreedsAndImages(page: Int) {
        if (isLoading) return  // Prevent multiple simultaneous API calls

        isLoading = true
        uiState.value = UiState.Loading

        viewModelScope.launch {
            try {
                val breeds = apiService.getBreeds(page, pageSize)
                val breedWithImages = mutableListOf<BreedWithImage>()

                for (breed in breeds) {
                    val images = apiService.getImageByBreed(breed.id)
                    val imageUrl = images.firstOrNull()?.url ?: ""
                    breedWithImages.add(BreedWithImage(breed, imageUrl))
                }

                // Set new data (this will replace the existing list in the RecyclerView)
                breedData.value = breedWithImages

                // Update the page number and button states
                currentPage = page
                isPreviousPageAvailable.value = currentPage > 0
                isNextPageAvailable.value = breeds.size == pageSize  // If we fetched full page, more may be available

                uiState.value = UiState.Success
            } catch (e: Exception) {
                uiState.value = UiState.Error("Error fetching breed data")
            } finally {
                isLoading = false
            }
        }
    }
}