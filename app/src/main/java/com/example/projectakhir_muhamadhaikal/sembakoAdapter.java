package com.example.projectakhir_muhamadhaikal;

//kelompok 9
//Fadil Muhammad 221011401032
//Muhamad Haikal 221011402748

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public class sembakoAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sembako> sembakoList;
    DatabaseHelper db;
    public sembakoAdapter(Context context, ArrayList<Sembako> sembakoList) {
        this.context = context;
        this.sembakoList = sembakoList;
        db = new DatabaseHelper(context);
    }

    @Override
    public int getCount() { return sembakoList.size(); }

    @Override
    public Object getItem(int position) { return sembakoList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    static class ViewHolder {
        TextView txtInfo;
        Button btnEdit;
        Button btnDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sembako s = sembakoList.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_sembako,
                    parent, false);
            holder = new ViewHolder();
            holder.txtInfo = convertView.findViewById(R.id.txtInfo);
            holder.btnEdit = convertView.findViewById(R.id.btnEdit);
            holder.btnDelete = convertView.findViewById(R.id.btnDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtInfo.setText(s.nama_barang + " _ " + s.harga + "(" + s.stock + ")");

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, inputdatasembakoActivity.class);
            intent.putExtra("ID", s.id);
            intent.putExtra("NAMA BARANG", s.nama_barang);
            intent.putExtra("HARGA", s.harga);
            intent.putExtra("STOCK", s.stock);
            context.startActivity(intent);
        });
        
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Hapus Data")
                    .setMessage("Yakin ingin menghapus data ini?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        db.deleteData(s.id);
                        sembakoList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Data dihapus", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Tidak", null)
                    .show();
        });

        return convertView;
    }
}