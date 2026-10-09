# AppDummy v1 — G-APPDUMMY-V1

Brian Falcon · 13539727

## Qué hace

AppDummy es una aplicación Android desarrollada con Kotlin y Jetpack Compose para gestionar un catálogo de libros. Permite consultar las portadas, los títulos, los autores y los años de publicación de varios libros. Incluye búsqueda por título, filtrado por autor, gestión de favoritos y control de lectura. También permite compartir información de los libros y gestionar el permiso de la cámara.

## Estructura

- `MainActivity.kt`: actividad principal que inicia la aplicación y aplica el tema visual.
- `screens/PantallaBienvenida.kt`: pantalla de bienvenida, validación del nombre y acceso a la aplicación.
- `screens/PantallaListado.kt`: modelo `LibroUI`, datos de ejemplo, búsqueda, filtros, tarjetas de libros, favoritos, lectura y acción de compartir.
- `screens/PantallaGestionPermisos.kt`: solicitud y gestión del permiso de cámara.
- `ui/theme/Theme.kt`: configuración del tema visual de AppDummy.
- `AndroidManifest.xml`: declaración de permisos y configuración de la aplicación.

## Dependencias añadidas

| Librería | Para qué |
|---|---|
| `androidx.compose.material:material-icons-extended` | Proporciona iconos para la interfaz. |
| `io.coil-kt.coil3:coil-compose` | Permite cargar imágenes en componentes de Compose. |
| `io.coil-kt.coil3:coil-network-okhttp` | Permite descargar portadas desde direcciones URL mediante Coil. |

## Permisos declarados

| Permiso | Por qué es necesario |
|---|---|
| `android.permission.INTERNET` | Permite descargar las portadas de los libros desde Internet. |
| `android.permission.CAMERA` | Permite solicitar acceso a la cámara para la funcionalidad prevista de captura de portadas. |

## Decisiones propias

1. Cambio en la variable busqueda con rememberSaveable en PantallaListado.kt 
2. Uso de CenterAlignedTopAppBar en PantallaBienvenida.kt
3. Emoji de libro leido y sin leer cambiado 


## Limitaciones conocidas

- Los libros se almacenan en una lista local de datos de ejemplo; todavía no se utiliza una base de datos para guardar los cambios de forma permanente.
- La funcionalidad de cámara se centra en la gestión del permiso; no se ha implementado la captura de fotografías de portadas.
- Las portadas remotas dependen de la conexión a Internet. Cuando no hay una URL válida, se muestra una imagen por defecto.
