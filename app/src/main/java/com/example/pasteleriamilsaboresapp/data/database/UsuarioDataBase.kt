package com.example.pasteleriamilsaboresapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pasteleriamilsaboresapp.data.dao.UsuarioDao
import com.example.pasteleriamilsaboresapp.data.model.Usuario

@Database(entities = [Usuario::class], version = 2, exportSchema = false)
abstract class UsuarioDataBase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao

    companion object {
        @Volatile
        private var INSTANCE: UsuarioDataBase? = null

        fun getDatabase(context: Context): UsuarioDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UsuarioDataBase::class.java,
                    "usuario_database"
                )
                    // ✅ Si cambias el modelo (por ejemplo, agregas columnas),
                    // evita que Room crashee automáticamente
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
