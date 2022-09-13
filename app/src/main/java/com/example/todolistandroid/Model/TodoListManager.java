package com.example.todolistandroid.Model;

import java.util.ArrayList;
import java.util.List;

public class TodoListManager {

    private static TodoListManager instance = new TodoListManager();
    private List<Todo> todos = new ArrayList<>();

    public static synchronized TodoListManager getInstance(){
        return instance;
    }

    public TodoListManager() {}

    public void addTodo(Todo todo){
        this.todos.add(todo);
    }
    public Todo getTodo(int index){
        return this.todos.get(index);
    }
    public Todo removeTodo(int index){
        Todo foo = this.todos.get(index);
        this.todos.remove(index);
        return foo;
    }
}
