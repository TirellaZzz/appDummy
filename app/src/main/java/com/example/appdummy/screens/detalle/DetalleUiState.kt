package com.example.appdummy.screens.detalle

import com.example.appdummy.data.model.Libro

sealed class DetalleUiState {
    data object Cargando : DetalleUiState()
    data class Exito(val libro: Libro) : DetalleUiState()
    data object NoEncontrado : DetalleUiState()
}