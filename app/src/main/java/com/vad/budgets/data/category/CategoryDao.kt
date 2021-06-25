package com.vad.budgets.data.category

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name DESC")
    fun getAll(): LiveData<List<Category>>
    
    @Query("SELECT * FROM categories where isActive = 1 ORDER BY name DESC")
    fun getAllActive(): LiveData<List<Category>>
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(category: Category): Long
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg category: Category)
    
    @Update
    fun update(category: Category): Int
    
    @Query("DELETE FROM categories")
    fun clearCategories()
    
    @Query("SELECT * FROM categories WHERE name = :name")
    fun getCategoryByName(name: String): Category
}
