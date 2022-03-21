package com.component.pharma.model

import java.util.*

data class PharmUserModel(
    val active: Int,
    val auditColumns: AuditColumns,
    val entryMode: String,
    val mobileNumber: String,
    val pharmUserId: Int,
    val readOnly: Boolean,
    val registrationDate: Date,
    val registrationStatus: Int
)