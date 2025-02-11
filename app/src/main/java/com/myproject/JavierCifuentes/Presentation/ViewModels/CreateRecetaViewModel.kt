package com.myproject.JavierCifuentes.Presentation.ViewModels

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateRecetaViewModel(private val recetaRepository: RecetaRepository) : ViewModel() {

    var nombre by mutableStateOf("")
    var descripcion by mutableStateOf("")
    var imagenRes by mutableStateOf(0)
    var ingredientes by mutableStateOf("")
    var pasos by mutableStateOf("")
    var tiempo by mutableStateOf(0)
    var isFavorite by mutableStateOf(false)

    fun saveReceta() {
        viewModelScope.launch {
            val nuevaReceta = RecetaEntity(
                nombre = nombre,
                descripcion = descripcion,
                imagenRes = imagenRes,
                ingredientes = ingredientes,
                pasos = pasos,
                tiempo = tiempo,
                isFavorite = isFavorite
            )
            recetaRepository.insertReceta(nuevaReceta)
        }
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
