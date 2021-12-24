package com.UliRinduDebora.savings;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private final ArrayList idList;
    private final ArrayList kategoriList;
    private final ArrayList uangList;
    private final ArrayList deskripsiList;
    private final ArrayList tanggalList;
    private final ArrayList prioritasList;
    private final ArrayList sumberList;
    private Context context;

    RecyclerViewAdapter(ArrayList idList, ArrayList kategoriList, ArrayList uangList, ArrayList deskripsiList, ArrayList tanggalList, ArrayList prioritasList, ArrayList sumberList){
        this.idList = idList;
        this.kategoriList = kategoriList;
        this.uangList = uangList;
        this.deskripsiList = deskripsiList;
        this.tanggalList = tanggalList;
        this.prioritasList = prioritasList;
        this.sumberList = sumberList;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView Kategori;
        private final TextView Jumlah;
        private final TextView Tanggal;
        private final CardView Card;
        private ImageButton Edit, Delete;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            Card = itemView.findViewById(R.id.card);
            Kategori = itemView.findViewById(R.id.kategori);
            Jumlah = itemView.findViewById(R.id.jumlah);
            Tanggal = itemView.findViewById(R.id.tanggal);
            Edit = itemView.findViewById(R.id.edit);
            Delete = itemView.findViewById(R.id.delete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design, parent, false);
        return new ViewHolder(V);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final int Id = (Integer) idList.get(position);//Mengambil data (Id) sesuai dengan posisi yang telah ditentukan
        final String Kategori = (String) kategoriList.get(position);//Mengambil data (Kategori) sesuai dengan posisi yang telah ditentukan
        final int Uang = (Integer) uangList.get(position);//Mengambil data (Jumlah) sesuai dengan posisi yang telah ditentukan
        final String Deskripsi = (String) deskripsiList.get(position);//Mengambil data (Deskripsi) sesuai dengan posisi yang telah ditentukan
        final String Tanggal = (String) tanggalList.get(position);//Mengambil data (Tanggal) sesuai dengan posisi yang telah ditentukan
        final String Prioritas = (String) prioritasList.get(position);//Mengambil data (Skala Prioritas) sesuai dengan posisi yang telah ditentukan
        final String Sumber = (String) sumberList.get(position);//Mengambil data (Sumber) sesuai dengan posisi yang telah ditentukan
        holder.Kategori.setText(Kategori);
        holder.Jumlah.setText("Rp."+Uang);
        holder.Tanggal.setText(Tanggal);

        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(view.getContext(), Result.class);
                intent.putExtra("id", Id);
                intent.putExtra("kategori", Kategori);
                intent.putExtra("uang", Uang);
                intent.putExtra("deskripsi", Deskripsi);
                intent.putExtra("tanggal", Tanggal);
                intent.putExtra("prioritas", Prioritas);
                intent.putExtra("sumber", Sumber);
                context.startActivity(intent);
            }
        });

        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                String message ="Yakin menghapus data "+Kategori+"?";
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        context);
                alertDialogBuilder.setTitle("Hapus Data");
                alertDialogBuilder
                        .setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("Ya",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                DBHelper dbHelper = new DBHelper(context.getApplicationContext());
                                dbHelper.hapusTransaksi(Id);
                                Toast.makeText(context.getApplicationContext(), "Transaksi berhasil dihapus", Toast.LENGTH_SHORT).show();
                                dbHelper.close();
                                Intent intent = new Intent(view.getContext(), ViewData.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("Tidak",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(view.getContext(), EditData.class);
                intent.putExtra("id", Id);
                intent.putExtra("kategori", Kategori);
                intent.putExtra("uang", Uang);
                intent.putExtra("deskripsi", Deskripsi);
                intent.putExtra("tanggal", Tanggal);
                intent.putExtra("prioritas", Prioritas);
                intent.putExtra("sumber", Sumber);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return idList.size();
    }

}
