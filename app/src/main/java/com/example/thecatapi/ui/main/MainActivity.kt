package com.example.thecatapi.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thecatapi.R
import com.example.thecatapi.ui.state.UiState

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: CatViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var breedAdapter: BreedAdapter
    private lateinit var previousButton: Button
    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        previousButton = findViewById(R.id.previousPageButton)
        nextButton = findViewById(R.id.nextPageButton)

        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this)[CatViewModel::class.java]

        // Initialize adapter
        breedAdapter = BreedAdapter(mutableListOf()) { breedWithImage ->
            // Handle click for breed item, start DetailActivity and pass data
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("breedName", breedWithImage.breed.name)
                putExtra("breedDescription", breedWithImage.breed.description)
                putExtra("breedImageUrl", breedWithImage.imageUrl)
            }
            startActivity(intent)
        }
        recyclerView.adapter = breedAdapter

        // Observe LiveData for breed data
        viewModel.breedData.observe(this) { breedsWithImages ->
            breedAdapter.updateData(breedsWithImages)  // Update the list with the new page data
        }

        // Observe UI state
        viewModel.uiState.observe(this) { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }

                is UiState.Success -> {
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }

                is UiState.Error -> {
                    progressBar.visibility = View.GONE
                }

                is UiState.LoadingMore -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
            }
        }

        // Observe button availability
        viewModel.isPreviousPageAvailable.observe(this) { isAvailable ->
            previousButton.isEnabled = isAvailable
        }

        viewModel.isNextPageAvailable.observe(this) { isAvailable ->
            nextButton.isEnabled = isAvailable
        }

        // Set button click listeners
        previousButton.setOnClickListener {
            viewModel.fetchBreedsAndImages(viewModel.currentPage - 1)
        }

        nextButton.setOnClickListener {
            viewModel.fetchBreedsAndImages(viewModel.currentPage + 1)
        }

        // Initially load the first page
        viewModel.fetchBreedsAndImages(0)
    }
}