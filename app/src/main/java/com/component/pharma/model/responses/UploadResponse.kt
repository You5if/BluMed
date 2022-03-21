package com.component.pharma.model.responses

data class UploadResponse(
    val apiPath: String,
    val extention: String,
    val fileName: String,
    val fullPath: String,
    val originalFileName: String
)