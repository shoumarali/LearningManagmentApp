package com.example.learningmanagementapp.models.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.learningmanagementapp.models.entities.Course;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    void insertCourse(Course course);
    @Delete
    void deleteCourse(Course course);
    @Update
    void updateCourse(Course course);

    @Query("SELECT * FROM course_table")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM course_table WHERE category_id == :id")
    LiveData<List<Course>> getCoursesWithSpecificCategory(long id);
}
