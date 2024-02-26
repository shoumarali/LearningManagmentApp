package com.example.learningmanagementapp.models.DataBase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.learningmanagementapp.models.Dao.CategoryDao;
import com.example.learningmanagementapp.models.Dao.CourseDao;
import com.example.learningmanagementapp.models.entities.Category;
import com.example.learningmanagementapp.models.entities.Course;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        Course.class,
        Category.class
} , version = 1)
public abstract class CourseDataBase extends RoomDatabase {

    public abstract CourseDao courseDao();
    public abstract CategoryDao categoryDao();

    private static CourseDataBase instance;

    public static CourseDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,CourseDataBase.class,"course_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback callback= new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            initializeData();
        }
    };
    private static void initializeData(){
        CourseDao courseDao = instance.courseDao();
        CategoryDao categoryDao= instance.categoryDao();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Category category1 = new Category();
            category1.setName("Front End");
            category1.setDescription("Web Development Interface");

            Category category2 = new Category();
            category2.setName("Back End");
            category2.setDescription("Web Development Database");

            categoryDao.insertCategory(category1);
            categoryDao.insertCategory(category2);


            // Courses
            Course course1 = new Course();
            course1.setName("HTML");
            course1.setPrice("100$");
            course1.setCategoryId(1);

            Course course2 = new Course();
            course2.setName("CSS");
            course2.setPrice("100$");
            course2.setCategoryId(1);

            Course course3 = new Course();
            course3.setName("PHP");
            course3.setPrice("400$");
            course3.setCategoryId(2);

            Course course4 = new Course();
            course4.setName("AJAX");
            course4.setPrice("200$");
            course4.setCategoryId(2);

            courseDao.insertCourse(course1);
            courseDao.insertCourse(course2);
            courseDao.insertCourse(course3);
            courseDao.insertCourse(course4);
        });
    }
}
