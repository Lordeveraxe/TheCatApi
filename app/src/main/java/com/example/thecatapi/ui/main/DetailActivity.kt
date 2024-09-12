package com.example.thecatapi.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.thecatapi.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get data from intent
        val breedName = intent.getStringExtra("breedName")
        val breedDescription = intent.getStringExtra("breedDescription")
        val breedImageUrl = intent.getStringExtra("breedImageUrl")

        // Find views
        val imageView = findViewById<ImageView>(R.id.breedImageView)
        val nameTextView = findViewById<TextView>(R.id.breedNameTextView)
        val descriptionTextView = findViewById<TextView>(R.id.breedDescriptionTextView)

        // Set breed data
        nameTextView.text = breedName
        descriptionTextView.text = breedDescription

        // Load breed image using Glide
        Glide.with(this).load(breedImageUrl).into(imageView)
    }
}