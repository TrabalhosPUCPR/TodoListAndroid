package com.example.todolistandroid.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.todolistandroid.Model.Todo;
import com.example.todolistandroid.Model.TodoListManager;
import com.example.todolistandroid.R;
import java.util.Objects;

public class TodoDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);

        TextView desc = findViewById(R.id.todo_detail_desc);
        TextView date = findViewById(R.id.todo_detail_date);
        TextView time = findViewById(R.id.todo_detail_time);

        int index = getIntent().getIntExtra("index", -1);

        Todo todo = TodoListManager.getInstance().get(index);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle(todo.getName());

        desc.setText(todo.getDescription());
        date.setText(todo.getDate());
        time.setText(todo.getTime());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }
}