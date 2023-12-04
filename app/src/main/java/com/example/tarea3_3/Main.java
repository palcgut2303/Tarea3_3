package com.example.tarea3_3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

public class Main extends AppCompatActivity {

    TextView listadoCompra;
    Button btAgregar,btLimpiar,btSalir;
    ListaCompraDatabaseAdapter dbAdapter = null;

    ListView listado;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listadoCompra = findViewById(R.id.listado);
        btAgregar = findViewById(R.id.btAnadir);
        btLimpiar = findViewById(R.id.btLimpiar);
        btSalir = findViewById(R.id.btSalir);
        listado = findViewById(R.id.listado);

        rellenarLista();

        btLimpiar.setOnClickListener( view -> {
            dbAdapter.limpiarTabla();
            rellenarLista();

        });

        btSalir.setOnClickListener(view -> {
            dbAdapter.close();
            finishAffinity();

        });

        btAgregar.setOnClickListener(view -> {
            Intent intent = new Intent(this,AgregarProducto.class);
            ///lanzadorActivity.launch();
        });

    }



    private void rellenarLista() {
        @SuppressWarnings("deprecation")
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.producto_row,
                dbAdapter.obtenerTodosElementos(),
                new String[] { ListaCompraDatabaseAdapter.CLAVE_PRODUCTO, ListaCompraDatabaseAdapter.CLAVE_CANTIDAD },
                new int[] { R.id.tvProducto, R.id.tvCantidad },
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        listado.setAdapter(adapter);
    }
}
