package com.example.todolistandroid.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistandroid.Controller.AddEditActivity;
import com.example.todolistandroid.Model.Todo;
import com.example.todolistandroid.Model.TodoListManager;
import com.example.todolistandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
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
        holder.dateTime.setText(todo.getDate() + " | " + todo.getTime());

        boolean isExpanded = todos.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        if(todo.isExpanded()) holder.arrow.setImageResource(R.drawable.ic_action_up_arrow);
        else holder.arrow.setImageResource(R.drawable.ic_action_down_arrow);

        holder.clickableLayout.setOnClickListener(view -> {
            todo.expanded();
            notifyItemChanged(position);
        });

        holder.removeFab.setOnClickListener(view -> {
            TodoListManager.getInstance().removeTodo(position);
            Toast.makeText(context, "Removido com sucesso!", Toast.LENGTH_SHORT).show();
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, TodoListManager.getInstance().getTodos().size());
        });

        holder.editFab.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddEditActivity.class);
            intent.putExtra("type", 1);
            intent.putExtra("index", position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }

    public class todoHolder extends RecyclerView.ViewHolder{

        LinearLayout todoLayout, expandableLayout;
        FrameLayout clickableLayout;
        TextView name;
        TextView desc;
        TextView dateTime;
        FloatingActionButton editFab, removeFab;
        ImageView arrow;

        public todoHolder(@NonNull View itemView) {
            super(itemView);

            this.todoLayout = itemView.findViewById(R.id.todoLayout);
            this.name = itemView.findViewById(R.id.todoList_name);
            this.desc = itemView.findViewById(R.id.todoList_desc);
            this.dateTime = itemView.findViewById(R.id.todoList_dateTime);
            this.expandableLayout = itemView.findViewById(R.id.expandable_todo);
            this.clickableLayout = itemView.findViewById(R.id.todoList_mainDetails);

            this.editFab = itemView.findViewById(R.id.fab_edit_todo);
            this.removeFab = itemView.findViewById(R.id.fab_delete_todo);
            arrow = itemView.findViewById(R.id.expanded_view_arrow);
        }
    }
}














