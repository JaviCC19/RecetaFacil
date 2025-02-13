package com.myproject.JavierCifuentes.Presentation.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.myproject.JavierCifuentes.Data.local.Domain.Receta
import com.myproject.JavierCifuentes.Data.local.Entity.mapToEntity
import com.myproject.JavierCifuentes.Data.local.repositories.RecetaRepository
import com.myproject.JavierCifuentes.Domain.Di.RoomDependencies
import com.myproject.JavierCifuentes.Presentation.States.RecetaDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecetasDetailViewModel(private val recetaRepository: RecetaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(RecetaDetailsState())
    val uiState: StateFlow<RecetaDetailsState> = _uiState


    fun loadRecetaDetail(recetaId: Int) {
        viewModelScope.launch {
            val receta = recetaRepository.getRecetaById(recetaId)
            _uiState.value = RecetaDetailsState(receta)
        }
    }

    fun toggleFavorite(receta: Receta) {
        viewModelScope.launch {
            val updatedReceta = receta.copy(isFavorite = !receta.isFavorite)
            recetaRepository.updateReceta(updatedReceta.mapToEntity())

            val nuevaReceta = recetaRepository.getRecetaById(updatedReceta.id)
            _uiState.value = RecetaDetailsState(nuevaReceta)
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val db = RoomDependencies.provideDatabase(application)
                val repository = RecetaRepository(db.recipieDAO())
                RecetasDetailViewModel(repository)
            }
        }
    }
}


