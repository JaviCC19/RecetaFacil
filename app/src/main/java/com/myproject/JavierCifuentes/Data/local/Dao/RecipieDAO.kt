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

    @Query("SELECT * FROM recetaentity WHERE id = :id LIMIT 1")
    suspend fun getRecetaById(id: Int): RecetaEntity?

    @Insert
    suspend fun insertReceta(receta: RecetaEntity): Long

    @Update
    suspend fun updateReceta(receta: RecetaEntity)

    @Query("UPDATE recetaentity SET imagenUri = :uri WHERE id = :id")
    suspend fun actualizarImagen(id: Int, uri: String)


    @Query("SELECT id FROM recetaentity ORDER BY id DESC LIMIT 1")
    suspend fun getUltimaRecetaId(): Int



}