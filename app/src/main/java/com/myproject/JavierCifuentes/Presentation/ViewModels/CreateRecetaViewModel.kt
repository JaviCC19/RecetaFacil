package com.myproject.JavierCifuentes.Presentation.ViewModels

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

import com.myproject.JavierCifuentes.Data.local.Dao.RecipieDAO
import com.myproject.JavierCifuentes.Data.local.Domain.Receta
import com.myproject.JavierCifuentes.Data.local.Entity.RecetaEntity
import com.myproject.JavierCifuentes.Data.local.repositories.RecetaRepository
import com.myproject.JavierCifuentes.Domain.Di.RoomDependencies
import com.myproject.JavierCifuentes.Presentation.States.CreateRecetaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.myproject.JavierCifuentes.Data.local.Entity.mapToModel
import com.myproject.JavierCifuentes.Data.local.Entity.mapToEntity
import io.ktor.http.ContentDisposition.Companion.File
import kotlinx.coroutines.launch
import java.io.File

class CreateRecetaViewModel(private val recetaRepository: RecetaRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateRecetaUiState())
    val uiState: StateFlow<CreateRecetaUiState> = _uiState

    fun onNombreChange(newNombre: String) {
        _uiState.value = _uiState.value.copy(nombre = newNombre)
    }

    fun onDescripcionChange(newDescripcion: String) {
        _uiState.value = _uiState.value.copy(descripcion = newDescripcion)
    }

    fun onIngredientesChange(newIngredientes: String) {
        _uiState.value = _uiState.value.copy(ingredientes = newIngredientes)
    }

    fun onPasosChange(newPasos: String) {
        _uiState.value = _uiState.value.copy(pasos = newPasos)
    }

    fun onTiempoChange(newTiempo: String) {
        _uiState.value = _uiState.value.copy(tiempo = newTiempo.toIntOrNull() ?: 0)
    }

    fun onFavoriteChange(isFav: Boolean) {
        _uiState.value = _uiState.value.copy(isFavorite = isFav)
    }

    fun onImageSelected(uri: Uri) {
        _uiState.value = _uiState.value.copy(imagenUri = uri.toString())
    }

    fun saveReceta(onSuccess: () -> Unit) {
        if (_uiState.value.nombre.isEmpty()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Debe ingresar un nombre para la receta")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSaving = true)

            val nuevaReceta = RecetaEntity(
                nombre = _uiState.value.nombre,
                descripcion = _uiState.value.descripcion,
                imagenUri = "", // Se actualizará después
                ingredientes = _uiState.value.ingredientes,
                pasos = _uiState.value.pasos,
                tiempo = _uiState.value.tiempo,
                isFavorite = _uiState.value.isFavorite
            )

            recetaRepository.insertReceta(nuevaReceta)
            val recetaId = recetaRepository.getUltimaRecetaId()

            _uiState.value.imagenUri?.let { uri ->
                recetaRepository.actualizarImagenReceta(recetaId, uri)
            }

            _uiState.value = _uiState.value.copy(isSaving = false)
            onSuccess()
        }
    }


    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }




    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = checkNotNull(this[APPLICATION_KEY])
                val db = RoomDependencies.provideDatabase(application)
                val repository = RecetaRepository(db.recipieDAO())
                CreateRecetaViewModel(repository)
            }
        }
    }
}
