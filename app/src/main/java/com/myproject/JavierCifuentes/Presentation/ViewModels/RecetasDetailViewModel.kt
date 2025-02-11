package com.myproject.JavierCifuentes.Presentation.ViewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myproject.JavierCifuentes.Data.local.Dao.RecipieDAO
import com.myproject.JavierCifuentes.Data.local.Domain.Receta
import com.myproject.JavierCifuentes.Data.local.Entity.RecetaEntity
import com.myproject.JavierCifuentes.Data.local.Entity.mapToModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateRecetaViewModel(private val recipieDAO: RecipieDAO) : ViewModel() {

    private val _uiState = MutableStateFlow<Receta?>(null)
    val uiState: StateFlow<Receta?> = _uiState.asStateFlow()

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
            recipieDAO.insertReceta(nuevaReceta)
        }
    }

    fun loadRecetaDetail(recetaId: Int) {
        viewModelScope.launch {
            val receta = recipieDAO.getRecetaById(recetaId)
            receta?.let {
                nombre = it.nombre
                descripcion = it.descripcion
                imagenRes = it.imagenRes
                ingredientes = it.ingredientes ?: ""
                pasos = it.pasos ?: ""
                tiempo = it.tiempo
                isFavorite = it.isFavorite

                _uiState.value = it.mapToModel()
            }
        }
    }
}
