package com.example.todolistandroid.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistandroid.Model.Todo;
import com.example.todolistandroid.Model.TodoListManager;
import com.example.todolistandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

public class AddEditActivity extends AppCompatActivity {
    private Todo newTodo;
    private EditText newName;
    private EditText newDesc;

    // TODO: 9/15/22
    // https://stackoverflow.com/questions/14933330/datepicker-how-to-popup-datepicker-when-click-on-edittext
    private EditText newDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_todo);

        newTodo = new Todo("", "", new Date());

        newName = findViewById(R.id.todo_edit_name);
        newDesc = findViewById(R.id.todo_edit_desc);
        newDate = findViewById(R.id.todo_edit_date);

        FloatingActionButton fabSave = findViewById(R.id.todo_edit_apply);
        FloatingActionButton fabCancel = findViewById(R.id.todo_edit_cancel);
        int index = this.getIntent().getIntExtra("index", -1);

        if(index != -1){

            newTodo = TodoListManager.getInstance().getTodo(index);

            newName.setText(newTodo.getName());
            newDesc.setText(newTodo.getDescription());
            newDate.setText(newTodo.getDate().toString());
        }

        newName.setOnClickListener(view -> {
            if(newName.getText().toString().equals(getString(R.string.todo_value_name))){
                newName.setText("");
            }
        });
        newDesc.setOnClickListener(view -> {
            if(newDesc.getText().toString().equals(getString(R.string.todo_value_desc))){
                newDesc.setText("");
            }
        });

        fabSave.setOnClickListener(view -> {
            newTodo.setName(newName.getText().toString());
            newTodo.setDescription(newDesc.getText().toString());
            // TODO: 9/15/22
            // arruma esse ngc pra receber a data
            //newTodo.setDate(newDate.getText().toString());
            newTodo.setDate(new Date());

            if(this.getIntent().getIntExtra("index", -1) == -1){
                TodoListManager.getInstance().addTodo(newTodo);
            }else{
                TodoListManager.getInstance().setTodo(index, newTodo);
            }

            Intent intent = new Intent();
            intent.putExtra("TodoName", newTodo.getName());
            setResult(RESULT_OK, intent);
            finish();
        });

        fabCancel.setOnClickListener(view -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}













