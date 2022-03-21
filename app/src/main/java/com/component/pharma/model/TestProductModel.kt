package com.component.pharma.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.component.pharma.ui.home.HomeViewModel
import java.io.Serializable

@Entity(
        tableName = "products"
)
data class TestProductModel (
        @PrimaryKey(autoGenerate = true)
        val phProductId: Int,
        val active: Boolean,
        val apiImagePath: String,
        val apiPath: String,
        val barcode: String,
        val bodysys: String,
        val bodysys2: String,
        val categoryName: String,
        val description: String,
        val dosage: String,
        val extension: String,
        val fileName: String,
        val fullPath: String,
        val groupName: String,
        val localAgent: String,
        val manufacturer: String,
        val originCou: String,
        val originalFileName: String,
        val pack: String,
        val phProdCatId: Int,
        val phProdGroupId: Int,
        val phProdUnitId: Int,
        val phSaleUnitId: Int,
        val phWarehouseId: Int,
        val prescClass: String,
        val price: Double,
        val productCode: String,
        val productName: String,
        val productUnit: String,
        val qrcode: String,
        val saleUnit: String,
        val sciName: String,
        val theraputic: String,
        val unitMeasure: String,
        val wareHouseName: String,
        var removeBtn: Boolean,
        var quantity: Int,
)