package com.example.password_manager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class register extends AppCompatActivity {

    Button btnLogin, btnSignUp;
    EditText etName, etPassword, etConfirmPassword;
    int userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnRegister);
        etName = findViewById(R.id.userName);
        etPassword = findViewById(R.id.userPass);
        etConfirmPassword = findViewById(R.id.userConfirmPass);

        Intent intent=getIntent();
        userid=intent.getIntExtra("userid",-1);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
                Intent intent = new Intent(register.this, MainActivity.class);
                intent.putExtra("userid",userid);
                startActivity(intent);
                finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(register.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void addUser() {

            String name = etName.getText().toString().trim();
            String password = etPassword.getText().toString();
            String confirm= etConfirmPassword.getText().toString();
            if(password.equals(confirm)) {
                DatabaseHelper myDatabaseHelper = new DatabaseHelper(this);
                myDatabaseHelper.open();

                myDatabaseHelper.insert(name, password);

                myDatabaseHelper.close();
                Toast.makeText(register.this, "User Registered", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(register.this, "Wrong Password", Toast.LENGTH_SHORT).show();
            }
    }
}