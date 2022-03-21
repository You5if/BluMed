package com.component.pharma.model.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "proms"
)
data class GetPromItems (
    @PrimaryKey(autoGenerate = true)
    var idR: Int? = null,
    val title: String,
    val imagePath : String
)