package com.myproject.JavierCifuentes.Domain.Di

import android.content.Context
import androidx.room.Room
import com.myproject.JavierCifuentes.Data.local.AppDatabase

object RoomDependencies {
    private var database: AppDatabase? = null

    private fun buildDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "receFacil.db"
        ).build()
    }

    fun provideDatabase(context: Context): AppDatabase {

        return database ?: synchronized(this) {
            database ?: buildDatabase(context).also { database = it }
        }
    }
}