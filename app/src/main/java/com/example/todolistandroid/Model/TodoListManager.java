package com.example.todolistandroid.Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoListManager {

    private static TodoListManager instance = new TodoListManager();
    private List<Todo> todos = new ArrayList<>();

    public static synchronized TodoListManager getInstance(){
        return instance;
    }

    private TodoListManager() {
        todos.add(new Todo("yes", "yes", new Date()));
        todos.add(new Todo("uau", "uauaua", new Date()));
        todos.add(new Todo("dsdssd", "no", new Date()));
        todos.add(new Todo("dsdssd", "no", new Date()));
        todos.add(new Todo("dsdssd", "no", new Date()));
        todos.add(new Todo("dsdssd", "no", new Date()));
        todos.add(new Todo("dsdssd", "no", new Date()));

    }

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

    public List<Todo> getTodos() {
        return todos;
    }
}
