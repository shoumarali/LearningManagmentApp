package com.example.learningmanagementapp.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learningmanagementapp.R;
import com.example.learningmanagementapp.databinding.CourseListItemBinding;
import com.example.learningmanagementapp.models.entities.Course;
import com.example.learningmanagementapp.utils.CourseDiffCallBack;

import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private ArrayList<Course> courses = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CourseListItemBinding courseListItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.course_list_item,
                parent,
                false
        );
        return new CourseViewHolder(courseListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course course= courses.get(position);
        holder.courseListItemBinding.setCourse(course);
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder{
        CourseListItemBinding courseListItemBinding;
        public CourseViewHolder(@NonNull CourseListItemBinding courseListItemBinding) {
            super(courseListItemBinding.getRoot());
            this.courseListItemBinding = courseListItemBinding;
            courseListItemBinding.getRoot().setOnClickListener((view) -> {
                int clickPosition = getAdapterPosition();
                if(onItemClickListener != null && clickPosition != RecyclerView.NO_POSITION)
                    onItemClickListener.onItemClick(courses.get(clickPosition));
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(Course course);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public void setCourses(ArrayList<Course> courses){
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(
                new CourseDiffCallBack(this.courses,courses),
                false);
        this.courses = courses;
        result.dispatchUpdatesTo(CourseAdapter.this);
    }
}
