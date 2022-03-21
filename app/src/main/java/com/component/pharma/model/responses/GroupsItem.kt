package com.component.pharma.model.responses

data class GroupsItem(
    val active: Boolean,
    val auditColumns: Any,
    val description: String,
    val groupCode: String,
    val groupName: String,
    val phProdCatId: Int,
    val phProdGroupId: Int
)