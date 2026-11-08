package com.example.appdummy.screens.listado

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import com.example.appdummy.data.model.Libro
import com.example.appdummy.data.repository.LibrosRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaListado(
    viewModel: LibrosViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val busqueda by viewModel.busqueda.collectAsStateWithLifecycle()
    val autores by viewModel.autores.collectAsStateWithLifecycle()
    val autorSeleccionado by viewModel.autorSeleccionado.collectAsStateWithLifecycle()
    val autoresDisponibles = listOf("Todos") + autores

    val libros = (uiState as? LibrosUiState.Exito)?.libros
        ?: emptyList()

    val librosFiltrados = libros.filter { libro ->

        val coincideBusqueda =
            libro.titulo.contains(busqueda, ignoreCase = true) ||
                    libro.autor.contains(busqueda, ignoreCase = true)

        val coincideAutor =
            autorSeleccionado == "Todos" ||
                    libro.autor == autorSeleccionado

        coincideBusqueda && coincideAutor
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var snackbarJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(Unit) {
        viewModel.eventos.collect { evento ->
            when (evento) {
                is LibrosEvento.MostrarSnackbar -> {
                    // Cancelar el Snackbar anterior si existe
                    snackbarJob?.cancel()

                    // Descartar el Snackbar visible
                    snackbarHostState.currentSnackbarData?.dismiss()

                    // Mostrar el nuevo Snackbar
                    snackbarJob = scope.launch {
                        snackbarHostState.showSnackbar(evento.mensaje)
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text("AppDummy") },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            Icons.Default.AccountCircle,
                            contentDescription = "Perfil"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        when (val estado = uiState) {

            is LibrosUiState.Cargando -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is LibrosUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.ErrorOutline,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.error
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(
                            text = estado.mensaje,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            is LibrosUiState.Exito -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {

                    OutlinedTextField(
                        value = busqueda,
                        onValueChange = {
                            viewModel.actualizarBusqueda(it)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        placeholder = {
                            Text("Buscar libros...")
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = null
                            )
                        },
                        trailingIcon = {
                            AnimatedVisibility(
                                visible = busqueda.isNotEmpty()
                            ) {
                                IconButton(
                                    onClick = {
                                        viewModel.actualizarBusqueda("")
                                    }
                                ) {
                                    Icon(
                                        Icons.Default.Clear,
                                        contentDescription = "Borrar búsqueda"
                                    )
                                }
                            }
                        },
                        singleLine = true
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        items(autoresDisponibles) { autor ->
                            FilterChip(
                                selected = autor == autorSeleccionado,
                                onClick = {
                                    viewModel.seleccionarAutor(autor)
                                },
                                label = {
                                    Text(autor)
                                }
                            )
                        }
                    }

                    if (librosFiltrados.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    Icons.Default.SearchOff,
                                    contentDescription = null,
                                    modifier = Modifier.size(64.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Spacer(Modifier.height(16.dp))

                                Text(
                                    "Sin resultados para \"$busqueda\"",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                    } else {
                        LazyVerticalGrid(
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(horizontal = 8.dp),
                            columns = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                librosFiltrados,
                                key = { it.id }
                            ) { libro ->

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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemLibroPreview() {
    MaterialTheme {
        ItemLibro(
            libro = Libro(
                id = 1,
                titulo = "Proyecto Hail Mary",
                autor = "Andy Weir",
                year = 2021,
                isbn = "9788418037016",
                esFavorito = true
            ),
            onToggleLeido = {},
            onToggleFavorito = {}
        )
    }
}