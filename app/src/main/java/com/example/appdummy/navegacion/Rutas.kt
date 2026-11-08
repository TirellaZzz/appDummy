package com.example.appdummy.navegacion

import kotlinx.serialization.Serializable

@Serializable
data object Bienvenida

@Serializable
data object Listado

@Serializable
data object Favoritos

@Serializable
data object Permisos

@Serializable
data class Detalle(val id: Int)
