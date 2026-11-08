# AppDummy v2 — G-APPDUMMY-V2

Brian Falcon · 13539727

## Qué hace

AppDummy es una aplicación Android desarrollada con Kotlin y Jetpack Compose que permite gestionar una biblioteca personal. Incluye un catálogo de libros con búsqueda por título, filtrado por autor, gestión de favoritos y control de lectura. Cada libro dispone de una pantalla de detalle. También cuenta con una pantalla de favoritos, una pantalla de gestión del permiso de cámara y navegación mediante una barra inferior.

En esta segunda versión se incorpora la arquitectura MVVM, utilizando ViewModel, StateFlow y SharedFlow, además de Navigation Compose para gestionar la navegación con una única Activity.

## Arquitectura

- `data/model/Libro.kt`: modelo de datos que representa un libro.
- `data/repository/LibrosRepository.kt`: proporciona los datos de los libros.
- `screens/listado/LibrosViewModel.kt`: gestiona el estado del catálogo, las búsquedas, los filtros, favoritos y libros leídos.
- `screens/listado/LibrosUiState.kt`: representa el estado de la pantalla del catálogo.
- `screens/detalle/DetalleViewModel.kt`: gestiona los datos del libro seleccionado mediante SavedStateHandle.
- `screens/`: contiene las pantallas de bienvenida, listado, detalle, favoritos y permisos.
- `navegacion/`: contiene las rutas, AppNavigation y la barra inferior.
- `MainActivity.kt`: única Activity que inicia la aplicación.

## Grafo de navegación

- Bienvenida → Listado.
- Listado ↔ Favoritos mediante la barra inferior.
- Listado / Favoritos → Detalle → pantalla de origen.
- Listado → Permisos → Listado.

La navegación se centraliza en `AppNavigation`. La barra inferior conserva el estado de las pestañas mediante `popUpTo`, `saveState`, `launchSingleTop` y `restoreState`.

## Dependencias añadidas

| Librería | Para qué |
|---|---|
| `lifecycle-viewmodel-compose` | Integración de ViewModel con Compose. |
| `lifecycle-runtime-compose` | Observación del estado mediante `collectAsStateWithLifecycle()`. |
| `kotlinx-coroutines-android` | Ejecución de operaciones mediante corrutinas. |
| `kotlinx-coroutines-test` | Pruebas de corrutinas. |
| `navigation-compose` | Navegación entre pantallas. |
| `kotlinx-serialization-json` | Serialización de rutas tipadas. |

## Decisiones propias

1. Compartir `LibrosViewModel` entre Listado y Favoritos para que los cambios realizados en una pantalla se reflejen inmediatamente en la otra.
2. Utilizar `popUpTo<Listado>` en la barra inferior porque Listado es la raíz real de navegación después de abandonar Bienvenida.
3. Mantener `AppDummyBottomBar` independiente de `NavController`, utilizando callbacks para que la navegación se gestione exclusivamente desde `AppNavigation`.

## Limitaciones conocidas

- Los favoritos y los libros leídos se mantienen en memoria y se pierden al cerrar la aplicación.
- Los datos de los libros proceden de un repositorio estático; todavía no se utiliza una base de datos.
- El detalle obtiene la información del repositorio, pero no refleja el estado de favorito del libro.
- La funcionalidad de cámara se limita a la gestión del permiso; no se implementa la captura de fotografías de portadas.