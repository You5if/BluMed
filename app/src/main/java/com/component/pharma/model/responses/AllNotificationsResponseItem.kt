package com.component.pharma.model.responses

data class AllNotificationsResponseItem(
    val active: Boolean,
    val auditColumns: Any,
    val notifDate: String,
    val notification: String,
    val notificationId: Int,
    val pharmUserId: Int,
    val remarks: String
)