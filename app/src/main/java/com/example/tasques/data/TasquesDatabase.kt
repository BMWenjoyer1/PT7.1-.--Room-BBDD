package com.example.tasques.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tasques.data.dao.TascaDao
import com.example.tasques.data.dao.TagDao
import com.example.tasques.data.dao.TascaTagDao
import com.example.tasques.data.entity.Tasca
import com.example.tasques.data.entity.Tag
import com.example.tasques.data.entity.TascaTag
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Tasca::class, Tag::class, TascaTag::class],
    version = 1
)
abstract class TasquesDatabase : RoomDatabase() {
    abstract fun tascaDao(): TascaDao
    abstract fun tagDao(): TagDao
    abstract fun tascaTagDao(): TascaTagDao

    companion object {
        @Volatile
        private var INSTANCE: TasquesDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): TasquesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TasquesDatabase::class.java,
                    "tasques_database"
                )
                    .addCallback(TasquesDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class TasquesDatabaseCallback(private val scope: CoroutineScope) :
        RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val tagDao = database.tagDao()
                    
                    // Insert initial tags
                    tagDao.insertTag(Tag(nom = "casa"))
                    tagDao.insertTag(Tag(nom = "feina"))
                }
            }
        }
    }
}
