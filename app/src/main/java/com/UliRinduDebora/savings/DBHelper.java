package com.UliRinduDebora.savings;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "savings.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE savings_table(id INTEGER PRIMARY KEY AUTOINCREMENT, kategori TEXT, uang INTEGER, deskripsi TEXT, tanggal TEXT, prioritas INTEGER, sumber TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS savings_table");
    }

    public boolean tambahData(SetterGetterData sgd) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kategori", sgd.getKategori());
        cv.put("uang", sgd.getUang());
        cv.put("deskripsi", sgd.getDeskripsi());
        cv.put("tanggal", sgd.getTanggal());
        cv.put("prioritas", sgd.getPrioritas());
        cv.put("sumber", sgd.getSumber());

        return db.insert("savings_table", null, cv) > 0;
    }

    public Cursor dapatkanSemuaTransaksi() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from " + "savings_table", null);
    }

    public Cursor dapatkanTransaksiTeratas() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("Select * from " + "savings_table" + " order by id desc limit 1", null);
    }

    public boolean perbaharuiTransaksi(SetterGetterData sgd, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kategori", sgd.getKategori());
        cv.put("uang", sgd.getUang());
        cv.put("deskripsi", sgd.getDeskripsi());
        cv.put("tanggal", sgd.getTanggal());
        cv.put("prioritas", sgd.getPrioritas());
        cv.put("sumber", sgd.getSumber());
        return db.update("savings_table", cv, "id" + "=" + id,
                null) > 0;
    }

    public void hapusTransaksi (int id) {
        SQLiteDatabase db = getReadableDatabase();
        db.delete("savings_table", "id" + "=" + id, null);
    }
}