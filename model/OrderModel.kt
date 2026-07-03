package com.example.nazahapp.model

data class OrderModel(
    val id: String = "",
    val productId: String = "",
    val productName: String = "",
    val productPrice: Double = 0.0,
    val productCode: String = "",
    val productPhotoUri: String? = null,
    val customerName: String = "",
    val customerAddress: String = "",
    val customerPhone: String = "",
    val status: String = "Pending", 
    val timestamp: Long = System.currentTimeMillis()
)
