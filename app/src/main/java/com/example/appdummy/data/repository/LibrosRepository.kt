package com.example.appdummy.data.repository

import com.example.appdummy.data.model.Libro
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class LibrosRepository {

    private val libros = listOf(
        Libro(
            id = 1,
            titulo = "Proyecto Hail Mary",
            autor = "Andy Weir",
            year = 2021,
            isbn = "9788418037016",
            cover = "https://covers.openlibrary.org/b/isbn/9788418037016-L.jpg",
            esFavorito = true
        ),
        Libro(
            id = 2,
            titulo = "Juego de tronos",
            autor = "George R.R. Martin",
            year = 1996,
            isbn = "9780307951182",
            cover = "https://covers.openlibrary.org/b/isbn/9780307951182-L.jpg",
            esFavorito = true,
            leido = true
        ),
        Libro(
            id = 3,
            titulo = "Festín de cuervos",
            autor = "George R.R. Martin",
            year = 2005,
            isbn = "9780307951212"
        ),
        Libro(
            id = 4,
            titulo = "Cementerio de Animales",
            autor = "Stephen King",
            year = 1983,
            isbn = "9788401499845",
            leido = true
        ),
        Libro(
            id = 5,
            titulo = "El juego de Ender",
            autor = "Orson Scott Card",
            year = 1985,
            isbn = "9788498720068",
            leido = true
        )
    )

    suspend fun getLibros(): List<Libro> {
        delay(800.milliseconds)
        return libros
    }

    suspend fun getAutores(): List<String> {
        delay(300.milliseconds)
        return libros.map { it.autor }.distinct().sorted()
    }

    suspend fun getLibroPorId(id: Int): Libro? {
        delay(300.milliseconds)
        return libros.find { it.id == id }
    }
}