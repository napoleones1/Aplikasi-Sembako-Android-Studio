package com.example.projectakhir_muhamadhaikal;

//kelompok 9
//Fadil Muhammad 221011401032
//Muhamad Haikal 221011402748

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Sembako.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "sembako";
    private static final String COL_ID = "id";
    private static final String COL_NAMA = "nama_barang";
    private static final String COL_HARGA = "harga";
    private static final String COL_STOCK = "stock";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAMA + " TEXT, " +
                COL_HARGA + " TEXT, " +
                COL_STOCK + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String nama_barang, String harga, String stock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAMA, nama_barang);
        values.put(COL_HARGA, harga);
        values.put(COL_STOCK, stock);
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateData(String id, String nama_barang, String harga, String stock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAMA, nama_barang);
        values.put(COL_HARGA, harga);
        values.put(COL_STOCK, stock);
        int result = db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{id});
        return result > 0;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{id});
    }

    // ✅ Tambahan: method cariBarang
    public Cursor cariBarang(String keyword) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_NAMA + " LIKE ?", new String[]{"%" + keyword + "%"});
    }
}
