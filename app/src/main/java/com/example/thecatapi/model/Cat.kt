package com.example.thecatapi.model

data class Cat(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<Breed> = emptyList(),
    val favourite: Favourite? = null
)