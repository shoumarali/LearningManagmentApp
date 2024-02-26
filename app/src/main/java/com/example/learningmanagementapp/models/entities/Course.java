package com.example.learningmanagementapp.models.entities;


import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "course_table" , foreignKeys = {
        @ForeignKey(entity = Category.class,
                parentColumns = "id",
                childColumns = "category_id",
                onDelete = ForeignKey.CASCADE
        )
})
public class Course  extends BaseObservable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "price")
    private String price;
    @ColumnInfo(name = "category_id")
    private long categoryId;

    @Ignore
    public Course(){}

    public Course(String name,String price, long categoryId){
        this.name= name;
        this.price = price;
        this.categoryId = categoryId;
    }
    @Bindable
    public long getCategoryId() {
        return categoryId;
    }
    @Bindable
    public String getPrice() {
        return price;
    }
    @Bindable
    public long getId() {
        return id;
    }
    @Bindable
    public String getName() {
        return name;
    }

    public void setId(long id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.price);
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
}
