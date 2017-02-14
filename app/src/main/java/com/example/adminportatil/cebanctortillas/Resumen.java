package com.example.adminportatil.cebanctortillas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class Resumen extends Activity {

    private ListView lvResultadoDesc;
    private ListView lvlResultadoCant;
    private Spinner spModif;
    private Button btnMas;
    private Button btnMenos;
    private Button btnQuitar;
    private Button btnAceptar;
    private Button btnCancelar;
    private TextView txtResultadoo;
    ArrayList<Producto> listaCompra;
    private Button btnMostrarPedidos;
    String infPers;
    double precioTotal = 0;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        Bundle extra = getIntent().getExtras();
        infPers = extra.getString("infPers");
        lvResultadoDesc = (ListView) findViewById(R.id.lvResultadoDesc);
        lvlResultadoCant = (ListView) findViewById(R.id.lvlResultadoCant);
        spModif = (Spinner) findViewById(R.id.spModif);
        btnMas = (Button) findViewById(R.id.btnMas);
        btnMenos = (Button) findViewById(R.id.btnMenos);
        btnQuitar = (Button) findViewById(R.id.btnQuitar);
        btnAceptar = (Button) findViewById(R.id.btnAceptar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        txtResultadoo = (TextView) findViewById(R.id.txtResultadoo);
        btnMostrarPedidos = (Button) findViewById(R.id.btnMostrarPedidos);


        listaCompra = (ArrayList<Producto>) extra.getSerializable("ArrayProducto");


        ponerDatos();


        btnMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaCompra.size() > 0) {
                    Producto pr = listaCompra.get(spModif.getSelectedItemPosition());
                    pr.sumarCantidad();
                    ponerDatos();
                }

            }
        });

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaCompra.size() > 0) {
                    int cantidad;
                    Producto pr = listaCompra.get(spModif.getSelectedItemPosition());
                    cantidad = pr.getCantidad();
                    if (cantidad > 1) {
                        pr.restarCantidad();
                        ponerDatos();
                    } else if (cantidad == 1) {
                        listaCompra.remove(spModif.getSelectedItemPosition());
                        ponerDatos();
                    }
                }


            }
        });

        btnQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listaCompra.size() > 0) {
                    listaCompra.remove(spModif.getSelectedItemPosition());
                    ponerDatos();
                }

            }
        });


        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finalizarPedido();



            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
        btnMostrarPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarPedidos(tratarInfPers());
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
                finishAffinity();
            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }


    public void onBackPressed() {
        Intent intent = new Intent(this, Bebidas.class);
        intent.putExtra("ArrayProducto", listaCompra);
        intent.putExtra("infPers", infPers);
        startActivity(intent);

    }

    public void ponerDatos() {
        int lineas;
        lineas = listaCompra.size();
        String[] descripciones = new String[lineas];
        String[] cantidades = new String[lineas];
        calcularPrecio(lineas);
        for (int l = 0; l < lineas; l++) {
            Producto pr = listaCompra.get(l);
            descripciones[l] = pr.getDescripcion();
            cantidades[l] = String.valueOf(pr.getCantidad());

        }
        ArrayAdapter<String> adapModif = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, descripciones);
        ArrayAdapter<String> adapDescripciones = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, descripciones) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);

                TextView textview = (TextView) view.findViewById(android.R.id.text1);

                //Set your Font Size Here.
                textview.setTextSize(10);

                return view;
            }
        };

        ArrayAdapter<String> adapPrecios = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cantidades);
        lvResultadoDesc.setAdapter(adapDescripciones);
        lvlResultadoCant.setAdapter(adapPrecios);
        spModif.setAdapter(adapModif);

    }

    public void calcularPrecio(int lineas) {
        precioTotal = 0;
        String nombre = tratarInfPers();
        for (int l = 0; l < lineas; l++) {
            Producto pr = listaCompra.get(l);
            precioTotal = (precioTotal + pr.getPrecio()) * pr.getCantidad();
        }
        txtResultadoo.setText("El precio total del pedido de '" + nombre + "' tiene un valor de: " + String.valueOf(precioTotal) + "€");
    }

    public String tratarInfPers() {
        String[] datos = infPers.split(",");
        return datos[0];
    }
    public void finalizarPedido(){
        TortillasDb basedatos = new TortillasDb(this, "TortillasDb", null, 1);
        SQLiteDatabase db = basedatos.getWritableDatabase();

        String nombre = tratarInfPers();
        int lineas = listaCompra.size();
        Toast toast1;
        calcularPrecio(lineas);
        Calendar c = Calendar.getInstance();
        int date = c.get(Calendar.DATE);


        if(listaCompra.size()==0){
            msbox ("Pedido NO tramitado","Disculpe "+nombre+". Se ha producido un Error, no tiene artículos en la cesta, intentelo de nuevo ");
        }else{

            if (precioTotal >= 18) {
                if (precioTotal > 32) {
                    db.execSQL("INSERT INTO cabecera(cliente, fecha_albaran) VALUES ('"+nombre+"',"+date+")");
                    for(int i=0;i<listaCompra.size();i++){
                        int idcabecera=0;
                        int prod=0;
                        int cantidad=0;
                        Producto pr;
                        pr = listaCompra.get(i);
                        String[]args2 = new String []{String.valueOf(date)};
                        Cursor cur = db.rawQuery("SELECT idcabecera FROM cabecera WHERE fecha_albaran=?", args2);
                        if(cur.moveToFirst()){
                            idcabecera=cur.getInt(0);
                        }

                        String [] args = new String []{pr.getDescripcion()};
                        cantidad = pr.getCantidad();
                        Cursor miCursor = db.rawQuery("SELECT idproducto FROM productos WHERE descripcion=?", args);
                        if (miCursor.moveToFirst()){
                            prod = miCursor.getInt(0);
                        }
                        db.execSQL("INSERT INTO lineas(cabecera, producto, cantidad) VALUES('"+idcabecera+"',"+prod+","+cantidad+")");
                    }
                    msbox( "Pedido tramitado", "Felicidades " + nombre + " ha conseguiudo un bale para comer en Cebanc");



                } else {
                    db.execSQL("INSERT INTO cabecera(cliente, fecha_albaran) VALUES ('"+nombre+"',"+date+")");
                    for(int i=0;i<listaCompra.size();i++){
                        int idcabecera=0;
                        int prod=0;
                        int cantidad=0;
                        Producto pr;
                        pr = listaCompra.get(i);
                        String[]args2 = new String []{String.valueOf(date)};
                        Cursor cur = db.rawQuery("SELECT idcabecera FROM cabecera WHERE fecha_albaran=?", args2);
                        if(cur.moveToFirst()){
                            idcabecera=cur.getInt(0);
                        }

                        String [] args = new String []{pr.getDescripcion()};
                        cantidad = pr.getCantidad();
                        Cursor miCursor = db.rawQuery("SELECT idproducto FROM productos WHERE descripcion=?", args);
                        if (miCursor.moveToFirst()){
                            prod = miCursor.getInt(0);
                        }
                        db.execSQL("INSERT INTO lineas(cabecera, producto, cantidad) VALUES('"+idcabecera+"',"+prod+","+cantidad+")");
                    }
                    msbox("Pedido tramitado", "Felicidades " + nombre + " ha conseguiudo un peluche de regalo");
                }
            } else {
                db.execSQL("INSERT INTO cabecera(cliente, fecha_albaran) VALUES ('"+nombre+"',"+date+")");
                for(int i=0;i<listaCompra.size();i++){
                    int idcabecera=0;
                    int prod=0;
                    int cantidad=0;
                    Producto pr;
                    pr = listaCompra.get(i);
                    String[]args2 = new String []{String.valueOf(date)};
                    Cursor cur = db.rawQuery("SELECT idcabecera FROM cabecera WHERE fecha_albaran=?", args2);
                    if(cur.moveToFirst()){
                        idcabecera=cur.getInt(0);
                    }

                    String [] args = new String []{pr.getDescripcion()};
                    cantidad = pr.getCantidad();
                    Cursor miCursor = db.rawQuery("SELECT idproducto FROM productos WHERE descripcion=?", args);
                    if (miCursor.moveToFirst()){
                        prod = miCursor.getInt(0);
                    }
                    db.execSQL("INSERT INTO lineas(cabecera, producto, cantidad) VALUES('"+idcabecera+"',"+prod+","+cantidad+")");
                }
                msbox("Pedido tramitado", "Se ha tramitado el pedido de" + nombre);
            }
        }
    }
    private void mostrarPedidos(String nombre){
        int idCabecera = 0;
        double precioTot = 0;
        boolean contiene = false;
        String cadena = "Pedidos de "+nombre+":\n";
        TortillasDb basedatos = new TortillasDb(this, "TortillasDb", null, 1);
        SQLiteDatabase db = basedatos.getWritableDatabase();
        String [] args0 = new String []{nombre};
        Cursor c = db.rawQuery("SELECT idcabecera FROM cabecera WHERE cliente='?'", args0);
        if(c.moveToFirst()){
            idCabecera = c.getInt(0);
            contiene = true;
        }
        if (contiene == true){
            String [] args = new String []{String.valueOf(idCabecera)};
            c = db.rawQuery("SELECT lineas.cabecera, productos.precio, lineas.cantidad FROM productos, lineas WHERE productos.idproducto=lineas.producto AND cabecera=?", args);
            if(c.moveToFirst()){
                do{

                    int cabecera = c.getInt(0);
                    Double precio = c.getDouble(1);
                    int cantidad = c.getInt(2);

                    if (idCabecera==cabecera){
                        precioTot = precio*cantidad;
                    }else{
                        cadena = "Pedido: "+idCabecera+" tiene un total de "+precioTot+"€ \n";
                    }


                }while(c.moveToNext());


            }
            msbox("Detalles",cadena);

        }

    }



}
