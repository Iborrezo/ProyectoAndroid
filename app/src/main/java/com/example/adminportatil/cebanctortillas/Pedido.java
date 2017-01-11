package com.example.adminportatil.cebanctortillas;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class Pedido extends Activity{
    private Spinner spTamaño;
    private Spinner spTipo;
    private Spinner spHuevo;
    private Button btnAñadir;
    private Button btnSalir;
    private Button btnSiguiente;
    private EditText edtCantidad;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        spTamaño = (Spinner)findViewById(R.id.spTamaño);
        spTipo = (Spinner)findViewById(R.id.spTipo);
        spHuevo = (Spinner)findViewById(R.id.spHuevo);
        btnAñadir = (Button)findViewById(R.id.btnAñadir);
        btnSalir = (Button)findViewById(R.id.btnSalir);
        btnSiguiente = (Button)findViewById(R.id.btnSiguiente);
        edtCantidad = (EditText)findViewById(R.id.edtCantidad);

        String [] tamaño ={"Individual", "Familiar"};
        ArrayAdapter<String>adapTamaño= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, tamaño);
        String [] tipo ={"Patata", "Verduras", "Bacalao", "Jamón Ibérico", "Queso Idiazabal", "Hongos"};
        ArrayAdapter<String>adapTipo= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, tipo);
        String [] huevo ={"Granja", "Campero", "Ecológico"};
        ArrayAdapter<String>adapHuevo= new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, huevo);

        spTamaño.setAdapter(adapTamaño);
        spTipo.setAdapter(adapTipo);
        spHuevo.setAdapter(adapHuevo);
        ArrayList<Producto> listaCompra = new ArrayList<Producto>();

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Añadir();
            }
        });

    }
    private void Añadir(){
        String Tipo =  spTipo.getSelectedItem().toString();

    }



}
