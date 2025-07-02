package com.example.projectakhir_muhamadhaikal;

//kelompok 9
//Fadil Muhammad 221011401032
//Muhamad Haikal 221011402748

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class LihatdataActivity extends AppCompatActivity {
    DatabaseHelper db;
    ListView listView;
    ArrayList <Sembako> sembakoList = new ArrayList<>();
    sembakoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihatdata);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        sembakoList.clear();
        Cursor cursor = db.getAllData();
        if (cursor.moveToFirst()) {
            do {
                Sembako s = new Sembako(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );
                sembakoList.add(s);
            } while (cursor.moveToNext());
        }
        adapter = new sembakoAdapter(this, sembakoList);
        listView.setAdapter(adapter);
    }
}