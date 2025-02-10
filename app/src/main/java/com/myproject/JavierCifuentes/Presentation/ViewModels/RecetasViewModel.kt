package com.myproject.JavierCifuentes.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.JavierCifuentes.Data.Local.Receta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RecetaUiState(
    val recetas: List<Receta> = emptyList(),
    val filtroFavoritos: Boolean = false,
    val filtroPorTiempo: Boolean = false
)

class RecetasViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        RecetaUiState(
            recetas = listOf(
                Receta(nombre = "Pasta Alfredo", descripcion = "Deliciosa pasta con salsa Alfredo", imagenRes = 0, ingredientes = listOf("Pasta", "Crema", "Queso parmesano"), pasos = listOf("Cocinar pasta", "Preparar salsa Alfredo"), tiempo = 20),
                Receta(nombre = "Pizza Margarita", descripcion = "Pizza clásica con tomate y mozzarella", imagenRes = 0, ingredientes = listOf("Masa de pizza", "Tomate", "Mozzarella"), pasos = listOf("Extender masa", "Agregar ingredientes", "Hornear"), tiempo = 30)
            )
        )
    )

    val uiState: StateFlow<RecetaUiState> = _uiState

    fun toggleFavorite(receta: Receta) {
        _uiState.update { currentState ->
            val updatedRecetas = currentState.recetas.map {
                if (it.nombre == receta.nombre) it.copy(isFavorite = !it.isFavorite) else it
            }
            currentState.copy(recetas = updatedRecetas)
        }
    }

    fun toggleFiltroFavoritos() {
        _uiState.update { it.copy(filtroFavoritos = !it.filtroFavoritos) }
    }

    fun toggleFiltroPorTiempo() {
        _uiState.update { it.copy(filtroPorTiempo = !it.filtroPorTiempo) }
    }
}
