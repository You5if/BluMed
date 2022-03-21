package com.component.pharma.model

import java.util.*

data class PrescripModel (
        val active: Int,
        val auditColumns: AuditColumns,
        val prescripId: Int,
        val pharmUserId: Int,
        val prescripCode: String,
        val phInvoiceId: Int,
        val remarks: String,
        val apiImagePath: String,
        val apiPath: String,
        val extension: String,
        val fileName: String,
        val fullPath: String,
        val originalFileName: String,
        val prescripDate: Date
)