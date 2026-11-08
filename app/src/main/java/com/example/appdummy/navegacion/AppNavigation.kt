package com.example.appdummy.navegacion

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appdummy.screens.bienvenida.PantallaBienvenida
import com.example.appdummy.screens.listado.PantallaListado
import com.example.appdummy.screens.favoritos.PantallaFavoritos
import com.example.appdummy.screens.permisos.PantallaGestionPermisos

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
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
            PantallaListado()
        }

        composable<Favoritos> {
            PantallaFavoritos()
        }

        composable<Permisos> {
            PantallaGestionPermisos()
        }
    }
}