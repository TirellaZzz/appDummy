package com.example.appdummy.screens.listado

import com.example.appdummy.MainDispatcherRule
import com.example.appdummy.data.repository.LibrosRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LibrosViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LibrosViewModel

    @Before
    fun setUp() {
        viewModel = LibrosViewModel(LibrosRepository())
    }

    @Test
    fun `el estado pasa de Cargando a Exito al terminar la carga`() = runTest {

        // Comprobamos el estado inicial
        assertTrue(viewModel.uiState.value is LibrosUiState.Cargando)

        // Adelantamos el tiempo virtual hasta completar las corrutinas
        advanceUntilIdle()

        // Comprobamos el resultado
        val estado = viewModel.uiState.value

        assertTrue("El estado debe ser Exito", estado is LibrosUiState.Exito)

        assertTrue(
            (estado as LibrosUiState.Exito).libros.isNotEmpty()
        )
    }

    @Test
    fun `toggleFavorito invierte el favorito del libro`() = runTest {

        advanceUntilIdle()

        val libro = (viewModel.uiState.value as LibrosUiState.Exito)
            .libros.first()

        viewModel.toggleFavorito(libro)

        advanceUntilIdle()

        val modificado = (viewModel.uiState.value as LibrosUiState.Exito)
            .libros.first { it.id == libro.id }

        assertNotEquals(libro.esFavorito, modificado.esFavorito)
    }
}