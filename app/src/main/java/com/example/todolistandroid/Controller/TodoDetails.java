package com.example.todolistandroid.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.todolistandroid.Model.Todo;
import com.example.todolistandroid.Model.TodoListManager;
import com.example.todolistandroid.R;
import java.util.Objects;

public class TodoDetails extends AppCompatActivity {

    private TextView desc, date, time;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);

        this.desc = findViewById(R.id.todo_detail_desc);
        this.date = findViewById(R.id.todo_detail_date);
        this.time = findViewById(R.id.todo_detail_time);

        this.index = getIntent().getIntExtra("index", -1);

        Todo todo = TodoListManager.getInstance().getTodo(this.index);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(todo.getName());

        this.desc.setText(todo.getDescription());
        this.date.setText(todo.getDate());
        this.time.setText(todo.getTime());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return false;
    }
}