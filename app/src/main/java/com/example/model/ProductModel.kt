package com.example.nazahapp.model

data class ProductModel(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val photoUri: String? = null,
    val productCode: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
