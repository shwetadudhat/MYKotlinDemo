package com.test.mykotlindemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.test.mykotlindemo.Model.Product


@Database(entities = [Product::class], version = 1)
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
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}