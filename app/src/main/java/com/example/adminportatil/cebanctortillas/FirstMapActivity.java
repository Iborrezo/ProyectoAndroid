package com.example.adminportatil.cebanctortillas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

public class FirstMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FirstMapFragment mFirstMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);

        mFirstMapFragment = FirstMapFragment.newInstance(k);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.map_container, mFirstMapFragment)
                .commit();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}

