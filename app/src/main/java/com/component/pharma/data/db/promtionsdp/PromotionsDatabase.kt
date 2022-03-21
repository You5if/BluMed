package com.component.pharma.data.db.promtionsdp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.component.pharma.data.db.ProductDao
import com.component.pharma.data.db.ProductDatabase
import com.component.pharma.model.TestProductModel
import com.component.pharma.model.responses.GetPromItems

@Database(
    entities = [GetPromItems::class],
    version = 1
)

abstract class PromotionsDatabase : RoomDatabase() {

    abstract fun getPromDao(): PromotionsDao

    companion object {
        @Volatile
        private var instance: PromotionsDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PromotionsDatabase::class.java,
                "prom_db1.db"
            ).build()
    }
}