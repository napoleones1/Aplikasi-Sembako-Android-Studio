package com.example.projectakhir_muhamadhaikal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//kelompok 9
//Fadil Muhammad 221011401032
//Muhamad Haikal 221011402748

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText newUsernameEditText = findViewById(R.id.newUsername);
        EditText newPasswordEditText = findViewById(R.id.newPassword);
        EditText confirmPasswordEditText = findViewById(R.id.confirmPassword);
        Button registerButton = findViewById(R.id.registerButton);
        Button loginRedirectButton = findViewById(R.id.loginButton);


        registerButton.setOnClickListener(view -> {
            String username = newUsernameEditText.getText().toString();
            String password = newPasswordEditText.getText().toString();
            String confirmPassword = confirmPasswordEditText.getText().toString();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(Register.this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(Register.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Simpan username dan password ke SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("password", password); // Password bisa di-hash di sini
                editor.apply();

                Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                // Pindah ke Login Activity setelah registrasi
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loginRedirectButton.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, MainActivity.class); // Ganti dengan nama class Login kamu
            startActivity(intent);
            finish();
        });

    }
}

