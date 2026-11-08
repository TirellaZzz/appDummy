package com.example.appdummy.screens.listado

import androidx.lifecycle.ViewModel
import com.example.appdummy.data.repository.LibrosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.appdummy.data.model.Libro
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class LibrosViewModel (private val repository: LibrosRepository) : ViewModel(){
    private val _uiState = MutableStateFlow<LibrosUiState>(
        LibrosUiState.Cargando
    )
    val uiState: StateFlow<LibrosUiState> = _uiState.asStateFlow()

    private val _autores = MutableStateFlow<List<String>>(
        emptyList()
    )
    val autores: StateFlow<List<String>> = _autores.asStateFlow()

    private val _autorSeleccionado = MutableStateFlow(
        "Todos"
    )
    val autorSeleccionado: StateFlow<String> =
        _autorSeleccionado.asStateFlow()

    private val _eventos = MutableSharedFlow<LibrosEvento>(
        replay = 0,
        extraBufferCapacity = 1
    )

    val eventos: SharedFlow<LibrosEvento> = _eventos.asSharedFlow()

    private val _busqueda = MutableStateFlow(
        ""
    )
    val busqueda: StateFlow<String> = _busqueda.asStateFlow()

    init {
        cargarLibros()
        cargarAutores()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LibrosViewModel(
                    repository = LibrosRepository()
                )
            }
        }
    }

    val librosFiltrados: List<Libro>
        get() {
            val estado = _uiState.value

            if (estado !is LibrosUiState.Exito) {
                return emptyList()
            }

            return estado.libros.filter { libro ->
                val coincideBusqueda =
                    libro.titulo.contains(_busqueda.value, ignoreCase = true) ||
                            libro.autor.contains(_busqueda.value, ignoreCase = true)

                val coincideAutor =
                    _autorSeleccionado.value == "Todos" ||
                            libro.autor == _autorSeleccionado.value

                coincideBusqueda && coincideAutor
            }
        }

    private fun cargarLibros() {
        viewModelScope.launch {
            try {
                val libros = repository.getLibros()
                _uiState.value = LibrosUiState.Exito(libros)
            } catch (e: Exception) {
                _uiState.value = LibrosUiState.Error(
                    e.message ?: "Error al cargar los libros"
                )
            }
        }
    }

    private fun cargarAutores() {
        viewModelScope.launch {
            try {
                _autores.value = repository.getAutores()
            } catch (e: Exception) {
                _autores.value = emptyList()
            }
        }
    }

    fun seleccionarAutor(autor: String) {
        _autorSeleccionado.value = autor
    }

    fun actualizarBusqueda(texto: String) {
        _busqueda.value = texto
    }

    fun toggleFavorito(libro: Libro) {
        val estado = _uiState.value

        if (estado is LibrosUiState.Exito) {
            val librosActualizados = estado.libros.map { libroActual ->
                if (libroActual.id == libro.id) {
                    libroActual.copy(esFavorito = !libroActual.esFavorito)
                } else {
                    libroActual
                }
            }

            _uiState.value = LibrosUiState.Exito(librosActualizados)

            val mensaje = if (libro.esFavorito) {
                "\"${libro.titulo}\" eliminado de favoritos"
            } else {
                "\"${libro.titulo}\" añadido a favoritos"
            }

            viewModelScope.launch {
                _eventos.emit(LibrosEvento.MostrarSnackbar(mensaje))
            }
        }
    }

    fun toggleLeido(libro: Libro) {
        val estado = _uiState.value

        if (estado is LibrosUiState.Exito) {
            val librosActualizados = estado.libros.map { libroActual ->
                if (libroActual.id == libro.id) {
                    libroActual.copy(leido = !libroActual.leido)
                } else {
                    libroActual
                }
            }

            _uiState.value = LibrosUiState.Exito(librosActualizados)
        }
    }
}