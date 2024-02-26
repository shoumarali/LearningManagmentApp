package com.example.learningmanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.learningmanagementapp.adapters.CourseAdapter;
import com.example.learningmanagementapp.databinding.ActivityMainBinding;
import com.example.learningmanagementapp.databinding.MainContentActivityBinding;
import com.example.learningmanagementapp.models.entities.Category;
import com.example.learningmanagementapp.models.entities.Course;
import com.example.learningmanagementapp.viewmodel.MainActivityViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding activityMainBinding;
    private MainActivityClickHandlers mainActivityClickHandlers;
    private MainActivityViewModel mainActivityViewModel;

    // Categories
    private ArrayList<Category> categoryArrayList;
    private Category selectedCategory;
    // Courses
    private ArrayList<Course> courseArrayList;
    private long selectedCourse;

    private static final int ADD_COURSE_REQUEST_CODE =1;
    private static final int EDIT_COURSE_REQUEST_CODE =2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityClickHandlers = new MainActivityClickHandlers(getApplicationContext());
        activityMainBinding.setClickHandler(mainActivityClickHandlers);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        mainActivityViewModel.getCategories().observe(this, (categories) -> {
            categoryArrayList =(ArrayList<Category>) categories;
            showOnSpinner();
        });
    }

    private void showOnSpinner(){
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                categoryArrayList
        );
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);
    }

    private void loadCoursesArrayList(long categoryId){
        mainActivityViewModel.getCoursesOfSelectedCategory(categoryId).observe(this, (courses) -> {
            courseArrayList =(ArrayList<Course>) courses;
            loadRecyclerView();
        });
    }

    private void loadRecyclerView(){
        RecyclerView recyclerView = activityMainBinding.secondaryLayout.recyclerView;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CourseAdapter courseAdapter = new CourseAdapter();
        recyclerView.setAdapter(courseAdapter);
        courseAdapter.setCourses(courseArrayList);

        courseAdapter.setOnItemClickListener((course) -> {
            selectedCourse = course.getId();
            Intent intent =new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra(AddEditActivity.COURSE_ID,selectedCourse);
            intent.putExtra(AddEditActivity.COURSE_NAME,course.getName());
            intent.putExtra(AddEditActivity.COURSE_PRICE,course.getPrice());
            startActivityForResult(intent, EDIT_COURSE_REQUEST_CODE);
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course courseTODelete = courseArrayList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteCourse(courseTODelete);
            }
        }).attachToRecyclerView(recyclerView);
    }

    public class MainActivityClickHandlers{

        Context context;
        MainActivityClickHandlers(Context context){

            this.context = context;
        }
        public void onFabClick(View view){
            Intent intent = new Intent(MainActivity.this , AddEditActivity.class);
            startActivityForResult(intent, ADD_COURSE_REQUEST_CODE);
        }
        public void onSelectItem(AdapterView<?> adapterView, View view, int selectedItem, long id){
            selectedCategory =(Category) adapterView.getItemAtPosition(selectedItem);
            loadCoursesArrayList(selectedCategory.getId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == EDIT_COURSE_REQUEST_CODE && resultCode == RESULT_OK){
            Course course = new Course();
            course.setCategoryId(selectedCategory.getId());
            assert data != null;
            course.setName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setPrice(data.getStringExtra(AddEditActivity.COURSE_PRICE));
            course.setId(selectedCourse);
            mainActivityViewModel.updateCourse(course);

        }else if(requestCode == ADD_COURSE_REQUEST_CODE && resultCode == RESULT_OK){
            Course course = new Course();
            course.setCategoryId(selectedCategory.getId());
            assert  data!=null;
            course.setName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setPrice(data.getStringExtra(AddEditActivity.COURSE_PRICE));
            mainActivityViewModel.addNewCourse(course);
        }
    }
}