package com.component.pharma.model.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(
//        tableName = "products"
//)
data class GetProduct(
//        @PrimaryKey(autoGenerate = true)
//        var idT: Int? = null,
    val active: Boolean,
    val apiImagePath: String,
    val apiPath: String,
    val barcode: String,
    val categoryName: String,
    val description: String,
    val extension: String,
    val fileName: String,
    val fullPath: String,
    val groupName: String,
    val originalFileName: String,
    val phProdCatId: Int,
    val phProdGroupId: Int,
    val phProdUnitId: Int,
    val phProductId: Int,
    val phSaleUnitId: Int,
    val phWarehouseId: Int,
    val price: Double,
    val productCode: String,
    val productName: String,
    val productUnit: String,
    val qrcode: String,
    val saleUnit: String,
    val sciName: String,
    val totalPages: Int,
    val totalRecords: Int,
    val unitMeasure: String,
    val wareHouseName: String,

)