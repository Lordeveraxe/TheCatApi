package com.example.thecatapi.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.thecatapi.R
import com.example.thecatapi.model.BreedWithImage

class BreedAdapter(
    private var breeds: MutableList<BreedWithImage>,
    private val onClick: (BreedWithImage) -> Unit
) : RecyclerView.Adapter<BreedAdapter.BreedViewHolder>() {

    class BreedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val breedName: TextView = itemView.findViewById(R.id.breedName)
        val breedImage: ImageView = itemView.findViewById(R.id.breedImage)
        val detailsButton: Button = itemView.findViewById(R.id.detailsButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_breed, parent, false)
        return BreedViewHolder(view)
    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        val breedWithImage = breeds[position]
        holder.breedName.text = breedWithImage.breed.name

        // Load the breed image using Glide
        Glide.with(holder.itemView.context).load(breedWithImage.imageUrl).into(holder.breedImage)

        // Set button click listener
        holder.detailsButton.setOnClickListener {
            onClick(breedWithImage)
        }
    }

    override fun getItemCount(): Int = breeds.size

    // Function to replace the current data with new data
    fun updateData(newBreeds: List<BreedWithImage>) {
        this.breeds.clear()  // Clear the existing data
        this.breeds.addAll(newBreeds)  // Add the new data
        notifyDataSetChanged()  // Notify the adapter of the data change
    }
}