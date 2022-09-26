package com.example.todolistandroid.Controller;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todolistandroid.Model.Todo;
import com.example.todolistandroid.Model.TodoListManager;
import com.example.todolistandroid.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class AddEditActivity extends AppCompatActivity {
    private Todo newTodo;
    private EditText newName;
    private EditText newDesc;
    private EditText newTime;

    int index;

    private EditText newDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_edit_todo);

        newTodo = new Todo();

        newName = findViewById(R.id.todo_edit_name);
        newDesc = findViewById(R.id.todo_edit_desc);
        newDate = findViewById(R.id.todo_edit_date);
        newTime = findViewById(R.id.todo_edit_time);
        this.index = this.getIntent().getIntExtra("index", -1);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(index != -1 ? "Edit" : "Add");

        if(index != -1){
            newTodo = TodoListManager.getInstance().get(index);

            newName.setText(newTodo.getName());
            newDesc.setText(newTodo.getDescription());
            newDate.setText(newTodo.getDate());
            newTime.setText(newTodo.getTime());
        }

        newDate.setOnClickListener(view -> {
            DatePickerDialog.OnDateSetListener datePicker = (datePicker1, year, month, day) -> newDate.setText(String.format("%02d/%02d/%04d", day, month + 1, year));
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.setTitle("Selecione o dia");
            datePickerDialog.show();
        });
        newTime.setOnClickListener(view -> {
            TimePickerDialog.OnTimeSetListener timePicker = (timePicker1, hour, minute) -> newTime.setText(String.format("%02d:%02d", hour, minute));
            Calendar calendar = Calendar.getInstance();
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, timePicker, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timePickerDialog.setTitle("Selecione o horario");
            timePickerDialog.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.addedit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(!(item.getItemId() == R.id.addedit_apply)){
            setResult(RESULT_CANCELED);
            finish();
            return true;
        }
        if (newName.getText().toString().isEmpty()){
            Toast.makeText(this, "Nome não pode estar vazio!", Toast.LENGTH_SHORT).show();
            return true;
        }
        newTodo.setName(newName.getText().toString());
        newTodo.setDescription(newDesc.getText().toString());

        try {
            if (!newDate.getText().toString().isEmpty())
                newTodo.setDate(new SimpleDateFormat(Todo.dateFormat).parse(newDate.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            if (!newTime.getText().toString().isEmpty())
                newTodo.setTime(new SimpleDateFormat(Todo.timeFormat).parse(newTime.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(index == -1){
            TodoListManager.getInstance().add(newTodo);
        }else{
            TodoListManager.getInstance().edit(newTodo, index);
        }

        Intent intent = new Intent();
        intent.putExtra("TodoName", newTodo.getName());
        intent.putExtra("index", index);
        setResult(RESULT_OK, intent);
        finish();
        return true;
    }
}













