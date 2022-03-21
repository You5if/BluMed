package com.component.pharma.model.responses

data class PromoResponse(
    val active: Boolean,
    val discountAmount: Double,
    val discountType: Int,
    val phPromoId: Int,
    val promoCode: String
)