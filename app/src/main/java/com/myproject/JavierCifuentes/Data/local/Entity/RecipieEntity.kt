package com.myproject.JavierCifuentes.Data.local.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myproject.JavierCifuentes.Data.local.Domain.Receta


@Entity
data class RecetaEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val imagenRes: Int,
    val ingredientes: String?,
    val pasos: String?,
    val isFavorite: Boolean = false,
    val tiempo: Int
)


fun RecetaEntity.mapToModel(): Receta {
    return Receta(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        imagenRes = imagenRes,
        //TODO: use string resources
        ingredientes = ingredientes ?: "No se inseraron ingredientes",
        pasos = pasos ?: "No se insertaron pasos",
        isFavorite = this.isFavorite,
        tiempo = this.tiempo
    )
}

fun Receta.mapToEntity(): RecetaEntity {
    return RecetaEntity(
        id = id,
        nombre = nombre,
        descripcion = descripcion,
        imagenRes = imagenRes,
        ingredientes = ingredientes,
        pasos = pasos,
        isFavorite = isFavorite,
        tiempo = tiempo
    )
}
