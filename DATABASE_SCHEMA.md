# Diagrama de la Base de Dades

## Esquema Relacional

```
┌─────────────────────────────┐
│         TASQUES             │
├─────────────────────────────┤
│ id (PK, AI)      INT        │
│ titol            STRING     │◄──────┐
│ descripcio       STRING     │       │
│ estat            STRING     │       │
│ dataCreacio      LONG       │       │
│ dataCanvi        LONG NULL  │       │
└─────────────────────────────┘       │
                                      │
                               ┌──────┴───────────┬──────────────┐
                               │                  │              │
                          ┌────┴──────────┐  TASCA_TAG   ┌──────┴─────┐
                          │                 TABLE         │            │
                          │  ┌────────────┐              │┌─────────────┤
                          │  │ tascaId (FK)│─────────────├┤ tagId (FK)  │
                          │  │ tagId (FK)  │─────────────┤│            │
                          │  └────────────┘              │└─────────────┤
                          │                  (M:N)        │            │
                          │                              │            │
                               ┌──────────────────────────┘            │
                               │                                       │
                        ┌──────┴──────────────┐            ┌───────────┴─────┐
                        │       TAGS          │            │     TAGS        │
                        ├─────────────────────┤            ├─────────────────┤
                        │ id (PK, AI)    INT  │            │ id (PK, AI) INT │
                        │ nom           STRING│            │ nom       STRING │
                        └─────────────────────┘            └─────────────────┘
```

## Relacions

### 1. TASQUES (Taula Principal)
- `id`: Identificador únic (autoincrement)
- `titol`: Títol de la tasca (requereix valor)
- `descripcio`: Descripció opcional de la tasca
- `estat`: Estado actual de la tasca (pendent, en_proces, completada)
- `dataCreacio`: Timestamp de la creació
- `dataCanvi`: Timestamp de l'últim canvi (nullable)

### 2. TAGS (Catàleg de Etiquetes)
- `id`: Identificador únic (autoincrement)
- `nom`: Nom de l'etiqueta (p.e., "casa", "feina")

### 3. TASCA_TAG (Taula de Relació M:N)
- `tascaId`: Clau forana que apunta a TASQUES.id
  - Esborrat en cascada: Si s'esborra una tasca, es netejen les etiquetes associades
- `tagId`: Clau forana que apunta a TAGS.id
  - Esborrat en cascada: Si s'esborra un tag, es netejen totes les associacions

**Claus primàries**: Es componen per `(tascaId, tagId)` - ensures no duplicates

## Fluxo de Dades

### Crear Tasca amb Tags

```
CreateTascaFragment
    ↓
CreateTascaViewModel.saveTasca()
    ↓
Repository.insertTasca() ⟶ TascaDao.insertTasca() ⟶ Database
    ↓                          (retorna ID)
Repository.assignTagToTasca() ⟶ TascaTagDao.insertTascaTag() ⟶ Database
```

### Obtenir Tasques amb Tags

```
TasquesFragment
    ↓
TascaListViewModel.allTasques (LiveData)
    ↓
Repository.getAllTasquesWithTags()
    ↓
TascaDao.getAllTasquesWithTags() (with @Relation)
    ↓
Database ⟶ TascaWithTags (Tasca + List<Tag>)
    ↓
TascaAdapter.submitList()
```

### Filtrar Tasques per Tag

```
TasquesFragment
    ↓
FilterTascasByTagViewModel.selectedTagId = 1
    ↓
Repository.getTasquesByTag(1)
    ↓
TascaDao.getTasquesByTag(1)
    ↓
SELECT t.* FROM tasques t
INNER JOIN tasca_tag tt ON t.id = tt.tascaId
WHERE tt.tagId = 1
    ↓
TascaWithTags[] ⟶ Adapter
```

## Operacions CRUD

### CREATE
- **Tasca**: `INSERT INTO tasques (titol, descripcio, estat, dataCreacio)`
- **Tag**: `INSERT INTO tags (nom)`
- **Associació**: `INSERT INTO tasca_tag (tascaId, tagId)`

### READ
- **Totes tasques**: `SELECT * FROM tasques`
- **Tasques amb tags**: `SELECT * FROM tasques + JOIN tasca_tag + JOIN tags`
- **Tasques per tag**: `SELECT * FROM tasques WHERE id IN (SELECT tascaId FROM tasca_tag WHERE tagId = ?)`
- **Tots tags**: `SELECT * FROM tags`

### UPDATE
- **Tasca**: `UPDATE tasques SET titol=?, descripcio=?, estat=?, dataCanvi=? WHERE id=?`
- **Tag**: `UPDATE tags SET nom=? WHERE id=?`

### DELETE
- **Tasca**: `DELETE FROM tasques WHERE id=?` (on cascade deletes tasca_tag rows)
- **Tag**: `DELETE FROM tags WHERE id=?` (on cascade deletes tasca_tag rows)
- **Associació**: `DELETE FROM tasca_tag WHERE tascaId=? AND tagId=?`

## Indices

Els indices s'han creat a la taula `tasca_tag` per a optimizar les queries:
- Index en `tascaId` - per a queries que filtren per tasca
- Index en `tagId` - per a queries que filtren per tag

## Versió del Schema

**Versió actual**: 1

Per a futures actualitzacions de l'esquema, s'haurien de crear migrations amb el sistema de Room Migrations.
