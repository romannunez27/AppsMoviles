package com.example.pasteleriamilsaboresapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pasteleriamilsaboresapp.data.dao.ProductoDao
import com.example.pasteleriamilsaboresapp.data.dao.CartDao
import com.example.pasteleriamilsaboresapp.data.model.Producto
import com.example.pasteleriamilsaboresapp.data.model.CartItem

@Database(
    entities = [Producto::class, CartItem::class], // 👈 agregado CartItem
    version = 4,                                   // 👈 subimos versión
    exportSchema = false
)
abstract class ProductoDataBase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun cartDao(): CartDao      // 👈 nuevo DAO

    companion object {

        @Volatile
        private var INSTANCE: ProductoDataBase? = null

        fun getDatabase(context: Context): ProductoDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDataBase::class.java,
                    "producto_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
