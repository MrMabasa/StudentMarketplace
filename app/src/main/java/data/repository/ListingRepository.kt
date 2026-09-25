package com.studentmarketplace.data.repository

import com.studentmarketplace.data.model.ApiListing
import com.studentmarketplace.data.model.CreateListingRequest
import com.studentmarketplace.data.remote.CreateListingResponse
import com.studentmarketplace.data.remote.RetrofitClient

class ListingRepository {

    suspend fun getListings(): List<ApiListing> {
        return RetrofitClient.api.getListings()
    }

    suspend fun createListing(
        request: CreateListingRequest
    ): CreateListingResponse {
        return RetrofitClient.api.createListing(request)
    }
}