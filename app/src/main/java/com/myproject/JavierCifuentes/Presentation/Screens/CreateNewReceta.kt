package com.myproject.JavierCifuentes.Presentation.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.myproject.JavierCifuentes.Domain.Util.copyImageToInternalStorage
import com.myproject.JavierCifuentes.Presentation.States.CreateRecetaUiState
import com.myproject.JavierCifuentes.Presentation.ViewModels.CreateRecetaViewModel
import com.myproject.JavierCifuentes.R





@Composable
fun CrearRecetaRoute(
    viewModel: CreateRecetaViewModel = viewModel(factory = CreateRecetaViewModel.Factory),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            val savedPath = copyImageToInternalStorage(context, selectedUri)
            savedPath?.let { viewModel.onImageSelected(Uri.parse(it)) }

        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            viewModel.clearError()
        }
    }

    CrearRecetaScreen(
        uiState = uiState,
        onNombreChange = viewModel::onNombreChange,
        onDescripcionChange = viewModel::onDescripcionChange,
        onIngredientesChange = viewModel::onIngredientesChange,
        onPasosChange = viewModel::onPasosChange,
        onTiempoChange = viewModel::onTiempoChange,
        onFavoriteChange = viewModel::onFavoriteChange,
        onImageSelected = { launcher.launch("image/*") },
        onSave = { viewModel.saveReceta(onNavigateBack) }
    )
}


@Composable
fun CrearRecetaScreen(
    uiState: CreateRecetaUiState,
    onNombreChange: (String) -> Unit,
    onDescripcionChange: (String) -> Unit,
    onIngredientesChange: (String) -> Unit,
    onPasosChange: (String) -> Unit,
    onTiempoChange: (String) -> Unit,
    onFavoriteChange: (Boolean) -> Unit,
    onImageSelected: () -> Unit,
    onSave: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        uiState.imagenUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(model = uri.toString()),
                contentDescription = "Imagen seleccionada",
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onImageSelected) {
            Text(text = "Seleccionar Imagen")
        }

        Spacer(modifier = Modifier.height(16.dp))

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
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    onTiempoChange(newValue)
                }
            },
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
