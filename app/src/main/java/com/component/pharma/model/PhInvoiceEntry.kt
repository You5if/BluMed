package com.component.pharma.model

import java.util.*

data class PhInvoiceEntry (
    val phInvoiceId: Int,
    val invoiceNo: String,
    val invoiceDate: Date,
    val promoCode: String,
    val pharmUserId: Int,
    val phWarehouseId: Int,
    val discJournalEntryId: Int,
    val discJournalDetailId: Int,
    val phLocId: Int,
    val description: String,
    val locationX: Double,
    val locationY: Double,
    val locationURL: String,
    val orderStatus: Int,
    val remarks: String,
    val phDriverId: Int,
    val createdBy: Int,
    val phoneNumber: String,
    val ccAgentRemarks: String,
    val omRemarks: String,
    val accRemarks: String,
    val active: Boolean,
    val auditColumns: AuditColumns,

)