package com.example.projectakhir_muhamadhaikal;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

//kelompok 9
//Fadil Muhammad 221011401032
//Muhamad Haikal 221011402748

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "login_channel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Cek permission notifikasi untuk Android 13 ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        Button registerButton = findViewById(R.id.registerButton);

        // Login button action
        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            String storedUsername = sharedPreferences.getString("username", null);
            String storedPassword = sharedPreferences.getString("password", null);

            if (username.equals(storedUsername) && password.equals(storedPassword)) {
                Intent intent = new Intent(MainActivity.this, Dashboard.class);
                startActivity(intent);
                finish();
                Toast.makeText(MainActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();

                // Tampilkan notifikasi login berhasil
                showLoginNotification();

            } else {
                Toast.makeText(MainActivity.this, "Username atau Password salah", Toast.LENGTH_SHORT).show();
            }
        });

        // Register button action
        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        });
    }

    // Method untuk menampilkan notifikasi login berhasil
    private void showLoginNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Buat channel notifikasi (untuk Android O ke atas)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Login Notification",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        // Bangun notifikasi
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Login Berhasil")
                .setContentText("Selamat datang di aplikasi!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        // Tampilkan notifikasi
        notificationManager.notify(1, notification);
    }
}