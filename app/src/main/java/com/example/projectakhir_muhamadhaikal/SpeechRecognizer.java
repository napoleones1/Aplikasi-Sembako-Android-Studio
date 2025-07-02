package com.example.projectakhir_muhamadhaikal;

//kelompok 9
//Fadil Muhammad 221011401032
//Muhamad Haikal 221011402748

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class SpeechRecognizer extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView textSpeech;
    private Button btnSpeak;
    private DatabaseHelper db;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speech_recognizer);

        textSpeech = findViewById(R.id.textSpeech);
        btnSpeak = findViewById(R.id.btnSpeak);
        db = new DatabaseHelper(this);

        // Inisialisasi TTS
        tts = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                tts.setLanguage(new Locale("id", "ID"));
            }
        });

        btnSpeak.setOnClickListener(v -> startSpeechToText());
    }

    private void startSpeechToText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Silahkan bicara");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "Speech recognition tidak tersedia", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> hasil = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (hasil != null && !hasil.isEmpty()) {
                String perintah = hasil.get(0).toLowerCase();
                textSpeech.setText("Kamu bilang: " + perintah);

                String[] kata = perintah.split(" ");

                if (perintah.startsWith("tambah") && kata.length >= 4) {
                    StringBuilder namaBuilder = new StringBuilder();
                    for (int i = 1; i < kata.length - 2; i++) {
                        namaBuilder.append(kata[i]).append(" ");
                    }
                    String nama = namaBuilder.toString().trim();
                    String stok = kata[kata.length - 2];
                    String harga = kata[kata.length - 1];

                    boolean inserted = db.insertData(nama, harga, stok);
                    if (inserted) {
                        speak("Barang " + nama + " berhasil ditambahkan.");
                        textSpeech.append("\nBarang ditambahkan:\n" + nama +
                                "\nStok: " + stok + "\nHarga: Rp" + harga);
                    } else {
                        speak("Gagal menambahkan barang.");
                        textSpeech.append("\n❌ Gagal menambahkan barang.");
                    }

                } else if (perintah.startsWith("hapus") && kata.length >= 2) {
                    StringBuilder namaBuilder = new StringBuilder();
                    for (int i = 1; i < kata.length; i++) {
                        namaBuilder.append(kata[i]).append(" ");
                    }
                    String nama = namaBuilder.toString().trim();

                    Cursor cursor = db.cariBarang(nama);
                    if (cursor.moveToFirst()) {
                        String id = cursor.getString(0);
                        db.deleteData(id);
                        speak("Barang " + nama + " berhasil dihapus.");
                        textSpeech.append("\nBarang dihapus: " + nama);
                    } else {
                        speak("Barang tidak ditemukan.");
                        textSpeech.append("\n❌ Barang tidak ditemukan: " + nama);
                    }

                } else if (perintah.startsWith("update") && kata.length >= 3) {
                    StringBuilder namaBuilder = new StringBuilder();
                    for (int i = 1; i < kata.length - 1; i++) {
                        namaBuilder.append(kata[i]).append(" ");
                    }
                    String nama = namaBuilder.toString().trim();
                    String stokBaru = kata[kata.length - 1];

                    Cursor cursor = db.cariBarang(nama);
                    if (cursor.moveToFirst()) {
                        String id = cursor.getString(0);
                        String harga = cursor.getString(cursor.getColumnIndex("harga"));
                        db.updateData(id, nama, harga, stokBaru);
                        speak("Stok barang " + nama + " diperbarui.");
                        textSpeech.append("\nStok diperbarui menjadi " + stokBaru);
                    } else {
                        speak("Barang tidak ditemukan untuk diupdate.");
                        textSpeech.append("\nBarang tidak ditemukan untuk diupdate.");
                    }

                } else if (perintah.contains("tampilkan semua")) {
                    Cursor cursor = db.getAllData();
                    StringBuilder semua = new StringBuilder();
                    if (cursor.moveToFirst()) {
                        do {
                            String nama = cursor.getString(1);
                            String harga = cursor.getString(2);
                            String stok = cursor.getString(3);
                            semua.append("• ").append(nama).append(" - Rp").append(harga)
                                    .append(" - stok ").append(stok).append("\n");
                        } while (cursor.moveToNext());
                        speak("Berikut semua data sembako.");
                        textSpeech.append("\n\n📋 Semua Data:\n" + semua.toString());
                    } else {
                        speak("Data kosong.");
                        textSpeech.append("\nData kosong.");
                    }

                } else {
                    // Pencarian otomatis
                    Cursor cursor = db.cariBarang(perintah);
                    if (cursor.moveToFirst()) {
                        String nama = cursor.getString(cursor.getColumnIndex("nama_barang"));
                        String stok = cursor.getString(cursor.getColumnIndex("stock"));
                        String harga = cursor.getString(cursor.getColumnIndex("harga"));
                        String hasilPencarian = "\n\n📦 Hasil Pencarian:\n" +
                                "Nama: " + nama + "\n" +
                                "Stok: " + stok + "\n" +
                                "Harga: Rp" + harga;
                        speak("Barang ditemukan: " + nama + ". Harga " + harga + " stok " + stok);
                        textSpeech.append(hasilPencarian);
                    } else {
                        speak("Barang tidak ditemukan.");
                        textSpeech.append("\n🔍 Barang tidak ditemukan.");
                    }
                }
            }
        }
    }

    private void speak(String text) {
        if (tts != null) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }
}
