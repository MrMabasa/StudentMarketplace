package com.studentmarketplace.data.model

data class CreateListingRequest(
    val seller_id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val condition: String,
    val category: String,
    val module_code: String?
)