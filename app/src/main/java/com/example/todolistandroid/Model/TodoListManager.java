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
        todos.add(new Todo("test", "very nice test to do", new Date()));
        todos.add(new Todo("foo", "bar", new Date()));
        todos.add(new Todo("do this", "yes finish this", new Date()));
        todos.add(new Todo("hello", "say hello", new Date()));
        todos.add(new Todo("last example", "make a better test list", new Date()));

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

    public void setTodo(int index, Todo todos) {
        this.todos.set(index, todos);
    }
}
