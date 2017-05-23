package com.example.gabriel.mercadoabierto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Gabriel on 28/04/2017.
 */

public class ProductListAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private List<Product> mListProducts;


    public ProductListAdapter(Context context, List<Product> listProducts) {
        this.mContext = context;
        this.mListProducts = listProducts;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Creo un inflator
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        //Inflar el detalle de celda (item)
        View viewProductsList = layoutInflater.inflate(R.layout.item_product, parent, false);

        ProductViewHolder productViewHolder = new ProductViewHolder(viewProductsList);

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //Buscar los datos a mostrar
        Product product = mListProducts.get(position);

        ProductViewHolder productViewHolder = (ProductViewHolder) holder;
        productViewHolder.loadProducts(product);

    }

    @Override
    public int getItemCount() {
        return mListProducts.size();
    }

    public List<Product> getListProducts() {
        return mListProducts;
    }

    //Creacion del ViewHolder que va a mantener las referencias
    //de los elementos de la celda y sabrá como cargar los datos

    private class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageProductPhoto;
        private TextView textProductName;
        private TextView textProductPrice;

        // Constructor
        public ProductViewHolder(View itemView) {
            super(itemView);

            //Obtener las views de la celda (item)
            imageProductPhoto = (ImageView) itemView.findViewById(R.id.image_producto);
            textProductName = (TextView) itemView.findViewById(R.id.text_product_name);
            textProductPrice = (TextView) itemView.findViewById(R.id.text_product_price);
        }

        public void loadProducts(Product product){

            //Buscar los datos a mostrar
            String productName = product.getProductName();
            String productPrice = "$" + product.getPrice().toString();
            Integer productImageId = product.getImageId();

            //Modificar cada view para mostrar los datos.
            imageProductPhoto.setImageResource(productImageId);
            textProductName.setText(productName);
            textProductPrice.setText(productPrice);

        }
    }
}
