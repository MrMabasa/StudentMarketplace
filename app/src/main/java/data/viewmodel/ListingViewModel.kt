package com.studentmarketplace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.studentmarketplace.data.model.ApiListing
import com.studentmarketplace.data.model.CreateListingRequest
import com.studentmarketplace.data.repository.ListingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListingViewModel : ViewModel() {

    private val repository = ListingRepository()

    private val _listings = MutableStateFlow<List<ApiListing>>(emptyList())
    val listings: StateFlow<List<ApiListing>> = _listings
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage
    private val _createSuccess = MutableStateFlow(false)
    val createSuccess: StateFlow<Boolean> = _createSuccess

    fun loadListings() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            try {
                _listings.value = repository.getListings()
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message ?: "Failed to load listings."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createListing(request: CreateListingRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _createSuccess.value = false

            try {
                repository.createListing(request)
                _createSuccess.value = true
            } catch (e: Exception) {
                _errorMessage.value =
                    e.message ?: "Failed to create listing."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetCreateSuccess() {
        _createSuccess.value = false
    }
}