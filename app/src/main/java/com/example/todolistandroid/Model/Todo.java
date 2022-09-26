package com.example.todolistandroid.Model;

import android.util.Log;

import com.example.todolistandroid.Model.Database.TodoDAO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo {

    private int id, typeId;
    private String name;
    private String description;
    private Date date, time;
    private boolean expanded;

    public static final String timeFormat = "HH:mm";
    public static final String dateFormat = "dd/MM/yyyy";

    public static final String nullTimePlaceHolder = "-:-";
    public static final String nullDatePlaceHolder = "-/-/-";

    public Todo() {}

    public Todo(Integer id, String name, String description, String date, String time, int typeId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.setTime(time);
        this.expanded = false;
        this.setDate(date);
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        if(this.date == null) return Todo.nullDatePlaceHolder;
        return new SimpleDateFormat(this.dateFormat).format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
        Log.d("DEBUG", date.toString());
    }
    public void setDate(String date){
        try {
            this.date = new SimpleDateFormat(Todo.dateFormat).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void expanded() {
        this.expanded = !this.expanded;
    }

    public void setTime(Date time) {
        this.time = time;
    }
    public void setTime(String time){
        try {
            this.time = new SimpleDateFormat(Todo.timeFormat).parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        if(this.time == null) return Todo.nullTimePlaceHolder;
        return new SimpleDateFormat(Todo.timeFormat).format(this.time);
    }

    @Override
    public String toString() {
        return  name + '\n' +
                description + '\n' +
                (getDate().equals(Todo.nullDatePlaceHolder) ? "" : getDate()) + '\n' +
                (getTime().equals(Todo.nullTimePlaceHolder) ? "" : getTime()) + '\n';
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getType(){
        return TodoDAO.types.get(this.typeId);
    }
}
