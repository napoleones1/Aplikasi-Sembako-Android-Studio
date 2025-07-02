package com.example.projectakhir_muhamadhaikal;

//kelompok 9
//Fadil Muhammad 221011401032
//Muhamad Haikal 221011402748

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class inputdatasembakoActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText etNamaBarang, etHarga, etStock;
    Button btnSave;
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputdatasembako);
        db = new DatabaseHelper(this);
        etNamaBarang = findViewById(R.id.etNamaBarang);
        etHarga = findViewById(R.id.etHarga);
        etStock = findViewById(R.id.etStock);
        btnSave = findViewById(R.id.btnSave);

        Intent intent = getIntent();
        if (intent !=null && intent.hasExtra("ID")) {
            id =intent.getStringExtra("ID");
            etNamaBarang.setText(intent.getStringExtra("NAMA BARANG"));
            etHarga.setText(intent.getStringExtra("HARGA"));
            etStock.setText(intent.getStringExtra("STOCK"));
            btnSave.setText("Update");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama_barang = etNamaBarang.getText().toString();
                String harga = etHarga.getText().toString();
                String stock = etStock.getText().toString();
                if (id.isEmpty()) {
                    db.insertData(nama_barang, harga, stock);
                    Toast.makeText(inputdatasembakoActivity.this, "Data disimpan", Toast.LENGTH_SHORT).show();
                } else {
                    db.updateData(id, nama_barang, harga, stock);
                    Toast.makeText(inputdatasembakoActivity.this, "Data Diperbarui", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}