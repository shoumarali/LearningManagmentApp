package com.example.learningmanagementapp.models.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.learningmanagementapp.models.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Update
    void updateCategory(Category category);
    @Delete
    void deleteCategory(Category category);
    @Insert
    void insertCategory(Category category);

    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> getAllCategories();
}
