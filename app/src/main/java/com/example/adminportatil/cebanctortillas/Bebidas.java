package com.example.adminportatil.cebanctortillas;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class Bebidas extends Activity{


    private Spinner spBebida;
    private EditText edtCantidad;
    private Button btnAñadir;
    private Button btnSalir;
    private Button btnSiguiente;
    ArrayList<Producto> listaCompra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);

        Bundle extra = getIntent().getExtras();
        String infPers = extra.getString("infPers");
        listaCompra = (ArrayList<Producto>)extra.getSerializable("ArrayProducto");




        spBebida =(Spinner) findViewById(R.id.spBebida);
        edtCantidad=(EditText)findViewById(R.id.edtCantidad2);
        btnAñadir=(Button) findViewById(R.id.btnAñadir2);
        btnSalir=(Button) findViewById(R.id.btnSalir2);
        btnSiguiente=(Button) findViewById(R.id.btnSiguiente3);



    }

}
