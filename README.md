# Flux - Gestor de Finanzas Personales

<div align="center">

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white)

Una aplicación móvil moderna para gestionar tus finanzas personales de manera sencilla e intuitiva.

</div>

---

## Características

- **Onboarding Personalizado**: Configuración inicial con el nombre del usuario
- **Registro de Transacciones**: Añade ingresos y gastos con categorías personalizadas
- **Resumen Financiero**: Visualiza tu balance total y movimientos mensuales
- **Persistencia de Datos**: Almacenamiento local seguro con Room Database
- **Configuración**: Administra tu perfil y preferencias
- **Asistente IA**: (Próximamente) Chat inteligente para ayudarte con tus finanzas

## Arquitectura

El proyecto sigue las mejores prácticas de desarrollo Android moderno:

### Arquitectura MVVM (Model-View-ViewModel)
```
app/
├── data/               # Capa de datos
│   ├── local/         # Base de datos Room
│   └── repository/    # Repositorios
├── di/                # Inyección de dependencias (Hilt)
└── ui/                # Capa de presentación
    ├── home/          # Pantalla principal
    ├── transaction/   # Agregar transacciones
    ├── settings/      # Configuración
    ├── onboarding/    # Pantalla de bienvenida
    └── assistant/     # Asistente IA
```

### Stack Tecnológico

#### Core
- **Lenguaje**: Kotlin 2.0.21
- **UI Framework**: Jetpack Compose
- **Build System**: Gradle (KTS)
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36

#### Principales Dependencias

| Librería | Versión | Propósito |
|----------|---------|-----------|
| [Jetpack Compose](https://developer.android.com/jetpack/compose) | BOM 2024.09.00 | UI declarativa moderna |
| [Room](https://developer.android.com/training/data-storage/room) | 2.6.1 | Base de datos local SQLite |
| [Hilt](https://dagger.dev/hilt/) | 2.51.1 | Inyección de dependencias |
| [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) | 2.8.0 | Navegación entre pantallas |
| [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) | 1.1.0 | Almacenamiento de preferencias |
| [Material3](https://m3.material.io/) | Latest | Design system moderno |
| [KSP](https://github.com/google/ksp) | 2.0.21-1.0.25 | Procesamiento de anotaciones |

## Comenzando

### Prerrequisitos

- Android Studio Ladybug (2024.2.1) o superior
- JDK 11 o superior
- Android SDK 36
- Gradle 8.13.2

### Instalación

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/tuusuario/flux.git
   cd flux
   ```

2. **Abre el proyecto en Android Studio**
   - File → Open → Selecciona la carpeta del proyecto

3. **Sincroniza Gradle**
   - Android Studio sincronizará automáticamente las dependencias
   - O ejecuta manualmente: `./gradlew sync`

4. **Ejecuta la aplicación**
   - Conecta un dispositivo Android o inicia un emulador
   - Haz clic en el botón "Run" (▶️) o presiona `Shift + F10`

## Uso

### Primera vez
1. La app te dará la bienvenida con una pantalla de onboarding
2. Ingresa tu nombre para personalizar la experiencia
3. ¡Comienza a registrar tus transacciones!

### Agregar una transacción
1. En la pantalla principal, toca el botón flotante (+)
2. Selecciona el tipo: Ingreso o Gasto
3. Completa los campos:
   - Cantidad
   - Categoría
   - Descripción (opcional)
   - Fecha
4. Guarda la transacción

### Ver tu resumen
- La pantalla principal muestra:
  - Balance total
  - Ingresos del mes actual
  - Gastos del mes actual
  - Listado de movimientos recientes

## Desarrollo

### Estructura de Módulos

#### Data Layer
```kotlin
// Entidades de Room
@Entity(tableName = "transactions")
data class Transaction(...)

// DAOs
@Dao
interface TransactionDao { ... }

// Repositorios
class TransactionRepository @Inject constructor(...)
```

#### UI Layer (Compose)
```kotlin
// Screens con ViewModels inyectados
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) { ... }
```

### Inyección de Dependencias

El proyecto usa **Hilt** para DI. Módulos principales:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(...): FluxDatabase
    
    @Provides
    fun provideDataStore(...): DataStore<Preferences>
}
```

### Testing

```bash
# Tests unitarios
./gradlew test

# Tests instrumentados
./gradlew connectedAndroidTest
```

## Roadmap

### Completado
- [x] Onboarding inicial
- [x] CRUD de transacciones
- [x] Resumen financiero mensual
- [x] Persistencia con Room y DataStore
- [x] Arquitectura MVVM
- [x] Inyección de dependencias con Hilt

### En Progreso
- [ ] Asistente IA con chat interactivo
- [ ] Gráficas y estadísticas visuales

### Planeado
- [ ] Categorías personalizables
- [ ] Exportar datos a CSV/PDF
- [ ] Presupuestos mensuales
- [ ] Recordatorios de pagos
- [ ] Modo oscuro/claro
- [ ] Soporte multi-idioma
- [ ] Sincronización en la nube
- [ ] Widgets de pantalla principal

## Contribuciones

¡Las contribuciones son bienvenidas! Si deseas mejorar Flux:

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Autor

**José Luyo**

- GitHub: [@jluyo](https://github.com/jluyo)

## Agradecimientos

- Material Design 3 por el sistema de diseño
- Google por las librerías de Jetpack
- La comunidad de Android Development