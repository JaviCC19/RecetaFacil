package com.myproject.JavierCifuentes.Data.local.Domain

import android.net.Uri

data class Receta(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val imagenUri: String? = null,
    val ingredientes: String,
    val pasos: String,
    val isFavorite: Boolean = false,
    val tiempo: Int

) {
}