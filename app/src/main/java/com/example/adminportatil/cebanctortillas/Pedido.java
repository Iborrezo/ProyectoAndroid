package com.example.adminportatil.cebanctortillas;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Pedido extends Activity {
    private Spinner spTamaño;
    private Spinner spTipo;
    private Spinner spHuevo;
    private Button btnAñadir;
    private Button btnSalir;
    private Button btnSiguiente;
    private EditText edtCantidad;
    private TextView txtCantidadTot;
    private Toast toast1;
    private boolean vuelta = false;
    private String infPers;
    String nombre = "";

    ArrayList<Producto> listaCompra = new ArrayList<Producto>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);
        String telefono;
        String apellido;

        Bundle extra = getIntent().getExtras();
        vuelta = extra.getBoolean("vuelta");

        if (vuelta == false) {

            nombre = extra.getString("nombre");
            apellido = extra.getString("direccion");
            telefono = extra.getString("telefono");
            infPers = nombre + "," + apellido + "," + telefono;

        } else {
            listaCompra = (ArrayList<Producto>) extra.getSerializable("ArrayProducto");
            infPers = extra.getString("infPers");

        }


        spTamaño = (Spinner) findViewById(R.id.spTamaño);
        spTipo = (Spinner) findViewById(R.id.spTipo);
        spHuevo = (Spinner) findViewById(R.id.spHuevo);
        btnAñadir = (Button) findViewById(R.id.btnAñadir);
        btnSalir = (Button) findViewById(R.id.btnSalir);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente);
        edtCantidad = (EditText) findViewById(R.id.edtCantidad);
        txtCantidadTot = (TextView) findViewById(R.id.txtCantidadTot);

        TortillasDb basedatos = new TortillasDb(this, "TortillasDb", null, 1);
        SQLiteDatabase db = basedatos.getWritableDatabase();
        String[] args = new String[]{"0"};
        Cursor c = db.rawQuery("SELECT DISTINCT descripcion FROM productos WHERE soluble=?", args);

        ArrayList<String> tamaño = new ArrayList<String>();
        ArrayList<String> tipo = new ArrayList<String>();
        ArrayList<String> huevo = new ArrayList<String>();
        tamaño.add("Seleccione un tamaño");
        tipo.add("Seleccione un ingrediente principal");
        huevo.add("Seleccione un tipo de huevo");
        if(c.moveToFirst()){
            do{
                String [] desc = c.getString(0).split(",");
                if(tamaño.contains(desc[1])){

                }else{
                    tamaño.add(desc[1]);
                }
                if(tipo.contains(desc[0])){

                }else{
                    tipo.add(desc[0]);
                }
                if(huevo.contains(desc[2])){

                }else{
                    huevo.add(desc[2]);
                }



            }while(c.moveToNext());
        }
        c.close();
        db.close();


        ArrayAdapter<String> adapTamaño = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tamaño);

         ArrayAdapter<String> adapTipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipo);
           ArrayAdapter<String> adapHuevo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, huevo);


        spTamaño.setAdapter(adapTamaño);
         spTipo.setAdapter(adapTipo);
            spHuevo.setAdapter(adapHuevo);


        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Añadir();

                }catch (Exception e){
                    msbox("Error", "El producto seleccionado ya no está disponible.");

                }

            }
        });


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(infPers);
            }
        });

        spTipo.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if (edtCantidad.getText().equals("")) {
                            edtCantidad.setText("1");
                            ActualizarPrecio(Integer.parseInt(edtCantidad.getText().toString()));
                        } else {
                            ActualizarPrecio(Integer.parseInt(edtCantidad.getText().toString()));

                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        spHuevo.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if (edtCantidad.getText().equals("")) {
                            edtCantidad.setText("1");
                            ActualizarPrecio(Integer.parseInt(edtCantidad.getText().toString()));
                        } else {
                            ActualizarPrecio(Integer.parseInt(edtCantidad.getText().toString()));

                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        spTamaño.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        if(edtCantidad.getText().equals("")){
                         edtCantidad.setText("1");
                            ActualizarPrecio(Integer.parseInt(edtCantidad.getText().toString()));
                        }else{
                            ActualizarPrecio(Integer.parseInt(edtCantidad.getText().toString()));

                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }
    public void msbox(String str,String str2)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle(str);
        dlgAlert.setMessage(str2);
        dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }


    private void Añadir() throws Exception{
        if (validarDatos()) {
            ActualizarPrecio(Integer.parseInt(edtCantidad.getText().toString()));
            String tipo = spTipo.getSelectedItem().toString();
            String tamaño = spTamaño.getSelectedItem().toString();
            String huevo = spHuevo.getSelectedItem().toString();
            int cantidad = Integer.parseInt(edtCantidad.getText().toString());
            String descripcion = (tipo + "," + tamaño + "," + huevo);
            Double precio = Double.parseDouble(txtCantidadTot.getText().toString());
            listaCompra.add(new Producto(precio, cantidad, descripcion));

            toast1 = Toast.makeText(getApplicationContext(), "Se ha añadido " + cantidad + " tortilla/s de: " + tipo + " con huevos '" + huevo + "' de tamaño: " + tamaño + ".", Toast.LENGTH_SHORT);
            toast1.show();

        }
    }

    private void ActualizarPrecio(int cantidad) {
//        int precio1 = prTamaño[spTamaño.getSelectedItemPosition()];
//        double precio2 = prTipo[spTipo.getSelectedItemPosition()];
//        int precio3 = prHuevo[spHuevo.getSelectedItemPosition()];
//        txtTamaño.setText(String.valueOf(precio1) + "€");
//        txtTipo.setText(String.valueOf(precio2) + "€");
//        txtHuevo.setText(String.valueOf(precio3) + "€");
//        txtCantidadTot.setText(String.valueOf((precio1 + precio2 + precio3) * cantidad) + "€");

        TortillasDb basedatos = new TortillasDb(this, "TortillasDb", null, 1);
        SQLiteDatabase db = basedatos.getWritableDatabase();


        String textoTamaño = spTamaño.getSelectedItem().toString();
        String textoTipo = spTipo.getSelectedItem().toString();
        String textoHuevo = spHuevo.getSelectedItem().toString();
        String cadena = textoTipo+","+textoTamaño+","+textoHuevo;

        String[] args = new String[]{cadena};
        Cursor test = db.rawQuery("SELECT precio FROM productos WHERE descripcion=?", args);
        if(test.moveToFirst()){
            Double precio = test.getDouble(0);
            txtCantidadTot.setText(precio.toString());
        }





    }


    private void next(String infPers) {
        Intent i = new Intent(this, Bebidas.class);
        i.putExtra("ArrayProducto", listaCompra);
        i.putExtra("infPers", infPers);
        startActivity(i);


    }

    private boolean validarDatos() {
        if (spTamaño.getSelectedItemPosition() > 0) {
            if (spHuevo.getSelectedItemPosition() > 0) {
                if (spTipo.getSelectedItemPosition() > 0) {
                    if (edtCantidad.getText().toString().equals("") || Integer.parseInt(edtCantidad.getText().toString()) == 0) {
                        toast1 = Toast.makeText(getApplicationContext(), "La cantidad tiene que ser superior a 0", Toast.LENGTH_SHORT);
                        toast1.show();
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    toast1 = Toast.makeText(getApplicationContext(), "Seleccione un ingrediente principal", Toast.LENGTH_SHORT);
                    toast1.show();
                    return false;
                }
            } else {
                toast1 = Toast.makeText(getApplicationContext(), "Seleccione un tipo de huevo", Toast.LENGTH_SHORT);
                toast1.show();
                return false;
            }
        } else {
            toast1 = Toast.makeText(getApplicationContext(), "Seleccione un tamaño", Toast.LENGTH_SHORT);
            toast1.show();
            return false;
        }
    }

}
