package com.component.pharma.model.responses

data class userProfile(
    val pharmUserId: Int,
    val profileSource: String,
    val profileType: String
)