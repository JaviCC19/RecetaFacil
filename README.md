 
# ReceFacil

ReceFacil es una aplicación móvil para gestionar recetas de cocina. Permite a los usuarios crear, editar, eliminar y visualizar recetas, así como marcar sus recetas favoritas.

## Características

- **Inicio de sesión**: Los usuarios pueden iniciar sesión en la aplicación.
- **Lista de recetas**: Visualiza una lista de todas las recetas disponibles.
- **Detalle de receta**: Muestra los detalles de una receta seleccionada.
- **Crear receta**: Permite a los usuarios crear nuevas recetas.
- **Editar receta**: Los usuarios pueden editar recetas existentes.
- **Eliminar receta**: Los usuarios pueden eliminar recetas.
- **Favoritos**: Los usuarios pueden marcar recetas como favoritas.
- **Filtros**: Filtra recetas por tiempo de preparación y favoritos.

## Requisitos

- Android Studio Koala Feature Drop | 2024.1.2
- Kotlin
- Gradle

## Instalación

1. Clona el repositorio:
    ```sh
    git clone https://github.com/JaviCC19/ReceFacil.git
    ```
2. Abre el proyecto en Android Studio.
3. Sincroniza el proyecto con Gradle.
4. Configura el archivo `google-services.json` en la carpeta `app`.

## Uso

### Navegación

La aplicación utiliza `NavHost` para gestionar la navegación entre pantallas. Las pantallas principales incluyen:

- `LoginScreen`: Pantalla de inicio de sesión.
- `RecetasListScreen`: Lista de recetas.
- `RecetaDetailScreen`: Detalle de una receta.
- `CrearRecetaScreen`: Crear una nueva receta.



### Temas

La aplicación utiliza `MaterialTheme` para aplicar temas consistentes en toda la aplicación.

