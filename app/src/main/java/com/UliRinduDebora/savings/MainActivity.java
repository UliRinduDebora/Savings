package com.UliRinduDebora.savings;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    int size = 0;
    RadioGroup kategori;
    String kategoriTerpilih = "", skala = "";
    EditText jumlah, deskripsi, tanggal;
    SeekBar seekBar;
    CheckBox gaji, bonus, freelance, investasi, konfirmasi;
    DatePickerDialog datePickerDialog;
    RelativeLayout prioritas, sumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        //Kategori
        kategori.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id){
                    case R.id.pemasukan:
                        kategoriTerpilih = "Pemasukan";
                        prioritas.setVisibility(View.GONE);
                        sumber.setVisibility(View.VISIBLE);
                        break;
                    case R.id.pengeluaran:
                        kategoriTerpilih = "Pengeluaran";
                        prioritas.setVisibility(View.VISIBLE);
                        sumber.setVisibility(View.GONE);
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
                datePickerDialog = new DatePickerDialog(MainActivity.this,
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
        String sumber = "";
        if (gaji.isChecked()) {
            sumber += "- Gaji \n";
        }
        if (bonus.isChecked()) {
            sumber +=  "- Bonus \n";
        }
        if (freelance.isChecked()) {
            sumber +=  "- Freelance \n";
        }
        if (investasi.isChecked()) {
            sumber += "- Hasil Investasi \n";
        }
        if (sumber.equals("")){
            sumber = " -";
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
            sgd.setSumber(sumber);
            boolean input;
            input = dbHelper.tambahData(sgd);
            if (input) {
                Toast.makeText(getApplicationContext(), "Transaksi berhasil disimpan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Kesalahan terjadi!", Toast.LENGTH_SHORT).show();
            }
            dbHelper.close();

            final DBHelper dh = new DBHelper(getApplicationContext());
            dh.dapatkanTransaksiTeratas();

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            String message = "";
            message+="Jumlah Uang: Rp." + sgd.getUang();
            message+="\nDeskripsi: " + sgd.getDeskripsi();
            message+="\nTanggal: " + sgd.getTanggal();
            if(sgd.getKategori().equals("Pemasukan"))
                message+="\nSumber Uang Masuk: " + sgd.getSumber();
            else
                message+="\nSkala Prioritas: " + sgd.getPrioritas();
            alertDialogBuilder.setTitle("Data " + sgd.getKategori());
            alertDialogBuilder
                    .setMessage(message)
                    .setIcon(R.drawable.file)
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(MainActivity.this, ViewData.class);
                            startActivity(intent);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            dh.close();
        }
    }

    public void backButton(View view) {
        onBackPressed();
    }
}