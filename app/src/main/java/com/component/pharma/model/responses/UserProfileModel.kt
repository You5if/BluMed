package com.component.pharma.model.responses

import com.component.pharma.model.AuditColumns

data class UserProfileModel (
        val auditColumns: AuditColumns,
        val phUserProfileId : Int,
        val firstName : String?,
        val secondName : String?,
        val apiImagePath : String,
        val apiPath : String,
        val extension : String,
        val fileName : String,
        val fullPath : String,
        val originalFileName : String,
        val pharmUserId : Int,
        val entryMode : String,
        val readOnly : Boolean,
        val active: Boolean,
)