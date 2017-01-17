package com.example.adminportatil.cebanctortillas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.MatrixCursor;
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

                String nombre = tratarInfPers();
                int lineas = listaCompra.size();
                Toast toast1;
                calcularPrecio(lineas);
                if(listaCompra.size()==0){
                   msbox ("Pedido NO tramitado","Disculpe "+nombre+". Se ha producido un Error, no tiene artículos en la cesta, intentelo de nuevo ");
                }else{

                    if (precioTotal >= 18) {
                        if (precioTotal > 32) {
                            msbox( "Pedido tramitado", "Felicidades " + nombre + " ha conseguiudo un bale para comer en Cebanc");
                        } else {
                            msbox("Pedido tramitado", "Felicidades " + nombre + " ha conseguiudo un peluche de regalo");
                        }
                    } else {
                        msbox("Pedido tramitado", "Se ha tramitado el pedido de" + nombre);
                    }
                }

            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener() {
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

}
