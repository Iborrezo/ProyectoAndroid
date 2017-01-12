package com.example.adminportatil.cebanctortillas;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class FirstMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirstMapFragment mFirstMapFragment;
    private Button inicio;
    private Button llamar;
    //test
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        inicio = (Button) findViewById(R.id.btnInicio);
        llamar = (Button) findViewById(R.id.btnLlamar);


        mFirstMapFragment = FirstMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map, mFirstMapFragment)
                .commit();

        // Registrar escucha onMapReadyCallback
        mFirstMapFragment.getMapAsync(this);

        //Lanzar a la 2 actividad
        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarInicio(null);
            }
        });

        //Realizar la llamada
        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realizarLlamada(null);
            }
        });
    }


    //El codigo para generar y especificar el lugar del mapa
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng cebanc = new LatLng(43.30469411639206, -2.0168709754943848);
        googleMap.addMarker(new MarkerOptions()
                .position(cebanc)
                .title("Tortillas Cebanc"));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(cebanc)
                .zoom(13)
                .build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void lanzarInicio(View view) {
        Intent inicio = new Intent(this, Registro.class);
        startActivity(inicio);
    }

    //La llamada
    public void realizarLlamada(View view) {
        Intent llamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:+681115462"));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(llamada);
    }
}




