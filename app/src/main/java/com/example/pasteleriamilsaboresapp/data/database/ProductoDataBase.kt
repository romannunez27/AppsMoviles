package com.example.pasteleriamilsaboresapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// ⚠️ No tiene entidades, así no se procesa nada.
@Database(
    entities = [],
    version = 1,
    exportSchema = false
)
abstract class ProductoDataBase : RoomDatabase() {
    // No implementes DAOs aún
    companion object {
        @Volatile
        private var INSTANCE: ProductoDataBase? = null

        fun getDatabase(context: Context): ProductoDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDataBase::class.java,
                    "producto_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
