package com.myproject.JavierCifuentes.Presentation.States

data class CreateRecetaUiState(
    val nombre: String = "",
    val descripcion: String = "",
    val imagenUri: String = "",
    val ingredientes: String = "",
    val pasos: String = "",
    val tiempo: Int = 0,
    val isFavorite: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null
)
