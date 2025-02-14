# ReceFacil

ReceFacil es una aplicación móvil para gestionar recetas de cocina, diseñada para facilitar la creación, organización y exploración de recetas de manera sencilla. Los usuarios pueden iniciar sesión, gestionar sus recetas y marcar sus favoritas para un acceso rápido.

## 📌 Características

- **Inicio de sesión**
- **Gestión de recetas**:
  - Crear y eliminar recetas fácilmente.
  - Marcar recetas como favoritas.
- **Visualización optimizada**:
  - Lista de recetas con opción de filtrado.
  - Vista detallada de cada receta.
- **Filtros avanzados**: Permite filtrar recetas por tiempo de preparación y estado de favorito.
- **Almacenamiento local**:
  - Uso de **Room** para gestionar la base de datos de recetas.
  - Uso de **DataStore** para persistir el usuario loggeado.

## 🛠️ Requisitos

- Android Studio **Koala Feature Drop | 2024.1.2**
- **Kotlin** como lenguaje principal.
- **Gradle** para la gestión de dependencias.

## 🚀 Instalación

1. Clona el repositorio en tu máquina local:
   ```sh
   git clone https://github.com/JaviCC19/ReceFacil.git
   ```
2. Abre el proyecto en **Android Studio**.
3. Sincroniza el proyecto con **Gradle**.
4. Agrega el archivo `google-services.json` en la carpeta `app` para configurar Firebase (si aplica).
5. Ejecuta la aplicación en un emulador o dispositivo físico.


## 📱 Uso de la Aplicación

### 📍 Navegación

ReceFacil utiliza `NavHost` para gestionar la navegación entre las diferentes pantallas:

- **`LoginScreen`** → Pantalla de inicio de sesión.
- **`RecetasListScreen`** → Muestra la lista de recetas.
- **`RecetaDetailScreen`** → Presenta los detalles de una receta seleccionada.
- **`CrearRecetaScreen`** → Permite crear una nueva receta.

### 🎨 Temas

- La aplicación utiliza **MaterialTheme** para aplicar un diseño coherente y moderno basado en Material Design.

---

