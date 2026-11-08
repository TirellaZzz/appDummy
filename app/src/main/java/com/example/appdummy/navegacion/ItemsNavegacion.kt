package com.example.appdummy.navegacion

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.MenuBook
data class ItemNavegacion(
    val ruta: Any,
    val etiqueta: String,
    val icono: ImageVector,
    val iconoSeleccionado: ImageVector
)

val itemsNavegacionPrincipal = listOf(
    ItemNavegacion(
        ruta = Listado,
        etiqueta = "Catálogo",
        icono = Icons.Outlined.MenuBook,
        iconoSeleccionado = Icons.Filled.MenuBook
    ),
    ItemNavegacion(
        ruta = Favoritos,
        etiqueta = "Favoritos",
        icono = Icons.Outlined.FavoriteBorder,
        iconoSeleccionado = Icons.Filled.Favorite
    ),
    ItemNavegacion(
        ruta = Leidos,
        etiqueta = "Leídos",
        icono = Icons.Outlined.BookmarkBorder,
        iconoSeleccionado = Icons.Filled.Bookmark
    )
)
