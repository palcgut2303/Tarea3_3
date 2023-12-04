package com.example.tarea3_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.sql.SQLException;

public class ListaCompraDatabaseAdapter {

    // Definición de la base de datos y las tablas
    static final String DATABASE_NOMBRE = "dbCompra";
    static final String DATABASE_TABLA = "productos";
    static final int DATABASE_VERSION = 1;
    private static final String TAG = "ListaCompraDatabaseAdapter";
    // Constantes para definir los nombres de las columnas de la tabla productos
    public static final String CLAVE_PRODUCTO = "producto";
    public static final String CLAVE_CANTIDAD = "cantidad";
    public static final String CLAVE_ID = "_id";
    // Columnas para la proyección de las consultas
    private static final String[] COLUMNAS_CONSULTA = {CLAVE_ID, CLAVE_PRODUCTO, CLAVE_CANTIDAD};
    // Sentencia SQL para la creación de la base de datos
    static final String CREAR_DATABASE = "create table " + DATABASE_TABLA + " (" + CLAVE_ID +
            " integer primary key autoincrement, " + CLAVE_PRODUCTO + " text not null, " +
            CLAVE_CANTIDAD + " integer not null);";
    // ATRIBUTOS PRIVADOS
    private DatabaseHelper dbHelper;
    private SQLiteDatabase dbCompra;
    private final Context dbContext;



    //Constructor de la clase
    public ListaCompraDatabaseAdapter(Context ctx) {
        this.dbContext = ctx;
    }

    //Método para abrir la conexión a la base de datos en modo escritura
    public ListaCompraDatabaseAdapter open() throws SQLException {
        this.dbHelper = new DatabaseHelper(this.dbContext);
        this.dbCompra = this.dbHelper.getWritableDatabase();
        return this;
    }

    //Método para cerrar la conexión a la BD
    public void close() {
        dbHelper.close();
    }

    //Método para insertar un elemento en la BD
    public long crearElemento(String nombre, int cantidad) {
        ContentValues valoresProducto = new ContentValues();
        valoresProducto.put(CLAVE_PRODUCTO, nombre);
        valoresProducto.put(CLAVE_CANTIDAD, cantidad);
        return dbCompra.insert(DATABASE_TABLA, null, valoresProducto);
    }

    //Método para borrar un elemento en la BD
    public boolean borrarElemento(long rowId) {
        return dbCompra.delete(DATABASE_TABLA, CLAVE_ID + "=" + rowId, null) > 0;
    }

    //Método para borrar todos los elementos de la tabla
    public boolean limpiarTabla(){
        return dbCompra.delete(DATABASE_TABLA,null, null) > 0;
    }

    //Método para obtener todos los elementos de la tabla mediante un cursor
    public Cursor obtenerTodosElementos() {
        return dbCompra.query(DATABASE_TABLA, COLUMNAS_CONSULTA, null, null, null, null, null);
    }

    //Método para obtener un elemento a partir de su id
    public Cursor obtenerElemento(long rowId) throws SQLException {
        Cursor mCursor = dbCompra.query(true, DATABASE_TABLA, COLUMNAS_CONSULTA, CLAVE_ID + "=" +
                rowId, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //Método para actualizar un elemento de la tabla
    public boolean actualizarElemento(long rowId, String producto, int cantidad) {
        ContentValues args = new ContentValues();
        args.put(CLAVE_PRODUCTO, producto);
        args.put(CLAVE_CANTIDAD, cantidad);
        return this.dbCompra.update(DATABASE_TABLA, args, CLAVE_ID + "=" + rowId, null) > 0;
    }
}