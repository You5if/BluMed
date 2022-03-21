package com.component.pharma.data.db.promtionsdp

import androidx.lifecycle.LiveData
import androidx.room.*
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.GetPromItems

@Dao
interface PromotionsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(prom: GetPromItems): Long


    @Query("SELECT * FROM proms")
    fun getAllProms(): LiveData<List<GetPromItems>>

    @Delete()
    suspend fun deleteProm(prom: GetPromItems)

    @Update
    suspend fun updateProm(prom: GetPromItems)
}