package com.myproject.JavierCifuentes.Data.local.repositories

import com.myproject.JavierCifuentes.Data.local.Dao.RecipieDAO
import com.myproject.JavierCifuentes.Data.local.Entity.RecetaEntity


class RecetaRepository(private val recipieDAO: RecipieDAO) {

    suspend fun insertReceta(receta: RecetaEntity) {
        recipieDAO.insertReceta(receta)
    }

    suspend fun getRecetaById(recetaId: Int): RecetaEntity? {
        return recipieDAO.getRecetaById(recetaId)
    }

    suspend fun actualizarImagenReceta(recetaId: Int, imagenUri: String) {
            recipieDAO.actualizarImagen(recetaId, imagenUri)
        }

    suspend fun updateReceta(receta: RecetaEntity) {
        recipieDAO.updateReceta(receta)
    }

    suspend fun getUltimaRecetaId(): Int {
        return recipieDAO.getUltimaRecetaId()
    }

}

