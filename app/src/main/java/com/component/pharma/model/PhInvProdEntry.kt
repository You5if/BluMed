package com.component.pharma.model

data class PhInvProdEntry(
    val phInvProdId: Int,
    val phInvoiceId: Int,
    val phProductId: Int,
    val quantity: Double,
    val phProdUnitId: Int,
    val unitPrice: Double,
    val totalPrice: Double,
    val journalEntryId: Int,
    val journalDetailId: Int,
    val active: Boolean,
    val auditColumns: AuditColumns,
)