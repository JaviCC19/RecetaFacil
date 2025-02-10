package com.myproject.JavierCifuentes.Presentation.Screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myproject.JavierCifuentes.Data.Local.Receta
import com.myproject.JavierCifuentes.Presentation.ViewModels.RecetasViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RecetasRoute(
    onRecetaClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecetasViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val recetasFiltradas = uiState.recetas
        .filter { if (uiState.filtroFavoritos) it.isFavorite else true }
        .sortedBy { if (uiState.filtroPorTiempo) it.tiempo else it.nombre.length }

    Column {
        if (recetasFiltradas.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No hay recetas todavía", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            RecetasScreen(
                recetas = recetasFiltradas,
                onRecetaClick = onRecetaClick,
                onToggleFavorite = { viewModel.toggleFavorite(it) },
                modifier = modifier,
                onFiltrarFavoritos = { viewModel.toggleFiltroFavoritos() },
                onFiltrarTiempo = { viewModel.toggleFiltroPorTiempo() },
                filtroFavoritosActivo = uiState.filtroFavoritos,
                filtroTiempoActivo = uiState.filtroPorTiempo,
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
                containerColor = if (filtroFavoritosActivo) Color.Green else Color.Gray
            )
        ) {
            Text(text = "Favoritos")
        }

        Button(
            onClick = onFiltrarTiempo,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (filtroTiempoActivo) Color.Green else Color.Gray
            )
        ) {
            Text(text = "Ordenar por tiempo")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecetasScreen(
    recetas: List<Receta>,
    onRecetaClick: (Int) -> Unit,
    onToggleFavorite: (Receta) -> Unit,
    modifier: Modifier = Modifier,
    onFiltrarFavoritos: () -> Unit,
    onFiltrarTiempo: () -> Unit,
    filtroFavoritosActivo: Boolean,
    filtroTiempoActivo: Boolean,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recetas") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* Agregar nueva receta */ },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar receta")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Filtros(
                onFiltrarFavoritos = {onFiltrarFavoritos() },
                onFiltrarTiempo = { onFiltrarTiempo() },
                filtroFavoritosActivo = filtroFavoritosActivo,
                filtroTiempoActivo = filtroTiempoActivo
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(recetas) { receta ->
                    RecetaCard(receta, onToggleFavorite)
                }
            }
        }
    }
}

@Composable
fun RecetaCard(receta: Receta, onToggleFavorite: (Receta) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
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
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecetasScreenEmptyPreview() {
    RecetasScreen(
        recetas = emptyList(),
        onRecetaClick = {},
        onToggleFavorite = {},
        onFiltrarFavoritos = {},
        onFiltrarTiempo = {},
        filtroFavoritosActivo = false,
        filtroTiempoActivo = false
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewRecetasScreen() {
    var recetas by remember {
        mutableStateOf(
            listOf(
                Receta(
                    "Pasta Carbonara",
                    "Deliciosa pasta con huevo y tocino",
                    imagenRes = 0,
                    ingredientes = listOf("Pasta", "Huevo", "Tocino", "Queso"),
                    pasos = listOf("Cocinar la pasta", "Mezclar con huevo", "Agregar tocino"),
                    tiempo = 5,
                    isFavorite = false
                ),
                Receta(
                    "Ensalada César",
                    "Ensalada con pollo y aderezo especial",
                    imagenRes = 0,
                    ingredientes = listOf("Lechuga", "Pollo", "Crutones", "Aderezo César"),
                    pasos = listOf("Cortar ingredientes", "Mezclar", "Agregar aderezo"),
                    tiempo = 10,
                    isFavorite = true
                )
            )

        )
    }

    var filtroFavoritos by remember { mutableStateOf(false) }
    var filtroPorTiempo by remember { mutableStateOf(false) }

    val recetasFiltradas = recetas
        .let { if (filtroFavoritos) it.filter { it.isFavorite } else it }
        .let { if (filtroPorTiempo) it.sortedBy { it.tiempo } else it.sortedBy { it.nombre.length } }

    RecetasScreen(
        recetas = recetasFiltradas,
        onRecetaClick = { /* Acción al hacer click en una receta */ },
        onToggleFavorite = { receta ->
            recetas = recetas.map { if (it.nombre == receta.nombre) it.copy(isFavorite = !it.isFavorite) else it }
        },
        onFiltrarFavoritos = {
            filtroFavoritos = !filtroFavoritos
        },
        onFiltrarTiempo = {
            filtroPorTiempo = !filtroPorTiempo
        },
        filtroFavoritosActivo = filtroFavoritos,
        filtroTiempoActivo = filtroPorTiempo
    )
}




