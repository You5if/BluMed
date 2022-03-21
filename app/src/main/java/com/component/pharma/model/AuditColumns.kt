package com.component.pharma.model

data class AuditColumns(
    val approvalStatusId: Int,
    val branchId: Int,
    val companyId: Int,
    val deviceType: String,
    val financialYearId: Int,
    val hostName: String,
    val iPAddress: String,
    val mACAddress: String,
    val userId: String
)