package com.UliRinduDebora.savings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Result extends AppCompatActivity {
    TextView kategori, jumlah, deskripsi, tanggal, sumberskala, textsumberskala, textToolbar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        kategori =findViewById(R.id.kategori);
        jumlah = findViewById(R.id.jumlah);
        deskripsi = findViewById(R.id.deskripsi);
        tanggal = findViewById(R.id.tanggal);
        sumberskala = findViewById(R.id.sumberskala);
        textsumberskala = findViewById(R.id.textsumberskala);
        textToolbar = findViewById(R.id.textToolbar);

        kategori.setText(getIntent().getExtras().getString("kategori"));
        jumlah.setText("Rp."+getIntent().getExtras().getInt("uang"));
        deskripsi.setText(getIntent().getExtras().getString("deskripsi"));
        tanggal.setText(getIntent().getExtras().getString("tanggal"));
        textToolbar.setText("Detail "+getIntent().getExtras().getString("kategori"));

        if(kategori.getText().toString().equals("Pemasukan")){
            textsumberskala.setText("Sumber Uang Masuk :");
            sumberskala.setText(getIntent().getExtras().getString("sumber"));
        }else{
            textsumberskala.setText("Skala Prioritas :");
            sumberskala.setText(getIntent().getExtras().getString("prioritas"));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Application On Start", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Application On Stop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Application On Restart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Application On Resume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Application On Pause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Selamat Tinggal", Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
        onBackPressed();
    }
}