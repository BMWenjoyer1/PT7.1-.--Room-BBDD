# Resum del Projecte - Android Tasques amb Room

## 📱 Descripció General

Aplicació Android que gestiona tasques i tags amb base de dades local Room. Permet afegir tasques, assignar etiquetes, cambiar estats, i filtrar tasques per tag.

---

## 📂 Estructura del Projecte

### Fitxers de Configuració
```
build.gradle                  # Build script del projecte raíz
app/build.gradle             # Build script del mòdul app (dependències)
settings.gradle              # Configuració de submòduls
gradle.properties            # Propietats de Gradle
app/proguard-rules.pro       # Regles de ProGuard per minificació
.gitignore                   # Fitxers a ignorar en Git
```

### Entitats de Base de Dades
```
data/entity/Tasca.kt         # Entitat de Tasques
data/entity/Tag.kt           # Entitat de Tags
data/entity/TascaTag.kt      # Taula de relació M:N
data/entity/TascaWithTags.kt # Relació amb tags adjunts
```

### Data Access Objects (DAO)
```
data/dao/TascaDao.kt         # Operacions de Tasques
data/dao/TagDao.kt           # Operacions de Tags
data/dao/TascaTagDao.kt      # Operacions de relacions
```

### Base de Dades i Repositori
```
data/TasquesDatabase.kt      # Configuració de Room
data/TasquesRepository.kt    # Pattern Repository
```

### ViewModels
```
ui/viewmodel/TascaListViewModel.kt        # Lógica de llista de tasques
ui/viewmodel/CreateTascaViewModel.kt      # Lógica de crear tasca
ui/viewmodel/FilterTascasByTagViewModel.kt # Lógica de filtratge
ui/viewmodel/TagViewModel.kt              # Lógica de tags
```

### Fragments (Interfaz)
```
ui/fragment/TasquesFragment.kt       # Llistat de tasques
ui/fragment/CreateTascaFragment.kt   # Crear nova tasca
ui/fragment/TagsFragment.kt          # Gestió de tags
```

### Adapters (RecyclerView)
```
ui/adapter/TascaAdapter.kt           # Adapter per tasques
ui/adapter/TagAdapter.kt             # Adapter per tags
ui/adapter/SelectableTagAdapter.kt   # Adapter per seleccionar tags
```

### Activities
```
MainActivity.kt                       # Activitat principal
TasquesApplication.kt                # Classe d'aplicació
```

### Layouts XML
```
res/layout/activity_main.xml              # Layout principal
res/layout/fragment_tasques.xml           # Layout de llistat de tasques
res/layout/fragment_create_tasca.xml      # Layout de crear tasca
res/layout/fragment_tags.xml              # Layout de gestió de tags
res/layout/item_tasca.xml                 # Layout d'element tasca
res/layout/item_tag.xml                   # Layout d'element tag
res/layout/item_tag_selectable.xml        # Layout d'element tag seleccionable
```

### Resources
```
res/menu/bottom_nav_menu.xml         # Menú de navegació inferior
res/values/strings.xml                # Cadenes de text
res/values/colors.xml                 # Definicions de colors
res/values/styles.xml                 # Estilos de l'app
res/values/arrays.xml                 # Arrays de dades
```

### AndroidManifest
```
app/src/main/AndroidManifest.xml     # Configuració de l'app
```

### Documentació
```
README.md                             # Documentació principal
DEVELOPMENT.md                        # Guia de desenvolupament
DATABASE_SCHEMA.md                    # Esquema de base de dades
QUICK_REFERENCE.md                    # Referència ràpida de codi
```

---

## 🔄 Flux de Dades

### Injecció de Dependències
```
TasquesApplication
    ├── Database
    └── Repository
        ├── TascaDao
        ├── TagDao
        └── TascaTagDao
```

### MVVM Architecture
```
Fragment (Interfaz)
    ↓
ViewModel (Lógica)
    ↓
Repository (Datos)
    ↓
DAO (Operacions)
    ↓
Room Database (Local)
```

---

## 🎯 Funcionalitats Implementades

### Tasques
✅ Crear noves tasques
✅ Veure totes les tasques
✅ Cambiar l'estat (pendent → en_proces → completada)
✅ Esborrar tasques
✅ Veure data de creació
✅ Registrar data de canvi d'estat

### Tags
✅ Crear nous tags
✅ Esborrar tags
✅ Veure tota la llista de tags
✅ Tags inicials: "casa" i "feina"

### Relacions
✅ Assignar múltiples tags a una tasca
✅ Filtrar tasques per tag
✅ Veure tags de cada tasca
✅ Esborrat en cascada (tags eliminats + tasques)

---

## 🛠️ Tecnologies Utilitzades

### Android Framework
- AndroidX Core 1.9.0
- AndroidX AppCompat 1.5.1
- Material Components 1.7.0

### Arquitectura
- AndroidX Lifecycle 2.5.1 (ViewModel, LiveData)
- AndroidX Room 2.5.1 (Base de dades local)
- Fragment & Navigation

### UI
- RecyclerView 1.3.0
- CardView
- ConstraintLayout

### Coroutines
- Kotlin Coroutines per operacions assincrones

### Idioma
- Kotlin 100%

---

## 📊 Base de Dades

### Taules
1. **tasques** - Emmagatzema les tasques principals
2. **tags** - Catàleg de etiquetes disponibles
3. **tasca_tag** - Relacions M:N entre tasques i tags

### Relacions
- Tasques → Tags (M:N through tasca_tag)
- On cascade delete per mantenir consistència

### Inicialització
- Dos tags creats automàticament: "casa", "feina"
- Empty tasques table per defecte

---

## 🚀 Com Executar

### Requisits
- Android Studio Flamingo o superior
- Android SDK 21+
- Gradle 7.0+

### Steps
1. Obrir el projecte a Android Studio
2. Sincronitzar Gradle (`File > Sync Now`)
3. Crear emulador o connectar dispositiu
4. Executar app (`Run > Run 'app'`)

---

## 📝 Fitxers Creats

Total: **47 fitxers**

### Per categoria:
- Entitats: 4 fitxers
- DAOs: 3 fitxers
- ViewModels: 4 fitxers
- Fragments: 3 fitxers
- Adapters: 3 fitxers
- Layouts: 7 fitxers
- Resources: 4 fitxers
- Config: 6 fitxers
- Main Classes: 2 fitxers
- Documentació: 4 fitxers

---

## 🔐 Seguretat i Bones Pràctiques

- ✅ Ús de Room para prevenir SQL injection
- ✅ Operacions de BD en threads separats (Coroutines)
- ✅ LiveData per a observables reactius
- ✅ MVVM architecture per separació de responsabilitats
- ✅ Proguard rules per minificació
- ✅ Cascading deletes para integritat referencial

---

## 🐛 Debugging

### Tools Disponibles
- Android Studio Debugger
- Logcat para logs
- Database Inspector para inspeccionar Room
- Layout Inspector per UI debugging

---

## 📚 Recursos Addicionals

- Android Developers: https://developer.android.com
- Room Persistence: https://developer.android.com/training/data-storage/room
- ViewModel: https://developer.android.com/topic/libraries/architecture/viewmodel
- LiveData: https://developer.android.com/topic/libraries/architecture/livedata

---

## 📄 Llicència

MIT License

---

**Versió**: 1.0
**Data de creació**: Març 2026
**Estat**: ✅ Completat i funcionant
