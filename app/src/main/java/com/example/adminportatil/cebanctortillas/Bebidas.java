package com.example.adminportatil.cebanctortillas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Bebidas extends Activity {
    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private Spinner spBebida;
    private EditText edtCantidad;
    private Button btnAñadir;
    private Button btnSalir;
    private Button btnSiguiente;
    private TextView txtPrecio;
    ArrayList<Producto> listaCompra;
    private Toast toast1;

    final double prBebidas[] = {0, 2, 1.75, 1.75, 2.20, 2.50, 1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);

        Bundle extra = getIntent().getExtras();
        final String infPers = extra.getString("infPers");
        listaCompra = (ArrayList<Producto>) extra.getSerializable("ArrayProducto");

        spBebida = (Spinner) findViewById(R.id.spBebida);
        edtCantidad = (EditText) findViewById(R.id.edtCantidad2);
        btnAñadir = (Button) findViewById(R.id.btnAñadir2);
        btnSalir = (Button) findViewById(R.id.btnSalir2);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente3);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente3);
        txtPrecio = (TextView) findViewById(R.id.txtPrecio);
        edtCantidad.setText("0");

        String[] bebidas = {"Seleccione una Bebida", "Cocacola", "Kas Limon", "Kas Naranja", "Nestea", "Cerveza", "Agua"};
        ArrayAdapter<String> adapBebidas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bebidas);
        spBebida.setAdapter(adapBebidas);


        spBebida.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        txtPrecio.setText((prBebidas[position]) + "€ unidad / ");
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadir();
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(infPers);
            }
        });

    }

    private void añadir() {
        if(spBebida.getSelectedItemPosition()>0){
            if(edtCantidad.getText().toString().equals("") || Integer.parseInt(edtCantidad.getText().toString()) == 0){
                toast1 = Toast.makeText(getApplicationContext(), "La cantidad tiene que ser superior a 0", Toast.LENGTH_SHORT);
                toast1.show();
        }
             else{
                String descripcion;
                int cantidad;
                double precio;
                descripcion = spBebida.getSelectedItem().toString();
                precio = prBebidas[spBebida.getSelectedItemPosition()];
                cantidad = Integer.parseInt(edtCantidad.getText().toString());
                listaCompra.add(new Producto(precio, cantidad, descripcion));
                toast1 = Toast.makeText(getApplicationContext(), "Se ha añadido " + cantidad + " " + descripcion + "/s .", Toast.LENGTH_SHORT);
                toast1.show();
            }


        }else{
            toast1 = Toast.makeText(getApplicationContext(), "Seleccione un tipo de bebida", Toast.LENGTH_SHORT);
            toast1.show();

        }


    }
    private void next(String infPers){
        Intent i = new Intent(this, Resumen.class);
        i.putExtra("ArrayProducto", listaCompra);
        i.putExtra("infPers", infPers);
        startActivity(i);
    }
}
