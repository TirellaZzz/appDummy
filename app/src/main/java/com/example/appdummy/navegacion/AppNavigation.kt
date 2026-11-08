package com.example.appdummy.navegacion

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.appdummy.screens.bienvenida.PantallaBienvenida
import com.example.appdummy.screens.favoritos.PantallaFavoritos
import com.example.appdummy.screens.leidos.PantallaLeidos
import com.example.appdummy.screens.listado.LibrosViewModel
import com.example.appdummy.screens.listado.PantallaListado
import com.example.appdummy.screens.permisos.PantallaGestionPermisos

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val librosViewModel: LibrosViewModel = viewModel(
        factory = LibrosViewModel.Factory
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val destinoActual = navBackStackEntry?.destination

    val mostrarBottomBar = itemsNavegacionPrincipal.any { item ->
        destinoActual?.hierarchy?.any {
            it.hasRoute(item.ruta::class)
        } == true
    }

    Scaffold(
        bottomBar = {
            if (mostrarBottomBar) {
                AppDummyBottomBar(
                    destinoActual = destinoActual,
                    onSeleccionarItem = { ruta ->
                        navController.navigate(ruta) {
                            popUpTo<Listado> {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Bienvenida,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable<Bienvenida> {
                PantallaBienvenida(
                    onEntrar = {
                        navController.navigate(Listado) {
                            popUpTo<Bienvenida> {
                                inclusive = true
                            }
                        }
                    }
                )
            }

            composable<Listado> {
                PantallaListado(
                    viewModel = librosViewModel
                )
            }

            composable<Favoritos> {
                PantallaFavoritos(
                    viewModel = librosViewModel
                )
            }

            composable<Leidos> {
                PantallaLeidos(
                    viewModel = librosViewModel
                )
            }

            composable<Permisos> {
                PantallaGestionPermisos()
            }
        }
    }
}

