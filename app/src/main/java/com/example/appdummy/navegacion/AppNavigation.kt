package com.example.appdummy.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appdummy.screens.bienvenida.PantallaBienvenida
import com.example.appdummy.screens.listado.PantallaListado
import com.example.appdummy.screens.favoritos.PantallaFavoritos
import com.example.appdummy.screens.permisos.PantallaGestionPermisos
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appdummy.screens.listado.LibrosViewModel

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val librosViewModel: LibrosViewModel = viewModel(
        factory = LibrosViewModel.Factory
    )
    NavHost(
        navController = navController,
        startDestination = Bienvenida
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

        composable<Permisos> {
            PantallaGestionPermisos()
        }
    }
}