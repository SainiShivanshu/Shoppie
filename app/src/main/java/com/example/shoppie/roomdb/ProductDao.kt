package com.example.shoppie.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert
   suspend fun insertProduct(product:ProductModel)
    @Delete
    suspend fun deleteProduct(product:ProductModel)

    @Query("SELECT * FROM products")
    fun getAllProducts() :LiveData<List<ProductModel>>
    // :id matlab agli line mein jo likhi vo
    @Query("SELECT *FROM products WHERE productId=:id")
    fun isExit(id : String) : ProductModel
}