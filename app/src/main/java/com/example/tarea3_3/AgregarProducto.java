package com.example.tarea3_3;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AgregarProducto extends AppCompatActivity {

    TextView tv_nombre,tv_cantidad;
    Button aceptar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tv_nombre = findViewById(R.id.tv_nombreProducto);
        tv_cantidad = findViewById(R.id.tv_Cantidad);
        aceptar = findViewById(R.id.btAceptar);

        aceptar.setOnClickListener(view -> {




        });


    }
}