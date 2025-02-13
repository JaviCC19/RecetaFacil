package com.myproject.JavierCifuentes.Presentation.ViewModels

import androidx.compose.runtime.remember
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.myproject.JavierCifuentes.Data.local.Dao.RecipieDAO
import com.myproject.JavierCifuentes.Data.local.DataStore.DataStoreManager
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
    private val recetaDao: RecipieDAO,
    private val dataStoreManager: DataStoreManager
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



    fun deleteReceta(id: Int) {
        viewModelScope.launch {
            recetaDao.deleteReceta(id)
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            dataStoreManager.clearUserName()

        }
    }



    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val db = RoomDependencies.provideDatabase(application)
                RecetasViewModel(
                    recetaDao = db.recipieDAO(),
                    dataStoreManager = DataStoreManager(application)
                )
            }
        }
    }
}
