package com.component.pharma.model.responses

data class AllOrdersResponseItem(
    val accRemarks: String,
    val active: Boolean,
    val auditColumns: Any,
    val ccAgentRemarks: String,
    val createdBy: Int,
    val description: String,
    val discJournalDetailId: Int,
    val discJournalEntryId: Int,
    val invoiceDate: String,
    val invoiceNo: String,
    val invoiceTotal: Double,
    val locationURL: String,
    val locationX: Double,
    val locationY: Double,
    val omRemarks: String,
    val orderStatus: Int,
    val paymentTotal: Double,
    val phDriverId: Int,
    val phInvoiceId: Int,
    val phLocId: Int,
    val phWarehouseId: Int,
    val pharmUserId: Int,
    val phoneNumber: String,
    val promoCode: String,
    val remarks: String
)