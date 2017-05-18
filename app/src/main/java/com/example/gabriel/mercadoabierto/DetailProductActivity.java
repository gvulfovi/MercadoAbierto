package com.example.gabriel.mercadoabierto;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        //Obtencion del Intent
        Intent unIntent = getIntent();

        //Obtencion del Bundle
        Bundle unBundle = unIntent.getExtras();

        //Creacion de un Fragment
        DetailProductFragment detailProductFragment = new DetailProductFragment();

        //Peticion del Fragment Manager
        FragmentManager unFragmentManager = getSupportFragmentManager();

        //Peticion de inicio de una transaccion
        FragmentTransaction unaTransaccion = unFragmentManager.beginTransaction();

        //Pasaje del Bundle al fragment
        detailProductFragment.setArguments(unBundle);

        //Agregado al contenedor del DetailProductActivity el fragment usando una transcacion.
        unaTransaccion.add(R.id.containerFragmentDetailProduct, detailProductFragment);

        //Confirmar la transaccion.
        unaTransaccion.commit();

    }
}
