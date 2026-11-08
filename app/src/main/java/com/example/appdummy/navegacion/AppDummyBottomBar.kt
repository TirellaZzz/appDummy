package com.example.appdummy.navegacion

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy

@Composable
fun AppDummyBottomBar(
    destinoActual: NavDestination?,
    onSeleccionarItem: (Any) -> Unit
) {
    NavigationBar {
        itemsNavegacionPrincipal.forEach { item ->

            val seleccionado = destinoActual?.hierarchy?.any {
                it.hasRoute(item.ruta::class)
            } == true

            NavigationBarItem(
                selected = seleccionado,
                onClick = {
                    onSeleccionarItem(item.ruta)
                },
                icon = {
                    Icon(
                        imageVector = if (seleccionado) {
                            item.iconoSeleccionado
                        } else {
                            item.icono
                        },
                        contentDescription = item.etiqueta
                    )
                },
                label = {
                    Text(item.etiqueta)
                }
            )
        }
    }
}