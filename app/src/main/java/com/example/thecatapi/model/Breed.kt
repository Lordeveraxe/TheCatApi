package com.example.thecatapi.model

data class Breed(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val countryCodes: String,
    val lifeSpan: String,
    val wikipediaUrl: String?,
    val weight: Weight,
    val description: String
)