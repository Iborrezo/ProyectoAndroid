package com.example.adminportatil.cebanctortillas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Registro extends AppCompatActivity {

    private EditText edtnombre;
    private EditText edtdireccion;
    private EditText edttelefono;
    private Button salir;
    private Button siguiente;
    private Button btnBuscarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);





        edtnombre =(EditText) findViewById(R.id.edtNombre);
        edtdireccion =(EditText) findViewById(R.id.edtDireccion);
        edttelefono =(EditText) findViewById(R.id.edtTelefono);
        salir=(Button) findViewById(R.id.btnSalir0);
        siguiente=(Button) findViewById(R.id.btnSiguiente);
        btnBuscarCliente=(Button)findViewById(R.id.btnBuscarCliente);
        edtnombre.requestFocus();

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtnombre.getText().toString().equals("")) {
                    edtnombre.setError("No ha introducido el edtnombre");
                }else if (edtdireccion.getText().toString().equals("")) {
                    edtdireccion.setError("No ha introducido la edtdireccion");
                }else if (edttelefono.getText().toString().equals("")) {
                    edttelefono.setError("No ha introducido el edttelefono");
                } else{
                        start();
                }
            }
        });

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();

            }
        });
        btnBuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarCliente();
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

    //Recogiendo los datos de la pestaña REGISTRO para lkuego poder imprimir
    public void recogerDatos(){




    }
    public void start(){
        TortillasDb basedatos = new TortillasDb(this, "TortillasDb", null, 1);
        SQLiteDatabase db = basedatos.getWritableDatabase();

        String[]args = new String []{edtnombre.getText().toString()};
        Cursor c = db.rawQuery("SELECT nombre, direccion, telefono FROM clientes WHERE nombre=?",args);
        if(c.moveToFirst()==false){
            db.execSQL("INSERT INTO clientes(nombre, direccion, telefono) VALUES('"+edtnombre.getText().toString()+"', '"+edtdireccion.getText().toString()+"', '"+edttelefono.getText().toString()+"')");
        }
        db.close();


        Intent registro=new Intent(this, Pedido.class);
        registro.putExtra("nombre", edtnombre.getText().toString());
        registro.putExtra("direccion", edtdireccion.getText().toString());
        registro.putExtra("telefono", edttelefono.getText().toString());
        startActivity(registro);
    }
    private void buscarCliente(){
        TortillasDb basedatos = new TortillasDb(this, "TortillasDb", null, 1);
        SQLiteDatabase db = basedatos.getWritableDatabase();

        String[]args = new String []{edtnombre.getText().toString()};
        Cursor c = db.rawQuery("SELECT nombre, direccion, telefono FROM clientes WHERE nombre=?",args);
        if (c.moveToFirst()){
            String nombre = c.getString(0);
            String direccion = c.getString(1);
            String telefono = c.getString(2);
            edtdireccion.setText(direccion);
            edttelefono.setText(telefono);
            db.close();

        }else{
            msbox("NO EXISTE", "El nombre del cliente introducido no se encuentra en la Base de Datos");

        }

    }
}
