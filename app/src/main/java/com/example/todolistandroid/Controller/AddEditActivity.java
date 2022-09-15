package com.example.todolistandroid.Controller;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistandroid.Model.Todo;
import com.example.todolistandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class AddEditActivity extends AppCompatActivity {
    private int position = -1;
    private Todo newTodo = new Todo("", "", new Date());
    private EditText newName;
    private EditText newDesc;
    private DatePicker newDate;
    private FloatingActionButton fabSave;
    private FloatingActionButton fabCancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_todo);

        //newName = findViewById(R.id.)
    }
}
