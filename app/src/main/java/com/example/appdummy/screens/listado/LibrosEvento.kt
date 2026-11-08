package com.example.appdummy.screens.listado


/**
 * Eventos de un solo disparo.
 * No forman parte del estado permanente de la interfaz.
 */
sealed class LibrosEvento {
    data class MostrarSnackbar(val mensaje: String) : LibrosEvento()
}