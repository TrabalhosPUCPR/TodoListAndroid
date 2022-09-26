package com.example.todolistandroid.Model;

import android.content.Context;

import com.example.todolistandroid.Model.Database.DAO;
import com.example.todolistandroid.Model.Database.TodoDAO;

import java.util.ArrayList;
import java.util.List;

public class TodoListManager implements DAO<Todo> {

    private static final TodoListManager instance = new TodoListManager();
    private List<Todo> todos = new ArrayList<>();
    private TodoDAO dao;

    public static synchronized TodoListManager getInstance(){
        return instance;
    }

    private TodoListManager() {}

    public void loadDatabase(Context context){
        this.dao = new TodoDAO(context);
        List<Todo> list = this.dao.getList();
        if(list != null){
            this.todos = list;
        }
    }

    public boolean add(Todo todo){
        if(this.dao.add(todo)){
            this.todos.add(todo);
            return true;
        }
        return false;
    }
    @Override
    public List<Todo> getList(){
        return this.todos;
    }
    public boolean remove(int index){
        if(dao.remove(this.todos.get(index).getId())){
            this.todos.remove(index);
            return true;
        }
        return false;
    }

    @Override
    public boolean clearAll() {
        if(dao.clearAll()){
            this.todos = new ArrayList<>();
            return true;
        }
        return false;
    }

    @Override
    public boolean edit(Todo todo, int index) {
        if(dao.edit(todo, this.todos.get(index).getId())){
            this.todos.set(index, todo);
            return true;
        }
        return false;
    }

    public Todo get(int id) {
        return todos.get(id);
    }
}
