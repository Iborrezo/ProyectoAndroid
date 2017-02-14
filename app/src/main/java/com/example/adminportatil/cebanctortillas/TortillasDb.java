package com.example.adminportatil.cebanctortillas;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TortillasDb extends SQLiteOpenHelper {

    public TortillasDb(Context contexto, String nombre,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }
        @Override
        public void onCreate (SQLiteDatabase db){

            String createProductos = "CREATE TABLE productos(idproducto INTEGER PRIMARY KEY, soluble INTEGER, descripcion TEXT, precio INTEGER)";
            String createClientes = "CREATE TABLE clientes(idcliente INTEGER PRIMARY KEY, nombre TEXT, direccion TEXT, telefono TEXT)";
            String createCabecera = "CREATE TABLE cabecera(idcabecera INTEGER PRIMARY KEY, cliente INTEGER, fecha_albaran DATE, FOREIGN KEY(cliente) REFERENCES clientes(idcliente))";
            String createLineas = "CREATE TABLE lineas(idlinea INTEGER PRIMARY KEY, cabecera INTEGER NOT NULL, producto INTEGER, cantidad INTEGER, FOREIGN KEY(cabecera) REFERENCES cabeceras(idcabecera), FOREIGN KEY (producto) REFERENCES productos(idproducto))";




            db.execSQL(createClientes);
            db.execSQL(createProductos);
            db.execSQL(createCabecera);
            db.execSQL(createLineas);

            for (int i=1;i<37;i++){
               String insertComidas = "INSERT INTO productos(soluble, descripcion, precio) VALUES(0, '"+obtenerDescripcionYPrecio(i)+")";
                db.execSQL(insertComidas);
            }
            db.execSQL("INSERT INTO productos(soluble, descripcion, precio) VALUES(1,'Cocacola',2)");
            db.execSQL("INSERT INTO productos(soluble, descripcion, precio) VALUES(1,'Kas Limon',1.75)");
            db.execSQL("INSERT INTO productos(soluble, descripcion, precio) VALUES(1,'Kas Naranja',1.75)");
            db.execSQL("INSERT INTO productos(soluble, descripcion, precio) VALUES(1,'Nestea',2.2)");
            db.execSQL("INSERT INTO productos(soluble, descripcion, precio) VALUES(1,'Cerveza',2.5)");
            db.execSQL("INSERT INTO productos(soluble, descripcion, precio) VALUES(1,'Agua',1)");
            db.execSQL("INSERT INTO clientes(nombre, direccion, telefono) VALUES('Igor', 'Amara', '123')");
        }

        @Override
        public void onUpgrade (SQLiteDatabase db,int oldVersion, int newVersion){


        }
    public String obtenerDescripcionYPrecio(int vuelta){
        String descripcion = "";
        double precio = 0;
        //Rellenar Primera posicion de Descripcion con el tipo de ingrediente principal
        if(vuelta==1 || vuelta == 2 || vuelta == 13 || vuelta == 14 || vuelta == 25 || vuelta == 26){
            descripcion = "Patata,";
            precio += 3;
        }else if (vuelta == 3 || vuelta == 4 || vuelta == 15 || vuelta == 16 || vuelta == 27 || vuelta == 28){
            descripcion= "Verduras,";
            precio += 2;
        }else if (vuelta == 5 || vuelta == 6 || vuelta == 17 || vuelta == 18 || vuelta == 29 || vuelta == 30){
            descripcion = "Bacalao,";
            precio += 2.5;
        }else if(vuelta == 7 || vuelta == 8 || vuelta == 19 || vuelta == 20 || vuelta == 31 || vuelta == 32){
            descripcion = "Jamón Ibérico,";
            precio += 4;
        }else if (vuelta == 9 || vuelta == 10 || vuelta == 21 || vuelta == 22 || vuelta == 33 || vuelta == 34){
            descripcion = "Queso Idiazabal,";
            precio += 3.5;
        }else if (vuelta == 11 || vuelta == 12 || vuelta == 23 || vuelta == 24 || vuelta == 35 || vuelta == 36){
            descripcion = "Hongos,";
            precio += 3;
        }
        //Rellenar Segunda posicion de Descripcion con el tamaño
        if (vuelta%2==0) {
            descripcion += "Familiar,";
            precio += 9;
        }else{
              descripcion += "Individual,";
            precio += 5;
            }

        //Rellenar Tercera posicion de Descripcion con el tipo de huevo

        if(vuelta<13){
            descripcion +="Granja";
            precio += 1;
        }else if (vuelta>12 && vuelta<25){
            descripcion +="Campero";
            precio += 2;
        }else{
            descripcion +="Ecológico";
            precio += 3;
        }

        descripcion += "',"+precio;

        return descripcion;

    }
    }

