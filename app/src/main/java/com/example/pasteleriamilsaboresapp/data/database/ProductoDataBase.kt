package com.example.pasteleriamilsaboresapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pasteleriamilsaboresapp.data.dao.ProductoDao
import com.example.pasteleriamilsaboresapp.data.model.Producto

@Database(
    entities = [Producto::class],
    version=2,
    exportSchema = false
)

abstract class ProductoDataBase: RoomDatabase(){

    abstract fun productoDao(): ProductoDao

    companion object{

        @Volatile
        private var INSTANCE: ProductoDataBase?=null

        fun getDatabase(context: Context): ProductoDataBase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductoDataBase::class.java,
                    "producto_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE=instance
                instance
            }
        }
    }
}