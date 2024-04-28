package com.example.password_manager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DeletedAdapter extends RecyclerView.Adapter<DeletedAdapter.ViewHolder> {


    private ArrayList<User> deletedEntries;
    private Context context;

    public DeletedAdapter(Context context, ArrayList<User> deletedEntries) {
        this.context = context;
        this.deletedEntries = deletedEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recycle_bin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User deletedEntry = deletedEntries.get(position);
        holder.tvName.setText(deletedEntry.getName());
        holder.tvEmail.setText(deletedEntry.getPassword());



    }

    @Override
    public int getItemCount() {
        return deletedEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvDName);
            tvEmail = itemView.findViewById(R.id.tvDPassword);
        }
    }
}
