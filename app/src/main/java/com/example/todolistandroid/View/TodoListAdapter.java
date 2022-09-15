package com.example.todolistandroid.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistandroid.Model.Todo;
import com.example.todolistandroid.R;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.todoHolder> {

    private List<Todo> todos;
    private Context context;

    public TodoListAdapter(List<Todo> todos, Context context){
        this.todos = todos;
        this.context = context;
    }

    @NonNull
    @Override
    public TodoListAdapter.todoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.todo_list_adapter, parent, false);
        return new todoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListAdapter.todoHolder holder, int position) {
        Todo todo = todos.get(position);

        holder.name.setText(todo.getName());
        holder.desc.setText(todo.getDescription());
        holder.date.setText(todo.getDate().toString());
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }

    public class todoHolder extends RecyclerView.ViewHolder{

        LinearLayout todoLayout;
        TextView name;
        TextView desc;
        TextView date;

        public todoHolder(@NonNull View itemView) {
            super(itemView);

            this.todoLayout = itemView.findViewById(R.id.todoLayout);
            this.name = itemView.findViewById(R.id.todoList_name);
            this.desc = itemView.findViewById(R.id.todoList_desc);
            this.date = itemView.findViewById(R.id.todoList_date);
        }
    }
}














