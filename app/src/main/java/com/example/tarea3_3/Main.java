package com.example.tarea3_3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import java.sql.SQLException;

public class Main extends AppCompatActivity {

    TextView listadoCompra;
    Button btAgregar,btLimpiar,btSalir;


    ListaCompraDatabaseAdapter dbAdapter;

    ListView listado;

    @SuppressLint({"WrongViewCast", "CutPasteId"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbAdapter = new ListaCompraDatabaseAdapter(this);
        try {
            dbAdapter.open();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        listado = findViewById(R.id.listado);
        listadoCompra = findViewById(R.id.tvLista);
        btAgregar = findViewById(R.id.btAnadir);
        btLimpiar = findViewById(R.id.btLimpiar);
        btSalir = findViewById(R.id.btSalir);


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
            lanzadorActivity.launch(intent);
        });

    }

    ActivityResultContract<Intent, ActivityResult> contrato = new ActivityResultContracts.StartActivityForResult();
    ActivityResultCallback<ActivityResult> respuesta = new ActivityResultCallback<ActivityResult>(){
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK) {
                //No hay códigos de actividad
                Intent intentDevuelto = o.getData();
                String nombreDevuelto = (String) intentDevuelto.getExtras().get("nombreProducto");
                String cantidadDevuelto = (String) intentDevuelto.getExtras().get("cantidadProducto");

                dbAdapter.crearElemento(nombreDevuelto,Integer.parseInt(cantidadDevuelto));
                dbAdapter.obtenerTodosElementos();


            }
        }
    };

    ActivityResultLauncher<Intent> lanzadorActivity = registerForActivityResult(contrato,respuesta);



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
