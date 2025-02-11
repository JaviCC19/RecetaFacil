package com.myproject.JavierCifuentes.Presentation.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.TextFieldValue
import com.myproject.JavierCifuentes.Data.local.Domain.Receta
import com.myproject.JavierCifuentes.Presentation.ViewModels.CreateRecetaViewModel

@Composable
fun CrearRecetaRoute(
    viewModel: CreateRecetaViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {


    CrearRecetaScreen(
        viewModel = viewModel,
        onSave = {
            viewModel.saveReceta()
            onNavigateBack()
        }
    )
}



@Composable
fun CrearRecetaScreen(viewModel: CreateRecetaViewModel, onSave: () -> Unit) {
    val context = LocalContext.current
    var isFavorite by remember { mutableStateOf(false) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.nombre = it },
            label = { Text("Nombre de la receta") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = viewModel.descripcion,
            onValueChange = { viewModel.descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = viewModel.imagenRes.toString(),
            onValueChange = { viewModel.imagenRes = it.toIntOrNull() ?: 0 },
            label = { Text("ID de imagen (Int)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        TextField(
            value = viewModel.ingredientes,
            onValueChange = { viewModel.ingredientes = it },
            label = { Text("Ingredientes") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = viewModel.pasos,
            onValueChange = { viewModel.pasos = it },
            label = { Text("Pasos de preparación") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = viewModel.tiempo.toString(),
            onValueChange = { viewModel.tiempo = it.toIntOrNull() ?: 0 },
            label = { Text("Tiempo (minutos)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Favorito")
            Switch(checked = isFavorite, onCheckedChange = { isFavorite = it })
        }

        Button(
            onClick = {

                Toast.makeText(context, "Receta guardada", Toast.LENGTH_SHORT).show()
                onSave()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Receta")
        }
    }
}


