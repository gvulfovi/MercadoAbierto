package com.example.gabriel.mercadoabierto;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gabriel on 28/04/2017.
 */

public class ProductListAdapter extends BaseAdapter{

    private Context context;
    private List<Product> mListProducts;


    public ProductListAdapter(Context context, List<Product> listProducts) {
        this.context = context;
        this.mListProducts = listProducts;
    }

    public List<Product> getListProducts() {
        return mListProducts;
    }

    @Override
    public int getCount() {
        return mListProducts.size();
    }

    @Override
    public Product getItem(int position) {
        return mListProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Creo un inflator
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        //Inflar el detalle de celda (item)
        View viewProductsList = layoutInflater.inflate(R.layout.item_product, parent, false);

        //Obtener las views de la celda (item)
        ImageView imageProductPhoto = (ImageView) viewProductsList.findViewById(R.id.image_producto);
        TextView textProductName = (TextView) viewProductsList.findViewById(R.id.text_product_name);
        TextView textProductPrice = (TextView) viewProductsList.findViewById(R.id.text_product_price);

        //Buscar los datos a mostrar
        String productName = mListProducts.get(position).getProductName();
        String productPrice = "$" + mListProducts.get(position).getPrice().toString();
        Integer productImageId = mListProducts.get(position).getImageId();

        //Modificar cada view para mostrar los datos.
        imageProductPhoto.setImageResource(productImageId);
        textProductName.setText(productName);
        textProductPrice.setText(productPrice);

        return viewProductsList;
    }
}
