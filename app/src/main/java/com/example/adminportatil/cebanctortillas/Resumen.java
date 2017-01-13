package com.example.adminportatil.cebanctortillas;

import android.app.Activity;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Resumen extends Activity {

    private ListView lvResultadoDesc;
    private ListView lvlResultadoCant;
    ArrayList<Producto> listaCompra;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen);
        Bundle extra = getIntent().getExtras();
        final String infPers = extra.getString("infPers");
        lvResultadoDesc = (ListView)findViewById(R.id.lvResultadoDesc);
        lvlResultadoCant = (ListView)findViewById(R.id.lvlResultadoCant);


        listaCompra = (ArrayList<Producto>) extra.getSerializable("ArrayProducto");
        int lineas;
        lineas =listaCompra.size();
        String [] descripciones = new String[lineas];
        String [] cantidades = new String [lineas];


        for(int l=0;l<lineas;l++){
            Producto pr = listaCompra.get(l);
            descripciones[l]= pr.getDescripcion();
            cantidades[l]= String.valueOf(pr.getCantidad());

        }


       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.rowlayout, R.id.restaurantname, values);
        ArrayAdapter<String> adapDescripciones = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, descripciones);
        ArrayAdapter<String> adapPrecios = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cantidades);

        lvResultadoDesc.setAdapter(adapDescripciones);
        lvlResultadoCant.setAdapter(adapPrecios);
    }

}
