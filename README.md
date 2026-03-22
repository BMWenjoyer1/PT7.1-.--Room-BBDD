# Aplicació de Gestió de Tasques amb Room

Una aplicació Android que permet gestionar tasques i tags mitjançant una base de dades local amb Room.

## Requisits

### Entitats de Base de Dades

#### Taula Tasques
- `id` (autoincrement) - Identificador únic
- `titol` - Títol de la tasca
- `descripcio` - Descripció (opcional)
- `estat` - Estat: pendent, en_proces, completada
- `dataCreacio` - Data de creació
- `dataCanvi` - Data de l'últim canvi d'estat

#### Taula Tags
- `id` (autoincrement) - Identificador únic
- `nom` - Nom del tag (exemple: casa, feina)

#### Relació M:N (Taula Intermèdia: TascaTag)
- `tascaId` - Clau forana a Tasques
- `tagId` - Clau forana a Tags

### Tags Inicials

L'aplicació inicialitza automàticament dos tags la primera vegada que s'executa:
- **casa**
- **feina**

## Funcionalitats

### Tasques
- ✅ Afegir noves tasques
- ✅ Veure totes les tasques
- ✅ Actualitzar l'estat de les tasques
- ✅ Esborrar tasques
- ✅ Filtrar tasques per tag
- ✅ Veure les tags assignades a cada tasca

### Tags
- ✅ Afegir nous tags
- ✅ Veure totes els tags
- ✅ Esborrar tags
- ✅ Assignar múltiples tags a una tasca

## Arquitectura

L'aplicació segueix l'arquitectura **MVVM** amb:

- **Entity**: Classes per representar les taules de la base de dades
- **DAO**: Interfícies per accés a les dades
- **Database**: Configuració de la base de dades Room
- **Repository**: Pattern per centralitzar l'accés a les dades
- **ViewModel**: Lògica de negoci i gestió d'estat
- **Fragment**: UI components
- **Adapter**: Adaptadors per a RecyclerViews

## Estructura del Projecte

```
com.example.tasques/
├── data/
│   ├── entity/
│   │   ├── Tasca.kt
│   │   ├── Tag.kt
│   │   ├── TascaTag.kt
│   │   └── TascaWithTags.kt
│   ├── dao/
│   │   ├── TascaDao.kt
│   │   ├── TagDao.kt
│   │   └── TascaTagDao.kt
│   ├── TasquesDatabase.kt
│   └── TasquesRepository.kt
├── ui/
│   ├── viewmodel/
│   │   ├── TascaListViewModel.kt
│   │   ├── CreateTascaViewModel.kt
│   │   ├── FilterTascasByTagViewModel.kt
│   │   └── TagViewModel.kt
│   ├── fragment/
│   │   ├── TasquesFragment.kt
│   │   ├── CreateTascaFragment.kt
│   │   └── TagsFragment.kt
│   └── adapter/
│       ├── TascaAdapter.kt
│       ├── TagAdapter.kt
│       └── SelectableTagAdapter.kt
└── MainActivity.kt
```

## Dependències Principals

- AndroidX Core
- AndroidX AppCompat
- Material Components
- AndroidX Lifecycle
- AndroidX Room
- AndroidX Navigation
- AndroidX RecyclerView

## Compilació i Execució

### Requisits del Sistema
- Android Studio Arctic Fox o superior
- Android SDK 21+
- Gradle 7.0+

### Steps
1. Obriu el projecte a Android Studio
2. Sincronitzeu els fitxers de Gradle (`File > Sync Now`)
3. Executeu l'aplicació en un emulador o dispositiu (`Run > Run 'app'`)

## Navegació

L'aplicació utilitza una navegació inferior amb dues pestanyes:

1. **Tasques** - Veu totes les tasques amb opció de afegir-ne noves
2. **Tags** - Gestiona els tags disponibles

## Uso de la Aplicació

### Crear una Tasca
1. Premeu el botó "+ Tasca"
2. Ingress el títol (obligatori)
3. Ingress la descripció (opcional)
4. Selecciona els tags desitjats
5. Premeu "Guardar"

### Assignar Tags a una Tasca
1. Quan creeu una tasca, podeu seleccionar múltiples tags
2. Els tags seleccionats es mostren a cada tasca

### Cambiar l'Estat d'una Tasca
1. Useu el selector de l'estat en cada tasca
2. Els estats disponibles són: pendent, en_proces, completada

### Afegir un Nou Tag
1. Accediu a la secció "Tags"
2. Ingress el nom del tag
3. Premeu "Afegir"

## Base de Dades

L'aplicació usa Room per a la persistència de dades. La base de dades s'emmagatzema localment en:
```
/data/data/com.example.tasques/databases/tasques_database
```

## Llicència

MIT License
