package com.example.todolistandroid.Model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Todo {

    private String name;
    private String description;
    private Date date, time;
    private boolean expanded;

    public static final String timeFormat = "HH:mm";
    public static final String dateFormat = "dd/MM/yyyy";

    public Todo(String name, String description, Date date, Date time) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.expanded = false;
        this.time = time;
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
        if(this.date == null) return "-/-/-";
        return new SimpleDateFormat(this.dateFormat).format(this.date);
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getTime() {
        if(this.time == null) return "-:-";
        return new SimpleDateFormat(this.timeFormat).format(this.time);
    }
}
