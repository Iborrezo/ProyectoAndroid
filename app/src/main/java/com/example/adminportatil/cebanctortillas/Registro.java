package com.example.adminportatil.cebanctortillas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registro extends AppCompatActivity {

    private EditText nombre;
    private EditText direccion;
    private EditText telefono;
    private Button salir;
    private Button siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nombre=(EditText) findViewById(R.id.edtNombre);
        direccion=(EditText) findViewById(R.id.edtDireccion);
        telefono=(EditText) findViewById(R.id.edtTelefono);
        salir=(Button) findViewById(R.id.btnSalir);
        siguiente=(Button) findViewById(R.id.btnSiguiente);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nombre.getText().toString().equals("")) {
                    nombre.setError("No ha introducido el nombre");
                }else if (direccion.getText().toString().equals("")) {
                    direccion.setError("No ha introducido la direccion");
                }else if (telefono.getText().toString().equals("")){
                    telefono.setError("No ha introducido la direccion");
                }

                recogerDatos(null);
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //Recogiendo los datos de la pestaña REGISTRO para lkuego poder imprimir
    public void recogerDatos(View view){
        Intent registro=new Intent(this, Pedido.class);
        registro.putExtra("nombre", nombre.getText().toString());
        registro.putExtra("direccion", direccion.getText().toString());
        registro.putExtra("telefono", telefono.getText().toString());
        startActivity(registro);

    }
}
