package com.test.mykotlindemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.test.mykotlindemo.Model.Product


val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Write your migration code here
        database.execSQL("ALTER TABLE product ADD COLUMN images TEXT NOT NULL DEFAULT ''")
    }
}


@Database(entities = [Product::class], version = 2)
abstract class ProductDatabase : RoomDatabase(){

    abstract fun productDao() : ProductDao

    companion object{
        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getDatabase(context: Context): ProductDatabase {
            if (INSTANCE == null) {
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context,
                        ProductDatabase::class.java,
                        "productDB")
                        .addMigrations(migration_1_2)
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}