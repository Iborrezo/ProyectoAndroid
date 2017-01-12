package com.example.adminportatil.cebanctortillas;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Pedido extends Activity {
    private Spinner spTamaño;
    private Spinner spTipo;
    private Spinner spHuevo;
    private Button btnAñadir;
    private Button btnSalir;
    private Button btnSiguiente;
    private Button btnCalcular;
    private EditText edtCantidad;
    private TextView txtTamaño;
    private TextView txtTipo;
    private TextView txtHuevo;
    private TextView txtCantidadTot;

    final int prTamaño[] = {5, 9};
    final double prTipo[] = {3, 2, 2.5, 4, 3.5, 3};
    final int prHuevo[] = {1, 2, 3};

    ArrayList<Producto> listaCompra = new ArrayList<Producto>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        Bundle extra = getIntent().getExtras();
        String nombre = extra.getString("nombre");
        String apellido = extra.getString("direccion");
        String telefono = extra.getString("telefono");
        final String infPers = nombre + "," + apellido + "," + telefono;

        spTamaño = (Spinner) findViewById(R.id.spTamaño);
        spTipo = (Spinner) findViewById(R.id.spTipo);
        spHuevo = (Spinner) findViewById(R.id.spHuevo);
        btnAñadir = (Button) findViewById(R.id.btnAñadir);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        edtCantidad = (EditText) findViewById(R.id.edtCantidad);
        txtTamaño = (TextView) findViewById(R.id.txtTamaño);
        txtTipo = (TextView) findViewById(R.id.txtTipo);
        txtHuevo = (TextView) findViewById(R.id.txtHuevo);
        txtCantidadTot = (TextView) findViewById(R.id.txtCantidadTot);
        String[] tamaño = {"Individual", "Familiar"};
        ArrayAdapter<String> adapTamaño = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tamaño);
        String[] tipo = {"Patata", "Verduras", "Bacalao", "Jamón Ibérico", "Queso Idiazabal", "Hongos"};
        ArrayAdapter<String> adapTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo);
        String[] huevo = {"Granja", "Campero", "Ecológico"};
        ArrayAdapter<String> adapHuevo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, huevo);


        spTamaño.setAdapter(adapTamaño);
        spTipo.setAdapter(adapTipo);
        spHuevo.setAdapter(adapHuevo);


        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Añadir();
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cantidad = Integer.parseInt(edtCantidad.getText().toString());
                ActualizarPrecio(cantidad);
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(infPers);
            }
        });


    }

    private void Añadir() {
        String tipo = spTipo.getSelectedItem().toString();
        String tamaño = spTamaño.getSelectedItem().toString();
        String huevo = spHuevo.getSelectedItem().toString();
        int cantidad = Integer.parseInt(edtCantidad.getText().toString());
        ActualizarPrecio(cantidad);
        double precio;
        String descripcion;
        precio = CalcularPrecio();
        descripcion = (tipo + "," + tamaño + "," + huevo);
        listaCompra.add(new Producto(precio, cantidad, descripcion));
        Toast toast1;
        toast1 = Toast.makeText(getApplicationContext(), "Se ha añadido " + cantidad + " tortilla/s de: " + tipo + " con huevos '" + huevo + "' de tamaño: " + tamaño + ".", Toast.LENGTH_SHORT);
        toast1.show();

    }

    private void ActualizarPrecio(int cantidad) {
        int precio1 = prTamaño[spTamaño.getSelectedItemPosition()];
        double precio2 = prTipo[spTipo.getSelectedItemPosition()];
        int precio3 = prHuevo[spHuevo.getSelectedItemPosition()];
        txtTamaño.setText(String.valueOf(precio1) + "€");
        txtTipo.setText(String.valueOf(precio2) + "€");
        txtHuevo.setText(String.valueOf(precio3) + "€");
        txtCantidadTot.setText(String.valueOf((precio1 + precio2 + precio3) * cantidad) + "€");

    }

    private Double CalcularPrecio() {
        int precio1 = prTamaño[spTamaño.getSelectedItemPosition()];
        double precio2 = prTipo[spTipo.getSelectedItemPosition()];
        int precio3 = prHuevo[spHuevo.getSelectedItemPosition()];
        return (precio1 + precio2 + precio3);

    }

    private void next(String infPers) {
        Intent i = new Intent(this, Bebidas.class);
        i.putExtra("ArrayProducto", listaCompra);
        i.putExtra("infPers", infPers);
        startActivity(i);


    }


}
