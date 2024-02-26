package com.example.learningmanagementapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.learningmanagementapp.models.entities.Category;
import com.example.learningmanagementapp.models.entities.Course;
import com.example.learningmanagementapp.models.repository.CourseShopRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    CourseShopRepository repo;
    LiveData<List<Course>> coursesOfSelectedCategory;
    LiveData<List<Category>> categories;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repo = new CourseShopRepository(application);
    }

    public void addNewCourse(Course course){
        repo.insertCourse(course);
    }
    public void deleteCourse(Course course){
        repo.deleteCourse(course);
    }
    public void updateCourse(Course course){
        repo.updateCourse(course);
    }
    public LiveData<List<Course>> getCoursesOfSelectedCategory(long categoryId){

        coursesOfSelectedCategory = repo.getCoursesWithSpecificCategory(categoryId);
        return coursesOfSelectedCategory;
    }
    public LiveData<List<Category>> getCategories(){
        categories = repo.getAllCategories();
        return categories;
    }
}
