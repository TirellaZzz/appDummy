package com.example.appdummy.screens.listado

import com.example.appdummy.data.model.Libro

sealed class LibrosUiState {

    data object Cargando : LibrosUiState()

    data class Exito(
        val libros: List<Libro>
    ) : LibrosUiState()

    data class Error(
        val mensaje: String
    ) : LibrosUiState()
}