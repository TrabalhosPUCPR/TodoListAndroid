package com.example.todolistandroid.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistandroid.Controller.AddEditActivity;
import com.example.todolistandroid.Controller.TodoDetails;
import com.example.todolistandroid.Model.OnAdapterItemClickListener;
import com.example.todolistandroid.Model.Todo;
import com.example.todolistandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.todoHolder> {

    private List<Todo> todos;
    private Context context;

    private OnAdapterItemClickListener listener;

    private ActivityResultLauncher<Intent> resultEditTodo;

    public TodoListAdapter(List<Todo> todos, Context context, OnAdapterItemClickListener listener, ActivityResultLauncher<Intent> resultEditTodo) {
        this.todos = todos;
        this.context = context;
        this.resultEditTodo = resultEditTodo;
        this.listener = listener;
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
            AlertDialog.Builder dialog = new AlertDialog.Builder(context, R.style.CustomAlertDialog);
            dialog.setTitle(context.getString(R.string.title));
            dialog.setMessage("Voce tem certeza que gostaria de remover?");
            dialog.setPositiveButton("Sim", (dialogInterface, i) -> {
                listener.onAdapterItemClickListener(position);
            });
            dialog.setNegativeButton("Não", null);
            dialog.show();
        });

        holder.editFab.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddEditActivity.class);
            intent.putExtra("type", 1);
            intent.putExtra("index", position);
            resultEditTodo.launch(intent);
        });

        holder.detailFab.setOnClickListener(view -> {
            Intent intent = new Intent(context, TodoDetails.class);
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
        FloatingActionButton editFab, removeFab, detailFab;
        ImageView arrow;

        public todoHolder(@NonNull View itemView) {
            super(itemView);

            this.todoLayout = itemView.findViewById(R.id.todoLayout);
            this.name = itemView.findViewById(R.id.todoList_name);
            this.desc = itemView.findViewById(R.id.todoList_desc);
            this.dateTime = itemView.findViewById(R.id.todoList_dateTime);
            this.expandableLayout = itemView.findViewById(R.id.expandable_todo);
            this.clickableLayout = itemView.findViewById(R.id.todoList_mainDetails);
            this.detailFab = itemView.findViewById(R.id.fab_detail_todo);

            this.editFab = itemView.findViewById(R.id.fab_edit_todo);
            this.removeFab = itemView.findViewById(R.id.fab_delete_todo);
            arrow = itemView.findViewById(R.id.expanded_view_arrow);
        }
    }
}














