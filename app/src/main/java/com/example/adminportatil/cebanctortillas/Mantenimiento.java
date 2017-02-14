package com.example.adminportatil.cebanctortillas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by adminportatil on 14/02/2017.
 */

public class Mantenimiento extends Activity {
    private Button btnAñadir;
    private Button btnEliminar;
    private EditText edtAñadir;
    private EditText edtEliminar;
    private Toast toast1;
    private EditText edtPrecio;
    private CheckBox cbLiquido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento);


        edtAñadir = (EditText) findViewById(R.id.edtAñadir);
        edtEliminar = (EditText) findViewById(R.id.edtEliminar);
        btnAñadir = (Button) findViewById(R.id.btnAñadir10);
        btnEliminar = (Button) findViewById(R.id.btnEliminar10);
        edtPrecio = (EditText) findViewById(R.id.edtCantidadd);
        cbLiquido = (CheckBox) findViewById(R.id.cbLiquido);

        btnAñadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                añadir();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar();
            }
        });
    }

    public void msbox(String str, String str2) {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
        dlgAlert.setTitle(str);
        dlgAlert.setMessage(str2);
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    private void añadir() {
        TortillasDb basedatos = new TortillasDb(this, "TortillasDb", null, 1);
        SQLiteDatabase db = basedatos.getWritableDatabase();

        String producto;
        producto = edtAñadir.getText().toString();

        int soluble = 0;
        if (cbLiquido.isChecked()) {
            soluble = 1;
        }
        db.execSQL("INSERT INTO productos(soluble, descripcion, precio) VALUES(" + soluble + ", '" + producto + "', " + Double.parseDouble(edtPrecio.getText().toString()) + ")");
        msbox("Cliente añadido", "Cliente añadido correctamente");


    }
    private void eliminar(){
            TortillasDb basedatos = new TortillasDb(this, "TortillasDb", null, 1);
            SQLiteDatabase db = basedatos.getWritableDatabase();


            db.execSQL("DELETE FROM productos WHERE idproducto="+edtEliminar.getText().toString());




    }


    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


}
