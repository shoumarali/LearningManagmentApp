package com.example.learningmanagementapp.models.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.learningmanagementapp.models.Dao.CategoryDao;
import com.example.learningmanagementapp.models.Dao.CourseDao;
import com.example.learningmanagementapp.models.DataBase.CourseDataBase;
import com.example.learningmanagementapp.models.entities.Category;
import com.example.learningmanagementapp.models.entities.Course;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CourseShopRepository{

    private CourseDao courseDao;
    private CategoryDao categoryDao;

    public CourseShopRepository(Application application){
        CourseDataBase courseDataBase = CourseDataBase.getInstance(application);
        courseDao = courseDataBase.courseDao();
        categoryDao = courseDataBase.categoryDao();
    }

    //Courses
    public LiveData<List<Course>> getCoursesWithSpecificCategory(long categoryId){
        return courseDao.getCoursesWithSpecificCategory(categoryId);
    }
    public void insertCourse(Course course){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            courseDao.insertCourse(course);
        });
    }
    public void deleteCourse(Course course){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            courseDao.deleteCourse(course);
        });
    }
    public void updateCourse(Course course){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            courseDao.updateCourse(course);
        });
    }

    //Category
    public LiveData<List<Category>> getAllCategories(){
        return categoryDao.getAllCategories();
    }
    public void insertCategory(Category category){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            categoryDao.insertCategory(category);
        });
    }
    public void updateCategory(Category category){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            categoryDao.updateCategory(category);
        });
    }
    public void deleteCategory(Category category){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            categoryDao.deleteCategory(category);
        });
    }
}
