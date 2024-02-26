package com.example.learningmanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learningmanagementapp.databinding.ActivityAddEditBinding;
import com.example.learningmanagementapp.models.entities.Course;

public class AddEditActivity extends AppCompatActivity {

    private ActivityAddEditBinding activityAddEditBinding;
    private AddEditActivityClickHandlers addEditActivityClickHandlers;
    private Course course;

    public static final String COURSE_ID = "courseId";
    public static final String COURSE_NAME = "courseName";
    public static final String COURSE_PRICE = "coursePrice";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        course= new Course();

        activityAddEditBinding = DataBindingUtil.setContentView(this,R.layout.activity_add_edit);
        addEditActivityClickHandlers = new AddEditActivityClickHandlers(this);
        activityAddEditBinding.setClickHandler(addEditActivityClickHandlers);

        activityAddEditBinding.setCourse(course);

        Intent intent = getIntent();
        Intent i = getIntent();
        if(i.hasExtra(COURSE_ID)){
            setTitle("Edit Course");
            course.setName(i.getStringExtra(COURSE_NAME));
            course.setPrice(i.getStringExtra(COURSE_PRICE));
        }else {
            setTitle("Create New Course");
        }
    }

    public class AddEditActivityClickHandlers{
        Context context;
        AddEditActivityClickHandlers(Context context){
            this.context =context;
        }
        public void onClick(View view){
            if(course.getName() == null){
                Toast.makeText(context, "Name and price can not be empty", Toast.LENGTH_SHORT).show();
            }else{
                Intent i = new Intent();
                i.putExtra(COURSE_NAME,course.getName());
                i.putExtra(COURSE_PRICE,course.getPrice());
                setResult(RESULT_OK, i);
                finish();
            }
        }
    }
}