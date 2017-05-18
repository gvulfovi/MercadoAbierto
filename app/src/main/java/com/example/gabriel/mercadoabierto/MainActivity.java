package com.example.gabriel.mercadoabierto;


import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements ListViewFragment.OnItemSelectedListener {

    private FragmentTransaction mTransaction;
    private String mDeviceResolution = "normal";
    private Boolean mIsTablet = false;
    private Product mSelectedProductItem; // Item de Producto Selecionado de la listView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Determina el screen size (Small, Normal, Large o Extra Large)
        mDeviceResolution = getSizeScreen(this);
        Toast.makeText(this, mDeviceResolution + " Screen", Toast.LENGTH_LONG).show();
        // Se considera Tablet si el screen sice es Large o XLarge
        mIsTablet = (mDeviceResolution.equals("xlarge") || mDeviceResolution.equals("large"));

        //Creacion de los Fragments
        ListViewFragment listViewFragment = new ListViewFragment();
        DetailProductFragment detailProductFragment = new DetailProductFragment();

        //Peticion del Fragment Manager
        FragmentManager unFragmentManager = getSupportFragmentManager();

        //Peticion de inicio de una transaccion
        mTransaction = unFragmentManager.beginTransaction();

        if (mIsTablet) { // Tablet (Media pantalla cada fragment)

            //Agregado al contenedor el fragment ListView a media pantalla
            mTransaction.add(R.id.containerFragmentListViewHalfScreen, listViewFragment);
            //Agregado al contenedor el fragment Detail Product si lo permite la pantalla.
            mTransaction.add(R.id.containerFragmentDetailProduct, detailProductFragment);

        } else { // Handset (Pantalla Completa para cada fragment)

            //Agregado al contenedor el fragment ListView a pantalla completa.
            mTransaction.add(R.id.containerFragmentListViewAllScreen, listViewFragment);
        }

        //Confirmar la transaccion.
        mTransaction.commit();
    }

    // Getter del Product Item seleccionado de la lista de productos.
    public Product getSelectedProductItem() {
        return mSelectedProductItem;
    }

    // Setter del Product Item seleccionado de la lista de productos.
    public void setSelectedProductItem(Product product) {
        mSelectedProductItem = product;
    }


    // Cuando un producto el clickeado en la lista del ListViewFragment lanza el activity DetailProductActivity
    // enviando el Product dentro del Bundle

    @Override
    public void onItemSelected(Product product, Integer position) {
        // Este es esl callback method que el fragment ListView (Fragment A)
        // invoca cuando un item de la lista es seleccionado.

        //Peticion del Fragment Manager
        FragmentManager unFragmentManager = getSupportFragmentManager();

        //Busco el detailProductFragment (Version vieja)
        //DetailProductFragment detailProductFragment = (DetailProductFragment) unFragmentManager.findFragmentById(R.id.containerFragmentDetailProduct);
        //Se recrea nuevamente el detailProductFragment
        DetailProductFragment detailProductFragment = new DetailProductFragment();

        if (!mIsTablet) {
            // Si no es Tablet, estamos en presencia del formato handset, se inicia el DetailProductActivity (Activity B)
            // y se le pasa la information del item seleccionado por medio de un bundle.

            // Creacion de un Bundle
            Bundle unBundle = new Bundle();

            // Creacion de Key en el Bundle
            unBundle.putString(DetailProductFragment.BUNDLE_KEY_PRODUCT_NAME, product.getProductName());
            unBundle.putDouble(DetailProductFragment.BUNDLE_KEY_PRODUCT_PRICE, product.getPrice());
            unBundle.putInt(DetailProductFragment.BUNDLE_KEY_PRODUCT_IMAGE_ID, product.getImageId());
            unBundle.putString(DetailProductFragment.BUNDLE_KEY_PRODUCT_ADVERTIZER, product.getAdvertizer());
            unBundle.putString(DetailProductFragment.BUNDLE_KEY_PRODUCT_DESCRIPTION, product.getDescripcion());

            // Creacion del Intent
            Intent unIntent = new Intent(this, DetailProductActivity.class);

            // Carga del bundle
            unIntent.putExtras(unBundle);

            // Inicio del Intent
            startActivity(unIntent);

        } else {
            // Si es una Tablet se Actualiza el product item a mostrar y se "actualiza" el fragment

            //Actualizo el Product Item a mostar.
            this.setSelectedProductItem(product);

            //Peticion de inicio de una transaccion
            FragmentTransaction unaTransaccion = unFragmentManager.beginTransaction();

            //Reemplazo del fragment Detail Product.
            unaTransaccion.replace(R.id.containerFragmentDetailProduct, detailProductFragment);

            /* Version vieja
            if (detailProductFragment == null) {
                //Creacion de los Fragments
                detailProductFragment = new DetailProductFragment();
                //Agregado al contenedor el fragment Detail Product si lo permite la pantalla.
                unaTransaccion.add(R.id.containerFragmentDetailProduct, detailProductFragment);
            } else {
                // "Refresh del Fragment detailProductFragment"
                unaTransaccion.detach(detailProductFragment);
                unaTransaccion.attach(detailProductFragment);
            }
            */
            //Confirmar la transaccion.
            unaTransaccion.commit();

        }
    }

    @Override
    public void onItemDeleted(Product product) {

        if (mIsTablet) {
            // Acciones solo realizadas cuando se ejecuta en una Tablet.

            //Peticion del Fragment Manager
            FragmentManager unFragmentManager = getSupportFragmentManager();

            //Peticion de inicio de una transaccion
            FragmentTransaction unaTransaccion = unFragmentManager.beginTransaction();

            //Actualizo el Product Item a mostar.
            this.setSelectedProductItem(product);

            if (product == null) { //Cuando no quedan mas productos en la lista
                //Busco el detailProductFragment
                DetailProductFragment detailProductFragment = (DetailProductFragment) unFragmentManager.findFragmentById(R.id.containerFragmentDetailProduct);

                // Si no hay mas productos para mostrar remuevo el fragment detailProductFragment
                unaTransaccion.remove(detailProductFragment);
            } else {
                //Se recrea nuevamente el detailProductFragment
                DetailProductFragment detailProductFragment = new DetailProductFragment();

                // Si hay productos para mostrar actualizo el fragment
                unaTransaccion.replace(R.id.containerFragmentDetailProduct, detailProductFragment);
                //unaTransaccion.attach(detailProductFragment);
            }

            //Confirmar la transaccion.
            unaTransaccion.commit();

        }
    }

    @Override
    public void onItemAdded(Product product) {

        // Acciones solo realizadas cuando se ejecuta en una Tablet. Para Handset no se realizada nada.
        if (mIsTablet) {

            //Peticion del Fragment Manager
            FragmentManager unFragmentManager = getSupportFragmentManager();

            //Actualizo el Product Item a mostar.
            this.setSelectedProductItem(product);

            //Se recrea nuevamente el detailProductFragment
            DetailProductFragment detailProductFragment = new DetailProductFragment();

            //Peticion de inicio de una transaccion
            FragmentTransaction unaTransaccion = unFragmentManager.beginTransaction();

            //Reemplazo del fragment Detail Product.
            unaTransaccion.replace(R.id.containerFragmentDetailProduct, detailProductFragment);

            //Confirmar la transaccion.
            unaTransaccion.commit();
        }

    }

    private static String getSizeScreen(Context context) {

        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout = screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return "small";
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return "normal";
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return "large";
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return "xlarge";
            default:
                return "undefined";
        }
    }
}
