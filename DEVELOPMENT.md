# Documentació de Desenvolupament

## Setup Inicial

### 1. Clonar el repositori
```bash
git clone https://github.com/username/PT7.1-.--Room-BBDD.git
cd PT7.1-.--Room-BBDD
```

### 2. Obrir a Android Studio
- Obriu Android Studio
- Selecciona "Open an Existing Project"
- Navegueu al directori del projecte
- Android Studio carregarà el projecte automàticament

### 3. Descarregar dependències
- Android Studio sincronitzarà les dependències automàticament
- Si no ho fa, aneu a `File > Sync Now`

### 4. Configurar Android SDK
- `File > Project Structure`
- Especifiqueu la ubicació de l'Android SDK
- Assegureu-vos que tingueu almenys Android SDK 21+

### 5. Crear un emulador o connectar un dispositiu
- Per crear un emulador: `Tools > AVD Manager > Create Virtual Device`
- Selecciona una imatge de sistema (Android 12.0 o superior es recomanat)
- Per a dispositiu físic: habilitar el debugging USB en el dispositiu

### 6. Executar l'aplicació
- Cliqueu a `Run > Run 'app'` o premeu `Shift + F10`
- Selecciona l'emulador o dispositiu
- L'aplicació es construirà, instal·larà i executarà

## Estructura de Fitxers

### Entities
Les entitats defineixen l'estructura de la base de dades:

```kotlin
// Tasca - representa una tasca individual
@Entity(tableName = "tasques")
data class Tasca(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titol: String,
    val descripcio: String = "",
    val estat: String = "pendent",
    val dataCreacio: Long = System.currentTimeMillis(),
    val dataCanvi: Long? = null
)

// Tag - representa una etiqueta
@Entity(tableName = "tags")
data class Tag(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nom: String
)

// TascaTag - relació M:N entre Tasques i Tags
@Entity(
    tableName = "tasca_tag",
    primaryKeys = ["tascaId", "tagId"],
    foreignKeys = [...]
)
data class TascaTag(val tascaId: Int, val tagId: Int)
```

### DAOs
Els DAOs proporcionen operacions CRUD:

```kotlin
@Dao
interface TascaDao {
    @Insert suspend fun insertTasca(tasca: Tasca): Long
    @Update suspend fun updateTasca(tasca: Tasca)
    @Delete suspend fun deleteTasca(tasca: Tasca)
    @Query("SELECT * FROM tasques") fun getAllTasques(): LiveData<List<Tasca>>
}
```

### ViewModels
Els ViewModels gestionen la lògica de negoci:

```kotlin
class TascaListViewModel(private val repository: TasquesRepository) : ViewModel() {
    val allTasques: LiveData<List<TascaWithTags>> = repository.getAllTasquesWithTags()
    
    fun deleteTasca(tasca: Tasca) {
        viewModelScope.launch {
            repository.deleteTasca(tasca)
        }
    }
}
```

### Fragments
Els Fragments contenen la lògica de UI:

```kotlin
class TasquesFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setup UI
        viewModel.allTasques.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}
```

## Base de Dades Room

### Inicialització
La base de dades es crea automàticament la primera vegada que s'executa l'aplicació.

### Migrations
Per futuros canvis en l'esquema:
```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Aquí va el codi de migració
    }
}

.addMigrations(MIGRATION_1_2)
```

## Testing

Per a proves unitàries:
```kotlin
@RunWith(AndroidTestRunner::class)
class TascaDaoTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    
    private lateinit var db: TasquesDatabase
    private lateinit var tascaDao: TascaDao
    
    // Tests...
}
```

## Depuració

### Accedir a la Base de Dades
Useu Android Studio Database Inspector:
- `View > Tool Windows > Database Inspector`
- Selecciona el dispositiu/emulador
- Busca "tasques_database"

### Logs
Afegiu logs per a depuració:
```kotlin
Log.d("TascaListViewModel", "All tasks: $allTasques")
```

## Publicació

### Build Signed APK
1. `Build > Generate Signed Bundle / APK`
2. Selecciona `APK`
3. Crea/selecciona un keystore
4. Selecciona el sabor de compilació
5. Android Studio construirà l'APK

## Troubleshooting

### Gradle Sync Fallido
- `File > Invalidate Caches / Restart`
- Asseguri't que tingueu la versió correcta de Gradle

### Compilació Fallida
- Verifiqueu que tingueu l'Android SDK correcte
- Comproveu que les versions de les dependències coincidasin

### Crashes en Runtime
- Comproveu els logs logcat: `Logcat` (a la part inferior de Android Studio)
- Utilitzeu el debugger: `Run > Debug 'app'`

## Contribució

Per a contribuir:
1. Crea una branca (`git checkout -b feature/AmazingFeature`)
2. Commit els canvis (`git commit -m 'Add AmazingFeature'`)
3. Push a la branca (`git push origin feature/AmazingFeature`)
4. Obriu un Pull Request

## Recursos

- [Android Developers](https://developer.android.com/)
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- [ViewModel Documentation](https://developer.android.com/topic/libraries/architecture/viewmodel)
