package com.studentmarketplace

import com.studentmarketplace.data.model.ApiListing
import java.util.Locale

fun ApiListing.toListing(): Listing {
    return Listing(
        id = listing_id,
        title = title,
        moduleCode = module_code,
        price = String.format(Locale.getDefault(), "R%.2f", price),
        condition = condition,
        category = category,
        description = description,
        imageRes = null,
        imageUri = null
    )
}