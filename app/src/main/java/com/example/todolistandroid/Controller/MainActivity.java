package com.example.todolistandroid.Controller;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolistandroid.Model.OnAdapterItemClickListener;
import com.example.todolistandroid.Model.TodoListManager;
import com.example.todolistandroid.R;
import com.example.todolistandroid.View.TodoListAdapter;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements OnAdapterItemClickListener {

    private TodoListAdapter todoAdapter;

    private RecyclerView rcvTodos;

    private ActivityResultLauncher<Intent> resultAddTodo;
    private ActivityResultLauncher<Intent> resultEditTodo;

    TextView emptyPlaceholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_help);

        emptyPlaceholder = findViewById(R.id.emptyRecyclerPlaceHolder);

        registerCallbacks();
        createTodoRcvView();

        updateIfRcvEmpty();
    }

    private void registerCallbacks(){
        resultAddTodo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK) {
                emptyPlaceholder.setVisibility(View.GONE);
                rcvTodos.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Tarefa adicionada", Toast.LENGTH_SHORT).show();
                todoAdapter.notifyItemChanged(TodoListManager.getInstance().getTodos().size() - 1);
            }
        });
        resultEditTodo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if(result.getResultCode() == RESULT_OK) {
                Toast.makeText(this, "Tarefa editada", Toast.LENGTH_SHORT).show();
                todoAdapter.notifyItemChanged(result.getData().getIntExtra("index", -1));
            }
        });
    }
    public void updateIfRcvEmpty(){
        if(TodoListManager.getInstance().getTodos().isEmpty()){
            findViewById(R.id.rcvTodos).setVisibility(View.GONE);
            findViewById(R.id.emptyRecyclerPlaceHolder).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.emptyRecyclerPlaceHolder).setVisibility(View.GONE);
            findViewById(R.id.rcvTodos).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            addTodo();
        }else{
            PopupMenu popupMenu = new PopupMenu(this, findViewById(R.id.popup_menu), Gravity.START);
            popupMenu.getMenuInflater().inflate(R.menu.dropdown_help, popupMenu.getMenu());
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()){
                    case R.id.helpDropdown_About:
                        AlertDialog.Builder dialogAbout = new AlertDialog.Builder(this, R.style.CustomAlertDialog);
                        dialogAbout.setTitle(R.string.title);
                        dialogAbout.setMessage("Desenvolvido por:\nLeonardo Knight\n");
                        dialogAbout.setNegativeButton("Ok", null);
                        dialogAbout.show();
                        break;
                    case R.id.helpDropdown_help:
                        Uri uri = Uri.parse(getString(R.string.githubLink));
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                        break;
                }
                return true;
            });
        }
        return true;
    }

    void createTodoRcvView(){
        rcvTodos = findViewById(R.id.rcvTodos);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        rcvTodos.setLayoutManager(llm);
        todoAdapter = new TodoListAdapter(TodoListManager.getInstance().getTodos(), this, this, resultEditTodo);
        rcvTodos.setAdapter(todoAdapter);
    }

    void addTodo(){
        Intent intent = new Intent(this, AddEditActivity.class);
        intent.putExtra("type", 0);
        resultAddTodo.launch(intent);
    }

    @Override
    public void onAdapterItemClickListener(int position) {
        TodoListManager.getInstance().removeTodo(position);
        this.todoAdapter.notifyItemRemoved(position);
        this.todoAdapter.notifyItemRangeChanged(position, TodoListManager.getInstance().getTodos().size());
        updateIfRcvEmpty();
        Toast.makeText(this, "Tarefa removida", Toast.LENGTH_SHORT).show();
    }
}