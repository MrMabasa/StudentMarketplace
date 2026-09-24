package com.studentmarketplace.data.remote

import com.studentmarketplace.data.model.ApiListing
import retrofit2.http.GET

interface ListingApiService {

    @GET("listings")
    suspend fun getListings(): List<ApiListing>
}