package com.myproject.JavierCifuentes.Presentation.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.input.TextFieldValue
import com.myproject.JavierCifuentes.Data.local.DataStore.DataStoreManager
import com.myproject.JavierCifuentes.Data.local.Domain.Receta
import com.myproject.JavierCifuentes.Presentation.States.CreateRecetaUiState
import com.myproject.JavierCifuentes.Presentation.ViewModels.CreateRecetaViewModel
import com.myproject.JavierCifuentes.R
import kotlinx.coroutines.launch

@Composable
fun CrearRecetaRoute(
    viewModel: CreateRecetaViewModel = viewModel(factory = CreateRecetaViewModel.Factory),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    CrearRecetaScreen(
        uiState = uiState,
        onNombreChange = viewModel::onNombreChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        onImagenResChange = viewModel::onImagenResChange,
        onIngredientesChange = viewModel::onIngredientesChange,
        onPasosChange = viewModel::onPasosChange,
        onTiempoChange = viewModel::onTiempoChange,
        onFavoriteChange = viewModel::onFavoriteChange,
        onSave = { viewModel.saveReceta(onNavigateBack) }
    )
}
@Composable
fun CrearRecetaScreen(
    uiState: CreateRecetaUiState,
    onNombreChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onImagenResChange: (String) -> Unit,
    onIngredientesChange: (String) -> Unit,
    onPasosChange: (String) -> Unit,
    onTiempoChange: (String) -> Unit,
    onFavoriteChange: (Boolean) -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = uiState.nombre,
            onValueChange = onNombreChange,
            label = { Text(stringResource(R.string.nombreReceta)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.descripcion,
            onValueChange = onDescripcionChange,
            label = { Text(stringResource(R.string.descripcionReceta)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.imagenRes.toString(),
            onValueChange = onImagenResChange,
            //TODO: take out dummy data
            label = { Text("ID de imagen (Int)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.ingredientes,
            onValueChange = onIngredientesChange,
            label = { Text(stringResource(R.string.ingredientesReceta)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.pasos,
            onValueChange = onPasosChange,
            label = { Text(stringResource(R.string.pasosReceta)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = uiState.tiempo.toString(),
            onValueChange = onTiempoChange,
            label = { Text(stringResource(R.string.tiempoReceta)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            //TODO: use string resources
            Text("Favorito")
            Switch(
                checked = uiState.isFavorite,
                onCheckedChange = onFavoriteChange
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            enabled = !uiState.isSaving,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            if (uiState.isSaving) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text(stringResource(R.string.guardarReceta))
            }
        }
    }
}
