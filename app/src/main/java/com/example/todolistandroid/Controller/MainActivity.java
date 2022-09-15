package com.example.todolistandroid.Controller;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.todolistandroid.Model.TodoListManager;
import com.example.todolistandroid.R;
import com.example.todolistandroid.View.TodoListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvTodos;
    private TodoListAdapter todoAdapter;
    private FloatingActionButton addTodoFab;

    private ActivityResultLauncher<Intent> resultAddTodo;
    private ActivityResultLauncher<Intent> resultEditTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createTodoRcvView();
        registerCallbacks();
        addTodoFab = findViewById(R.id.mainFab);
        addTodoFab.setOnClickListener(view -> addTodo());
    }

    void registerCallbacks(){
        resultAddTodo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK){
                todoAdapter.notifyDataSetChanged();

            }
        });
    }

    void createTodoRcvView(){
        rcvTodos = findViewById(R.id.rcvTodos);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rcvTodos.setLayoutManager(llm);
        todoAdapter = new TodoListAdapter(TodoListManager.getInstance().getTodos(), this);
        rcvTodos.setAdapter(todoAdapter);
    }

    void addTodo(){
        Intent intent = new Intent(this, AddEditActivity.class);
        startActivity(intent);
    }
}