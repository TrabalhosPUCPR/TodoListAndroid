package com.example.todolistandroid.View;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistandroid.Controller.AddEditActivity;
import com.example.todolistandroid.Model.Todo;
import com.example.todolistandroid.Model.TodoListManager;
import com.example.todolistandroid.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        boolean isExpanded = todos.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }

    public class todoHolder extends RecyclerView.ViewHolder{

        LinearLayout todoLayout, expandableLayout;
        TextView name;
        TextView desc;
        TextView date;
        FloatingActionButton editFab, removeFab;

        public todoHolder(@NonNull View itemView) {
            super(itemView);

            this.todoLayout = itemView.findViewById(R.id.todoLayout);
            this.name = itemView.findViewById(R.id.todoList_name);
            this.desc = itemView.findViewById(R.id.todoList_desc);
            this.date = itemView.findViewById(R.id.todoList_date);
            this.expandableLayout = itemView.findViewById(R.id.expandable_todo);

            this.editFab = itemView.findViewById(R.id.fab_edit_todo);
            this.removeFab = itemView.findViewById(R.id.fab_delete_todo);


            this.name.setOnClickListener(view -> {
                Todo todo = todos.get(getAdapterPosition());
                todo.expanded();
                notifyItemChanged(getAdapterPosition());
            });

            this.removeFab.setOnClickListener(view -> {
                TodoListManager.getInstance().removeTodo(this.getAdapterPosition());
                Toast.makeText(this.itemView.getContext(), "Removido com sucesso!", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            });

            this.editFab.setOnClickListener(view -> {
                Intent intent = new Intent(this.itemView.getContext(), AddEditActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("index", this.getAdapterPosition());
                this.itemView.getContext().startActivity(intent);
                // TODO: 9/16/22 arrumar retorno do resultado pra atualiza a tela 
            });
        }
    }
}














