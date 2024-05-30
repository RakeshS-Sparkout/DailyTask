package com.example.buyergetter.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CartItem::class, Products::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create a new table with the correct schema
                database.execSQL("""
                    CREATE TABLE products_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        image INTEGER NOT NULL,
                        name TEXT NOT NULL,
                        price REAL NOT NULL,
                        description TEXT NOT NULL,
                        rating REAL NOT NULL,
                        shopId INTEGER NOT NULL
                    )
                """.trimIndent())

                // Copy the data from the old table to the new table
                database.execSQL("""
                    INSERT INTO products_new (image, name, price, description, rating, shopId)
                    SELECT image, name, price, 'default description', rating, shopId
                    FROM products
                """.trimIndent())

                // Drop the old table
                database.execSQL("DROP TABLE products")

                // Rename the new table to the old table's name
                database.execSQL("ALTER TABLE products_new RENAME TO products")
            }
        }

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
