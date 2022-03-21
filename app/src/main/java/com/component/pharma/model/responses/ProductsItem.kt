package com.component.pharma.model.responses

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(
//    tableName = "products"
//)
data class ProductsItem(
    @PrimaryKey(autoGenerate = true)
    var idR: Int? = null,
    val active: Boolean,
    val apiImagePath: String,
    val apiPath: String,
    val auditColumns: Any,
    val barcode: String,
    val bodysys: String,
    val bodysys2: String,
    val description: String,
    val dosage: String,
    val extension: String,
    val fileName: String,
    val fullPath: String,
    val localAgent: String,
    val manufacturer: String,
    val originCou: String,
    val originalFileName: String,
    val pack: String,
    val phProdCatId: Int,
    val phProdGroupId: Int,
    val phProdUnitId: Int,
    val phProductId: Int,
    val phSaleUnitId: Int,
    val phWarehouseId: Int,
    val prescClass: String,
    val productCode: String,
    val productName: String,
    val qrcode: String,
    val sciName: String,
    val theraputic: String,
    val unitMeasure: String,
    var removeBtn: Boolean,
)