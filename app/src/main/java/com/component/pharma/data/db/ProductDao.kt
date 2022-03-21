package com.component.pharma.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.GetProduct


@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(product: TestProductModel): Long


    @Query("SELECT * FROM products")
    fun getAllProducts(): LiveData<List<TestProductModel>>

    @Delete()
    suspend fun deleteProduct(product: TestProductModel)

    @Update
    suspend fun updateProducts(product: TestProductModel)
}