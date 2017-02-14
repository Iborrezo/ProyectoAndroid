package com.example.adminportatil.cebanctortillas;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.StringTokenizer;

public class Bebidas extends Activity {

    private Spinner spBebida;
    private EditText edtCantidad;
    private Button btnAñadir;
    private Button btnSalir;
    private Button btnSiguiente;
    private TextView txtPrecio;
    ArrayList<Producto> listaCompra;
    private Toast toast1;
    private String infPers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bebidas);

        Bundle extra = getIntent().getExtras();
        infPers = extra.getString("infPers");
        listaCompra = (ArrayList<Producto>) extra.getSerializable("ArrayProducto");

        spBebida = (Spinner) findViewById(R.id.spBebida);
        edtCantidad = (EditText) findViewById(R.id.edtCantidad2);
        btnAñadir = (Button) findViewById(R.id.btnAñadir2);
        btnSalir = (Button) findViewById(R.id.btnSalir2);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente3);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente3);
        txtPrecio = (TextView) findViewById(R.id.txtPrecio);
        edtCantidad.setText("1");

        ArrayList<String> lista = new ArrayList<String>();
        lista.add("Seleccione una bebdia");
        TortillasDb basedatos = new TortillasDb(this, "TortillasDb", null, 1);
        SQLiteDatabase db = basedatos.getWritableDatabase();
        String[] args = new String[]{"1"};
        Cursor c = db.rawQuery("SELECT descripcion FROM productos WHERE soluble=?", args);
        c.moveToFirst();
        do{
           lista.add(c.getString(0));
        }while(c.moveToNext());

        ArrayAdapter<String> adapBebidas = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista);
        spBebida.setAdapter(adapBebidas);


        spBebida.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                            actualizarPrecio();
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

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }


    private void añadir() {
        if (spBebida.getSelectedItemPosition() > 0) {
            if (edtCantidad.getText().toString().equals("") || Integer.parseInt(edtCantidad.getText().toString()) == 0) {

            } else {
                String descripcion;
                int cantidad;
                double precio;
                descripcion = spBebida.getSelectedItem().toString();
                precio = Double.parseDouble(txtPrecio.getText().toString());
                cantidad = Integer.parseInt(edtCantidad.getText().toString());
                listaCompra.add(new Producto(precio, cantidad, descripcion));
                toast1 = Toast.makeText(getApplicationContext(), "Se ha añadido " + cantidad + " " + descripcion + "/s .", Toast.LENGTH_SHORT);
                toast1.show();
            }


        } else {
            toast1 = Toast.makeText(getApplicationContext(), "Seleccione un tipo de bebida", Toast.LENGTH_SHORT);
            toast1.show();

        }


    }

    private void next(String infPers) {
        Intent i = new Intent(this, Resumen.class);
        i.putExtra("ArrayProducto", listaCompra);
        i.putExtra("infPers", infPers);
        startActivityForResult(i, 1);
    }

    public void onBackPressed() {
        boolean vuelta = true;
        Intent intent = new Intent(this, Pedido.class);
        intent.putExtra("ArrayProducto", listaCompra);
        intent.putExtra("infPers", infPers);
        intent.putExtra("vuelta", vuelta);
        startActivity(intent);

    }
    public void actualizarPrecio(){
        if(spBebida.getSelectedItemPosition()==0){
            txtPrecio.setText("0");
        }else {
            TortillasDb basedatos = new TortillasDb(this, "TortillasDb", null, 1);
            SQLiteDatabase db = basedatos.getWritableDatabase();
            String[] args = new String[]{spBebida.getSelectedItem().toString()};
            Cursor c = db.rawQuery("SELECT precio FROM productos WHERE descripcion=?", args);
            if (c.moveToFirst()) {
                txtPrecio.setText(String.valueOf(c.getDouble(0)));
            }
        }
    }
}
