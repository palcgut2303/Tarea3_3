package com.example.tarea3_3;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.example.tarea3_3.ListaCompraDatabaseAdapter.CREAR_DATABASE;
import static com.example.tarea3_3.ListaCompraDatabaseAdapter.DATABASE_NOMBRE;
import static com.example.tarea3_3.ListaCompraDatabaseAdapter.DATABASE_TABLA;
import static com.example.tarea3_3.ListaCompraDatabaseAdapter.DATABASE_VERSION;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

 class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_DATABASE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("Actualizando BD", TAG);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLA);
        onCreate(db);
    }
}