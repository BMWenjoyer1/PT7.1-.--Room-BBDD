# Referència Ràpida - Operacions Comunes

## Crear una Tasca

```kotlin
val repository = (activity?.application as TasquesApplication).repository

viewModelScope.launch {
    val tasca = Tasca(
        titol = "Mi primera tarea",
        descripcio = "Descripción opcional",
        estat = "pendent"
    )
    
    val tascaId = repository.insertTasca(tasca).toInt()
    
    // Assignar tags (opcional)
    repository.assignTagToTasca(tascaId, tagId = 1)
    repository.assignTagToTasca(tascaId, tagId = 2)
}
```

## Obtenir Totes les Tasques

```kotlin
repository.getAllTasquesWithTags().observe(viewLifecycleOwner) { tasques ->
    // tasques és una List<TascaWithTags>
    tasques.forEach { tascaWithTags ->
        println("Tasca: ${tascaWithTags.tasca.titol}")
        println("Tags: ${tascaWithTags.tags.joinToString { it.nom }}")
    }
}
```

## Filtrar Tasques per Tag

```kotlin
repository.getTasquesByTag(tagId = 1).observe(viewLifecycleOwner) { tasques ->
    // tasques filtrades pel tag especificat
    adapter.submitList(tasques)
}
```

## Actualitzar l'Estat d'una Tasca

```kotlin
viewModelScope.launch {
    val tascaActualitzada = tasca.copy(
        estat = "completada",
        dataCanvi = System.currentTimeMillis()
    )
    repository.updateTasca(tascaActualitzada)
}
```

## Esborrar una Tasca

```kotlin
viewModelScope.launch {
    repository.deleteTasca(tasca)
}
```

## Crear un Tag

```kotlin
viewModelScope.launch {
    val tag = Tag(nom = "urgente")
    val tagId = repository.insertTag(tag)
}
```

## Obtenir Tots els Tags

```kotlin
repository.getAllTags().observe(viewLifecycleOwner) { tags ->
    // tags és una List<Tag>
    tags.forEach { tag ->
        println("${tag.id}: ${tag.nom}")
    }
}
```

## Assignar Múltiples Tags a una Tasca

```kotlin
viewModelScope.launch {
    val tagIds = listOf(1, 2, 3)
    repository.updateTascaTags(tascaId = 5, tagIds)
}
```

## Estats de Tasques

Els estats disponibles són:
- `"pendent"` - La tasca no ha començat
- `"en_proces"` - La tasca está en progreso
- `"completada"` - La tasca está finalizada

## Accder a la Aplicació Global

```kotlin
val app = activity?.application as TasquesApplication
val repository = app.repository
```

## Estructura de TascaWithTags

```kotlin
data class TascaWithTags(
    val tasca: Tasca,        // La tasca principal
    val tags: List<Tag>      // Els tags assignats
)
```

## Estructura de Tasca

```kotlin
data class Tasca(
    val id: Int,                    // Autoincrement
    val titol: String,              // Obligatori
    val descripcio: String = "",    // Opcional
    val estat: String = "pendent",  // pendent | en_proces | completada
    val dataCreacio: Long,          // Unix timestamp
    val dataCanvi: Long? = null     // Unix timestamp o null
)
```

## Estructura de Tag

```kotlin
data class Tag(
    val id: Int,            // Autoincrement
    val nom: String         // Obligatori
)
```

## Coroutines en Fragments

```kotlin
// Els Fragments ja tienen lifecycleScope disponible
lifecycleScope.launch {
    val tasques = repository.getAllTagsSync()
    // Uso de tasques
}
```

## Observing LiveData en Fragments

```kotlin
repository.getAllTasques().observe(viewLifecycleOwner) { tasques ->
    // Aquest codi s'executarà cada vegada que les dades cambiin
    adapter.submitList(tasques)
}
```

## ViewModel Setup

```kotlin
// A onViewCreated de Fragment
val factory = TascaListViewModelFactory(repository)
viewModel = ViewModelProvider(this, factory).get(TascaListViewModel::class.java)

// O amb ViewBind
binding.apply {
    viewModel = this@Fragment.viewModel
    lifecycleOwner = viewLifecycleOwner
}
```
