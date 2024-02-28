package com.example.catapp.data.models

import java.io.Serializable
data class CatItem(
    val id: String,
    val name: String,
    val imageUrl: String?,
    val origin: String,
    val temperament: String,
    val lifeSpan: String,
    val description: String
): Serializable