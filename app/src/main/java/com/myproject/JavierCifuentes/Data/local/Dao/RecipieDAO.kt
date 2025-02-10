package com.myproject.JavierCifuentes.Data.local.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.myproject.JavierCifuentes.Data.local.Entity.RecetaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface RecipieDAO {

    @Query("SELECT * FROM RecetaEntity")
    fun getAllRecetas(): Flow<List<RecetaEntity>>


    @Query("DELETE FROM RecetaEntity WHERE id = :id")
    suspend fun deleteReceta(id: Int)

    @Insert
    suspend fun insertReceta(receta: RecetaEntity)

    @Update
    suspend fun updateReceta(receta: RecetaEntity)

}