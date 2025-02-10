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
    val ingredientes: List<String>,
    val pasos: List<String>,
    val isFavorite: Boolean = false,
    val tiempo: Int
)


fun RecetaEntity.mapToModel(): Receta {
    return Receta(
        id = this.id,
        nombre = this.nombre,
        descripcion = this.descripcion,
        imagenRes = this.imagenRes,
        ingredientes = this.ingredientes,
        pasos = this.pasos,
        isFavorite = this.isFavorite,
        tiempo = this.tiempo
    )
}

fun Receta.mapToEntity(): RecetaEntity {
    return RecetaEntity(
        id = this.id,
        nombre = this.nombre,
        descripcion = this.descripcion,
        imagenRes = this.imagenRes,
        ingredientes = this.ingredientes,
        pasos = this.pasos,
        isFavorite = this.isFavorite,
        tiempo = this.tiempo
    )
}
