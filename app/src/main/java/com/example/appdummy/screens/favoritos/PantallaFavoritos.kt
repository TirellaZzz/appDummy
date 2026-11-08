package com.example.appdummy.screens.favoritos

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appdummy.screens.listado.LibrosUiState
import com.example.appdummy.screens.listado.LibrosViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import com.example.appdummy.screens.listado.ItemLibro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaFavoritos(
    viewModel: LibrosViewModel
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
                        }
                    )
                }
            }
        }
    }
}