package com.example.password_manager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private ArrayList<User> users;
    public static ArrayList<User> deletedUsers; // List to store deleted users
    private Context context;
    private DatabaseHelper database; // Add database reference
    ImageView ivDelete, ivEdit, ivUrl;

    // Constructor
    public UserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
        this.deletedUsers = new ArrayList<>(); // Initialize the list
        this.database = new DatabaseHelper(context); // Initialize database
        this.database.open(); // Open the database
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_single_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.tvName.setText(user.getName());
        holder.tvPassword.setText(user.getPassword());
        holder.tvUrl.setText(user.getUrl());

        ivDelete = holder.itemView.findViewById(R.id.ivDelete);
        ivEdit = holder.itemView.findViewById(R.id.ivEdit);


        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog editDialog = new AlertDialog.Builder(context).create();
                View view = LayoutInflater.from(context).inflate(R.layout.activity_add_name, null, false);
                editDialog.setView(view);

                EditText etName = view.findViewById(R.id.etName);
                EditText etPassword = view.findViewById(R.id.etPassword);
                EditText etUrl = view.findViewById(R.id.etUrl);
                Button btnUpdate = view.findViewById(R.id.btnAdd);
                Button btnCancel = view.findViewById(R.id.btnCancel);

                etName.setText(users.get(holder.getAdapterPosition()).getName());
                etPassword.setText(users.get(holder.getAdapterPosition()).getPassword());
                etUrl.setText(users.get(holder.getAdapterPosition()).getUrl());

                editDialog.show();

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editDialog.dismiss();
                    }
                });

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = etName.getText().toString().trim();
                        String password = etPassword.getText().toString();
                        String url = etUrl.getText().toString();
                        DatabaseHelper myDatabaseHelper = new DatabaseHelper(context);
                        myDatabaseHelper.open();
                        myDatabaseHelper.updateUrl(users.get(holder.getAdapterPosition()).getId(),
                                name, password, url);
                        myDatabaseHelper.close();

                        editDialog.dismiss();

                        users.get(holder.getAdapterPosition()).setName(name);
                        users.get(holder.getAdapterPosition()).setPassword(password);
                        users.get(holder.getAdapterPosition()).setUrl(url);
                        notifyDataSetChanged();

                    }
                });
            }
        });

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
                deleteDialog.setTitle("Confirmation");
                deleteDialog.setMessage("Do you really want to delete it?");
                deleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Store the deleted user before removing it from the list
                        User deletedUser = users.get(holder.getAdapterPosition());
                        deletedUsers.add(deletedUser);

                        // Delete the user from the database and the list
                        DatabaseHelper database = new DatabaseHelper(context);
                        database.open();
                        database.deleteUrl(deletedUser.getId());

                        database.close();

                        users.remove(holder.getAdapterPosition());
                        notifyDataSetChanged();
                    }
                });
                deleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel the deletion
                    }
                });

                deleteDialog.show();
            }
        });
    }

    // Method to get the list of deleted users
    public ArrayList<User> getDeletedUsers() {
        return deletedUsers;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvPassword;
        TextView tvUrl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPassword = itemView.findViewById(R.id.tvPassword);
            tvUrl = itemView.findViewById(R.id.tvURL);
        }
    }
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if (database != null) {
            database.close(); // Close the database when the adapter is destroyed
        }
    }
}