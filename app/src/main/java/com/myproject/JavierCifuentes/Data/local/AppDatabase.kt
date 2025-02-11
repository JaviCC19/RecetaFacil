package com.myproject.JavierCifuentes.Data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myproject.JavierCifuentes.Data.local.Dao.RecipieDAO
import com.myproject.JavierCifuentes.Data.local.Entity.RecetaEntity




@Database(entities = [RecetaEntity::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun recipieDAO(): RecipieDAO
}
