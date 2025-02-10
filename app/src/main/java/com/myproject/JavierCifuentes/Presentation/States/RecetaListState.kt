package com.myproject.JavierCifuentes.Presentation.States

import com.myproject.JavierCifuentes.Data.local.Domain.Receta


data class RecetaUiState(
    val recetas: List<Receta> = emptyList(),
    val filtroFavoritos: Boolean = false,
    val filtroPorTiempo: Boolean = false
)