package com.studentmarketplace.data.repository

import com.studentmarketplace.data.model.ApiListing
import com.studentmarketplace.data.remote.RetrofitClient

class ListingRepository {

    suspend fun getListings(): List<ApiListing> {
        return RetrofitClient.api.getListings()
    }
}