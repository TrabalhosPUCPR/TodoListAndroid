package com.example.todolistandroid.Controller;

import android.content.Context;

import com.example.todolistandroid.Model.Todo;
import com.example.todolistandroid.Model.TodoListManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DatabaseManager {
    private final String FILENAME = "database.txt";
    Context context;

    public DatabaseManager(Context context){
        this.context = context;
        loadData();
    }

    public boolean writeData(Todo todo){
        try {
            OutputStream outputStream = context.openFileOutput(FILENAME, Context.MODE_APPEND);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            writer.write(todo.toString());
            writer.flush();
            writer.close();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateData(){
        try {
            context.deleteFile(FILENAME);
            OutputStream outputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            for(Todo todo : TodoListManager.getInstance().getTodos()) {
                writer.write(todo.toString());
            }
            writer.flush();
            writer.close();
            outputStream.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void loadData(){
        try {
            FileInputStream inputStream = context.openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            while(reader.ready()){
                Todo todo = new Todo();
                todo.setName(reader.readLine());
                todo.setDescription(reader.readLine());
                todo.setDate(new SimpleDateFormat(Todo.dateFormat).parse(reader.readLine()));
                todo.setTime(new SimpleDateFormat(Todo.timeFormat).parse(reader.readLine()));

                TodoListManager.getInstance().addTodo(todo);
            }
            reader.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
