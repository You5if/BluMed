package com.component.pharma.model.responses

data class OrderDetailsItem(
    val city: String,
    val deliveryPrice: Double,
    val description: String,
    val discountPrice: Double,
    val invoiceDate: String,
    val invoiceNo: String,
    val phInvProdId: Int,
    val phoneNumber: String,
    val price: Double,
    val productName: String,
    val promoCode: String,
    val quantity: Double,
    val totalPrice: Double,
    val unitName: String,
    val unitPrice: Double
)