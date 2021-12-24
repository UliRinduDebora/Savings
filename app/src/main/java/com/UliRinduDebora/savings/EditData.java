package com.UliRinduDebora.savings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Calendar;

public class EditData extends AppCompatActivity {

    int size = 0;
    RadioGroup kategori;
    RadioButton pemasukan, pengeluaran;
    String kategoriTerpilih = "", skala = "", sumberUang = "";
    EditText jumlah, deskripsi, tanggal;
    SeekBar seekBar;
    CheckBox gaji, bonus, freelance, investasi, konfirmasi;
    DatePickerDialog datePickerDialog;
    RelativeLayout prioritas, sumber;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        kategori =findViewById(R.id.kategori);
        jumlah = findViewById(R.id.etJumlah);
        deskripsi = findViewById(R.id.etDeskripsi);
        tanggal = findViewById(R.id.etTanggal);
        seekBar = findViewById(R.id.seekBar);
        gaji = findViewById(R.id.gaji);
        bonus = findViewById(R.id.bonus);
        freelance = findViewById(R.id.freelance);
        investasi = findViewById(R.id.investasi);
        prioritas = findViewById(R.id.layoutprioritas);
        sumber = findViewById(R.id.layoutsumber);
        konfirmasi = findViewById(R.id.konfirmasi);
        pemasukan = findViewById(R.id.pemasukan);
        pengeluaran = findViewById(R.id.pengeluaran);

        jumlah.setText(String.valueOf(getIntent().getExtras().getInt("uang")));
        deskripsi.setText(getIntent().getExtras().getString("deskripsi"));
        tanggal.setText(getIntent().getExtras().getString("tanggal"));

        sumberUang = getIntent().getExtras().getString("sumber");
        String[] sum = sumberUang.split(" ");
        for (String s : sum) {
            switch (s) {
                case "Gaji":
                    gaji.setChecked(true);
                    break;
                case "Bonus":
                    bonus.setChecked(true);
                    break;
                case "Freelance":
                    freelance.setChecked(true);
                    break;
                case "Investasi":
                    investasi.setChecked(true);
                    break;
            }
        }

        skala = getIntent().getExtras().getString("prioritas");
        if(skala.equals("Important")){
            seekBar.setProgress(1);
        }
        else if(skala.equals("Not Important")){
            seekBar.setProgress(-1);
        }
        else{
            seekBar.setProgress(0);
        }

        kategoriTerpilih = getIntent().getExtras().getString("kategori");
        if(kategoriTerpilih.equals("Pemasukan")){
            pemasukan.setChecked(true);
            prioritas.setVisibility(View.GONE);
            sumber.setVisibility(View.VISIBLE);
        }
        else{
            pengeluaran.setChecked(true);
            prioritas.setVisibility(View.VISIBLE);
            sumber.setVisibility(View.GONE);
        }

        //Kategori
        kategori.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.pemasukan:
                        kategoriTerpilih = "Pemasukan";
                        prioritas.setVisibility(View.GONE);
                        sumber.setVisibility(View.VISIBLE);
                        skala = "";
                        break;
                    case R.id.pengeluaran:
                        kategoriTerpilih = "Pengeluaran";
                        prioritas.setVisibility(View.VISIBLE);
                        sumber.setVisibility(View.GONE);
                        sumberUang = "";
                        break;
                }
            }
        });


        //Tanggal
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                datePickerDialog = new DatePickerDialog(EditData.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String tgl = dayOfMonth + "/"+ (monthOfYear + 1) + "/" + year;
                                tanggal.setText(tgl);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        //Seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            int progress = 0;

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                size = size + (progressValue - progress);
                progress = progressValue;
                if(size == -1){
                    skala = "Not Important";
                }
                else if(size == 1){
                    skala = "Important";
                }
                else{
                    skala = "Neutral";
                }
            }
        });
    }

    public void submit(View view) {
        int id = getIntent().getExtras().getInt("id");
        sumberUang = "";
        if(kategoriTerpilih.equals("Pemasukan")){
            if (gaji.isChecked()) {
                sumberUang += "- Gaji \n";
            }
            if (bonus.isChecked()) {
                sumberUang += "- Bonus \n";
            }
            if (freelance.isChecked()) {
                sumberUang += "- Freelance \n";
            }
            if (investasi.isChecked()) {
                sumberUang += "- Hasil Investasi \n";
            }
            if (sumberUang.equals("")) {
                sumberUang = " -";
            }
        }

        if (jumlah.getText().toString().isEmpty() || deskripsi.getText().toString().isEmpty() ||
                kategoriTerpilih.equals("") || tanggal.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Data belum lengkap", Toast.LENGTH_SHORT).show();
        } else if (!konfirmasi.isChecked()) {
            Toast.makeText(getApplicationContext(), "Pastikan data sudah benar", Toast.LENGTH_SHORT).show();
        } else {
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            SetterGetterData sgd = new SetterGetterData();
            sgd.setKategori(kategoriTerpilih);
            sgd.setUang(Integer.parseInt(jumlah.getText().toString()));
            sgd.setDeskripsi(deskripsi.getText().toString());
            sgd.setTanggal(tanggal.getText().toString());
            sgd.setPrioritas(skala);
            sgd.setSumber(sumberUang);
            boolean input;
            input = dbHelper.perbaharuiTransaksi(sgd, id);
            if (input) {
                Toast.makeText(getApplicationContext(), "Transaksi berhasil diperbaharui", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Kesalahan terjadi!", Toast.LENGTH_SHORT).show();
            }
            dbHelper.close();
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            String message = "";
            message+="Jumlah Uang: Rp." + Integer.parseInt(jumlah.getText().toString());
            message+="\nDeskripsi: " + deskripsi.getText().toString();
            message+="\nTanggal: " + tanggal.getText().toString();
            if(kategoriTerpilih.equals("Pemasukan"))
                message+="\nSumber Uang Masuk: " + sumberUang;
            else
                message+="\nSkala Prioritas: " + skala;
            alertDialogBuilder.setTitle("Data " + kategoriTerpilih);
            alertDialogBuilder
                    .setMessage(message)
                    .setIcon(R.drawable.file)
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(EditData.this, ViewData.class);
                            startActivity(intent);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public void backButton(View view) {
        onBackPressed();
    }
}