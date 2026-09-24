package com.studentmarketplace.data.model

data class ApiListing(
    val listing_id: Int,
    val seller_id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val condition: String,
    val category: String,
    val module_code: String?,
    val status: String,
    val date_created: String
)