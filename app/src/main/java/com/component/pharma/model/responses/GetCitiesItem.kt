package com.component.pharma.model.responses

import androidx.room.Entity
import java.io.Serializable

data class GetCitiesItem(
    val active: Boolean,
    val cityName: String,
    val phDeliverPriceId: Int,
    val phLocId: Int,
    val price: Double,
    val totalPages: Int,
    val totalRecords: Int
): Serializable