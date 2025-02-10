package com.myproject.JavierCifuentes.Data.local.Domain

data class Receta(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val imagenRes: Int,
    val ingredientes: List<String>,
    val pasos: List<String>,
    val isFavorite: Boolean = false,
    val tiempo: Int

) {
}