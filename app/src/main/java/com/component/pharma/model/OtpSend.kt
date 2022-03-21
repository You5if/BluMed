package com.component.pharma.model

import kotlinx.coroutines.flow.Flow
import java.io.Serializable

data class OtpSend(
    val PharmUserId: Int,
    val MobileNumber: String,
    val otp: Int
) : Serializable