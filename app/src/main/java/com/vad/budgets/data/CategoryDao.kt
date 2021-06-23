package com.vad.budgets.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY categoryId DESC")
    fun getAll(): List<Category>
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(category: Category) : Long
    
    @Update
    fun update(category: Category): Int
    
    @Query("DELETE FROM categories")
    fun clearCategories()
    
    @Query("SELECT * FROM categories WHERE categoryId = :id")
    fun getCategoryById(id: Long): Category
}
