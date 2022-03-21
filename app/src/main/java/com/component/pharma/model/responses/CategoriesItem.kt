package com.component.pharma.model.responses

data class CategoriesItem(
    val active: Boolean,
    val auditColumns: Any,
    val categoryCode: String,
    val categoryName: String,
    val description: String,
    val phProdCatId: Int
)