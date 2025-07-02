package com.example.projectakhir_muhamadhaikal;

//kelompok 9
//Fadil Muhammad 221011401032
//Muhamad Haikal 221011402748

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Dashboard extends AppCompatActivity {


    private DrawerLayout drawerLayout;
    private ListView navList;
    private ImageView menuButton;
    private LinearLayout sensorLayout, mapsLayout, speechRecognizerlayout, lihatDataLayout;

    private String[] drawerItems = {"Dashboard", "Sensor", "Keluar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Inisialisasi komponen layout
        drawerLayout = findViewById(R.id.drawer_layout);
        navList = findViewById(R.id.nav_list);
        menuButton = findViewById(R.id.menu_button);
        sensorLayout = findViewById(R.id.Tambah_layout);
        mapsLayout = findViewById(R.id.maps_layout);
        lihatDataLayout = findViewById(R.id.lihatdata_layout);
        speechRecognizerlayout = findViewById(R.id.speechRecognizer_layout);


        // Set adapter ListView drawer
        navList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, drawerItems));

        // Klik item di drawer menu
        navList.setOnItemClickListener((parent, view, position, id) -> {
            switch (position) {
                case 0: // Dashboard
                    startActivity(new Intent(Dashboard.this, Dashboard.class));
                    break;
                case 1: // Input data
                    startActivity(new Intent(Dashboard.this, sensorActivity.class));
                    break;
                case 2: // Keluar
                    finish();
                    break;
                default:
                    Toast.makeText(this, "Menu tidak dikenali", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(navList);
        });

        // Tombol menu drawer
        menuButton.setOnClickListener(v -> drawerLayout.openDrawer(navList));

        // Klik fitur Sensor
        sensorLayout.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, inputdatasembakoActivity.class));
            Toast.makeText(Dashboard.this, "Fitur Tambah Sembako diklik", Toast.LENGTH_SHORT).show();
        });

        // Klik fitur maps
        lihatDataLayout.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, LihatdataActivity.class));
            Toast.makeText(Dashboard.this, "Fitur Lihat Sembako diklik", Toast.LENGTH_SHORT).show();
        });

        speechRecognizerlayout.setOnClickListener(v -> {
            startActivity(new Intent( Dashboard.this, SpeechRecognizer.class));
            Toast.makeText(Dashboard.this, "Fitur Speech to text diklik", Toast.LENGTH_SHORT).show();
        });

        mapsLayout.setOnClickListener(v -> {
            startActivity(new Intent(Dashboard.this, Maps.class));
            Toast.makeText(Dashboard.this, "Fitur Maps diklik", Toast.LENGTH_SHORT).show();
        });


    }
}