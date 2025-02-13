package com.myproject.JavierCifuentes.Presentation.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myproject.JavierCifuentes.Data.local.Domain.Receta
import com.myproject.JavierCifuentes.Presentation.ViewModels.RecetasViewModel
import com.myproject.JavierCifuentes.R


@Composable
fun RecetasRoute(
    onRecetaClick: (Int) -> Unit,
    onCrearRecetaClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecetasViewModel = viewModel(factory = RecetasViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsState()
    val filtroFavoritos by viewModel.filtroFavoritos.collectAsState()
    val filtroTiempo by viewModel.filtroTiempo.collectAsState()

    val recetasFiltradas = uiState.recetas
        .filter { if (filtroFavoritos) it.isFavorite else true }
        .sortedBy { if (filtroTiempo) it.tiempo else it.nombre.length }
    val onCerrarSesion: () -> Unit = {
        viewModel.cerrarSesion()
    }

    Column {
        if (recetasFiltradas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = stringResource(R.string.No_recipes),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Button(onClick = onCrearRecetaClick) {
                        //TODO: change this to a resource
                        Text(text = "Crear nueva receta")
                    }
                }
            }
        } else {
            RecetasScreen(
                recetas = recetasFiltradas,
                onRecetaClick = onRecetaClick,
                onToggleFavorite = { viewModel.toggleFavorite(it) },
                onDeleteReceta = { viewModel.deleteReceta(it.id) },
                onFiltrarFavoritos = { viewModel.toggleFiltroFavoritos() },
                onFiltrarTiempo = { viewModel.toggleFiltroPorTiempo() },
                filtroFavoritosActivo = filtroFavoritos,
                filtroTiempoActivo = filtroTiempo,
                onCrearRecetaClick = onCrearRecetaClick,
                onCerrarSesion = onCerrarSesion
            )
        }
    }
}

@Composable
fun Filtros(
    onFiltrarFavoritos: () -> Unit,
    onFiltrarTiempo: () -> Unit,
    filtroFavoritosActivo: Boolean,
    filtroTiempoActivo: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onFiltrarFavoritos,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (filtroFavoritosActivo) MaterialTheme.colorScheme.onPrimaryContainer else Color.Gray
            )
        ) {
            Text(text = "Favoritos")
        }

        Button(
            onClick = onFiltrarTiempo,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (filtroTiempoActivo) MaterialTheme.colorScheme.onPrimaryContainer else Color.Gray
            )
        ) {
            Text(text = stringResource(R.string.Filtro_temp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetasScreen(
    recetas: List<Receta>,
    onRecetaClick: (Int) -> Unit,
    onToggleFavorite: (Receta) -> Unit,
    onDeleteReceta: (Receta) -> Unit,
    onFiltrarFavoritos: () -> Unit,
    onFiltrarTiempo: () -> Unit,
    filtroFavoritosActivo: Boolean,
    filtroTiempoActivo: Boolean,
    onCrearRecetaClick: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    Scaffold(
        topBar = {

            TopAppBar(
                title = { Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(stringResource(R.string.Recetas))

                    IconButton(onClick = onCerrarSesion) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menú") }
                }
                   } ,
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),


            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCrearRecetaClick,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar receta")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Filtros(
                onFiltrarFavoritos = onFiltrarFavoritos,
                onFiltrarTiempo = onFiltrarTiempo,
                filtroFavoritosActivo = filtroFavoritosActivo,
                filtroTiempoActivo = filtroTiempoActivo
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(recetas) { receta ->
                    RecetaCard(receta, onToggleFavorite, onDeleteReceta, onRecetaClick)
                }
            }
        }
    }
}



@Composable
fun RecetaCard(
    receta: Receta,
    onToggleFavorite: (Receta) -> Unit,
    onDeleteReceta: (Receta) -> Unit,
    onClick: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(receta.id) }
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = receta.nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(text = receta.descripcion, modifier = Modifier.padding(bottom = 4.dp))
            Text(
                text = "Tiempo: ${receta.tiempo} min",
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onToggleFavorite(receta) }) {
                    Icon(
                        imageVector = if (receta.isFavorite) Icons.Filled.Star else Icons.Outlined.Star,
                        contentDescription = "Favorito",
                        tint = if (receta.isFavorite) Color.Yellow else Color.Gray
                    )
                }
                IconButton(onClick = { onDeleteReceta(receta) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar receta",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}






