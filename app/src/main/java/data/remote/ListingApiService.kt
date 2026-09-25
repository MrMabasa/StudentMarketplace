package com.studentmarketplace.data.remote

import com.studentmarketplace.data.model.ApiListing
import com.studentmarketplace.data.model.CreateListingRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ListingApiService {

    @GET("listings")
    suspend fun getListings(): List<ApiListing>

    @POST("listings")
    suspend fun createListing(
        @Body request: CreateListingRequest
    ): CreateListingResponse
}

data class CreateListingResponse(
    val message: String,
    val listing_id: Int
)