package com.component.pharma.model.responses

data class OtpResponse (
    val userId: Int,
    val userName: String,
    val displayName: String,
    val userStatus: Int,
    val active: Boolean,
    val token: String,
    val firstName : String,
    val secondName : String,
    val profilePic : String,
    val phUserProfileId : Int
)