package com.myproject.JavierCifuentes.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.myproject.JavierCifuentes.Data.local.Dao.RecipieDAO
import com.myproject.JavierCifuentes.Data.local.Domain.Receta
import com.myproject.JavierCifuentes.Data.local.Entity.mapToEntity
import com.myproject.JavierCifuentes.Data.local.Entity.mapToModel
import com.myproject.JavierCifuentes.Domain.Di.RoomDependencies
import com.myproject.JavierCifuentes.Presentation.States.RecetaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class RecetasViewModel(
    private val recetaDao: RecipieDAO
) : ViewModel() {

    val uiState: StateFlow<RecetaUiState> = recetaDao.getAllRecetas()
        .map { entities ->
            RecetaUiState(
                recetas = entities.map { it.mapToModel() }
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            RecetaUiState()
        )

    private val _filtroFavoritos = MutableStateFlow(false)
    val filtroFavoritos = _filtroFavoritos.asStateFlow()

    private val _filtroTiempo = MutableStateFlow(false)
    val filtroTiempo = _filtroTiempo.asStateFlow()

    fun toggleFavorite(receta: Receta) {
        viewModelScope.launch {
            recetaDao.updateReceta(receta.copy(isFavorite = !receta.isFavorite).mapToEntity())
        }
    }

    fun toggleFiltroFavoritos() {
        _filtroFavoritos.value = !_filtroFavoritos.value
    }

    fun toggleFiltroPorTiempo() {
        _filtroTiempo.value = !_filtroTiempo.value
    }

    fun addReceta(nombre: String, descripcion: String, ingredientes: List<String>, pasos: List<String>, tiempo: Int) {
        viewModelScope.launch {
            val nuevaReceta = Receta(
                id = 0,
                nombre = nombre,
                descripcion = descripcion,
                imagenRes = 0,
                ingredientes = ingredientes,
                pasos = pasos,
                tiempo = tiempo
            )
            recetaDao.insertReceta(nuevaReceta.mapToEntity())
        }
    }

    fun deleteReceta(id: Int) {
        viewModelScope.launch {
            recetaDao.deleteReceta(id)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val db = RoomDependencies.provideDatabase(application)
                RecetasViewModel(
                    recetaDao = db.recipieDAO()
                )
            }
        }
    }
}
