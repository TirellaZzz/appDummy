
package com.example.appdummy.screens.favoritos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3 .Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.appdummy.screens.listado.ItemLibro
import com.example.appdummy.screens.listado.LibrosUiState
import com.example.appdummy.screens.listado.LibrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFavoritos(
    viewModel: LibrosViewModel,
    onNavegaADetalle: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val librosFavoritos = when (val estado = uiState) {
        is LibrosUiState.Exito -> estado.libros.filter { it.esFavorito }
        else -> emptyList()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Mis favoritos")
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            if (librosFavoritos.isEmpty()) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Text(
                        text = "Todavía no tienes favoritos",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "Añade libros a favoritos para verlos aquí.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

            } else {

                Text("Tienes ${librosFavoritos.size} libros favoritos")

                LazyVerticalGrid(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(librosFavoritos) { libro ->
                        ItemLibro(
                            libro = libro,
                            onToggleLeido = { _ ->
                                viewModel.toggleLeido(libro)
                            },
                            onToggleFavorito = { _ ->
                                viewModel.toggleFavorito(libro)
                            },
                            onClick = {
                                onNavegaADetalle(libro.id)
                            }
                        )
                    }
                }
            }
        }
    }
}
