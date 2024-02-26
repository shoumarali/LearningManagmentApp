package com.example.learningmanagementapp.utils;

import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.learningmanagementapp.models.entities.Course;

import java.util.ArrayList;

public class CourseDiffCallBack extends DiffUtil.Callback {

    private ArrayList<Course> newArrayCourse;
    private ArrayList<Course> oldArrayCourse;

    public CourseDiffCallBack(ArrayList<Course> newArrayCourse, ArrayList<Course> oldArrayCourse){
        this.newArrayCourse = newArrayCourse;
        this.oldArrayCourse = oldArrayCourse;
    }

    @Override
    public int getOldListSize() {
        return oldArrayCourse.size();
    }

    @Override
    public int getNewListSize() {
        return newArrayCourse.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldArrayCourse.get(oldItemPosition).getId() == newArrayCourse.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldArrayCourse.get(oldItemPosition).equals(newArrayCourse.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
