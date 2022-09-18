package com.example.todolistandroid.Controller;

import android.content.Context;
import android.util.Log;

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
import java.util.Objects;

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
            writer.close();
            outputStream.close();
            return true;
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
            writer.close();
            outputStream.close();
            return true;
        } catch (IOException e) {
            Log.d("UPDATEDATA", "UPDATE: " + e);
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
                try{
                    todo.setDate(new SimpleDateFormat(Todo.dateFormat).parse(reader.readLine()));
                }catch (ParseException ignored){
                    Log.d("LOADDATA", "TIME: " + ignored);
                }
                try{
                    todo.setTime(Objects.requireNonNull(new SimpleDateFormat(Todo.timeFormat).parse(reader.readLine())));
                }catch (ParseException ignored){
                    Log.d("LOADDATA", "TIME: " + ignored);
                }
                TodoListManager.getInstance().addTodo(todo);
            }
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("LOADDATA", e.toString());
        }
    }
}
