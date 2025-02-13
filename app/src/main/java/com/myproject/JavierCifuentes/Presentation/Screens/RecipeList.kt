package com.myproject.JavierCifuentes.Presentation.Screens

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
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
    val menuExpanded by viewModel.menuExpanded.collectAsState()

    val recetasFiltradas = uiState.recetas
        .filter { if (filtroFavoritos) it.isFavorite else true }
        .sortedBy { if (filtroTiempo) it.tiempo else it.nombre.length }

    val onCerrarSesion: () -> Unit = {
        viewModel.cerrarSesion()
    }

    val onToggleMenu: () -> Unit = {
        viewModel.toggleMenu()
    }

    RecetasScreen(
        recetas = recetasFiltradas,
        onRecetaClick = onRecetaClick,
        onToggleFavorite = { viewModel.toggleFavorite(it) },
        onFiltarTiempo = { viewModel.toggleFiltroPorTiempo() },
        onFiltrarFavoritos = { viewModel.toggleFiltroFavoritos() },
        onDeleteReceta = { viewModel.deleteReceta(it.id) },
        filtroFavoritosActivo = filtroFavoritos,
        filtroTiempoActivo = filtroTiempo,
        onCrearRecetaClick = onCrearRecetaClick,
        onCerrarSesion = onCerrarSesion,
        menuExpanded = menuExpanded,
        onToggleMenu = onToggleMenu
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
        FilterChip(
            selected = filtroFavoritosActivo,
            onClick = onFiltrarFavoritos,
            label = { Text(text = stringResource(R.string.filtro_Favoritos)) },
            leadingIcon = if (filtroFavoritosActivo) {
                { Icon(Icons.Default.Star, contentDescription = null) }
            } else null,
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                labelColor = MaterialTheme.colorScheme.onSurface
            )
        )

        FilterChip(
            selected = filtroTiempoActivo,
            onClick = onFiltrarTiempo,
            label = { Text(text = stringResource(R.string.Filtro_temp)) },
            leadingIcon = if (filtroTiempoActivo) {
                { Icon(Icons.Default.Timer, contentDescription = null) }
            } else null,
            colors = FilterChipDefaults.filterChipColors(
                selectedContainerColor = MaterialTheme.colorScheme.primary,
                selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                labelColor = MaterialTheme.colorScheme.onSurface
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetasScreen(
    recetas: List<Receta>,
    onRecetaClick: (Int) -> Unit,
    onToggleFavorite: (Receta) -> Unit,
    onFiltrarFavoritos: () -> Unit,
    onFiltarTiempo: () -> Unit,
    onDeleteReceta: (Receta) -> Unit,
    filtroFavoritosActivo: Boolean,
    filtroTiempoActivo: Boolean,
    onCrearRecetaClick: () -> Unit,
    onCerrarSesion: () -> Unit,
    menuExpanded: Boolean,
    onToggleMenu: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(stringResource(R.string.Recetas))
                        IconButton(onClick = onToggleMenu) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menú")
                        }
                    }
                },
                actions = {
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = onToggleMenu
                    ) {
                        DropdownMenuItem(
                            text = { Text(stringResource(R.string.cerrarSesion)) },
                            onClick = {
                                onToggleMenu()
                                onCerrarSesion()
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
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
                onFiltrarTiempo = onFiltarTiempo,
                filtroFavoritosActivo = filtroFavoritosActivo,
                filtroTiempoActivo = filtroTiempoActivo
            )

            if (recetas.isEmpty() && filtroFavoritosActivo) {
                MensajeSinRecetas(
                    mensaje = stringResource(R.string.no_favoritos),
                    onCrearRecetaClick = onFiltrarFavoritos
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(recetas) { receta ->
                        RecetaCard(receta, onToggleFavorite, onDeleteReceta, onRecetaClick)
                    }
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


            if (!receta.imagenUri.isNullOrEmpty()) {
                val uri = Uri.parse(receta.imagenUri)
                AsyncImage(
                    model = uri,
                    contentDescription = "Imagen de la receta",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }


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

@Composable
fun MensajeSinRecetas(
    mensaje: String,
    onCrearRecetaClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = mensaje,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(onClick = onCrearRecetaClick) {
                Text(text = stringResource(R.string.crearReceta))
            }
        }
    }
}




