package com.test.mykotlindemo.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.test.mykotlindemo.Utils.Converters
import java.io.Serializable

@Entity(tableName = "product")
@TypeConverters(Converters::class)
data class Product(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    @PrimaryKey(autoGenerate = true)
    val proid: Int,
    val id: Int,
    val images: List<String>,
    val price: Int?,
    val rating: Double,
    val stock: Int,
    val thumbnail: String?,
    val title: String
) : Serializable