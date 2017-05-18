package com.example.gabriel.mercadoabierto;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailProductFragment extends Fragment {

    public static final String BUNDLE_KEY_PRODUCT_NAME = "product_name";
    public static final String BUNDLE_KEY_PRODUCT_PRICE = "product_price";
    public static final String BUNDLE_KEY_PRODUCT_IMAGE_ID = "product_image_id";
    public static final String BUNDLE_KEY_PRODUCT_ADVERTIZER = "product_advertizer";
    public static final String BUNDLE_KEY_PRODUCT_DESCRIPTION = "product_description";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String textoProductName, textoProductPrice, textoProductAdvertizer, textoProductDescription;
        Integer imageId;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_product, container, false);

        //Obtencion del Bundle desde el Activity DetailProductActivity
        Bundle unBundle = getArguments();

        if(unBundle != null) {
            //Obtencion de los valores almacenados en el Bundle
            textoProductName = unBundle.getString(BUNDLE_KEY_PRODUCT_NAME);
            textoProductPrice = "$" + Double.toString(unBundle.getDouble(BUNDLE_KEY_PRODUCT_PRICE));
            imageId = unBundle.getInt(BUNDLE_KEY_PRODUCT_IMAGE_ID);
            textoProductAdvertizer = unBundle.getString(BUNDLE_KEY_PRODUCT_ADVERTIZER);
            textoProductDescription = unBundle.getString(BUNDLE_KEY_PRODUCT_DESCRIPTION);

        } else {
            //Busco el primer dato de la lista
            MainActivity mainActivity = (MainActivity) inflater.getContext();
            Product product = mainActivity.getSelectedProductItem();
            //Context context = (MainActivity) inflater.getContext();
            //Product product = mainActivity.getSelectedProductItem();

            textoProductName = product.getProductName();
            textoProductPrice = "$" + Double.toString(product.getPrice());
            imageId = product.getImageId();
            textoProductAdvertizer = product.getAdvertizer();
            textoProductDescription = product.getDescripcion();
        }

        // Muestra de todos los valores en lo Widgets.
        TextView textViewProductName = (TextView) view.findViewById(R.id.text_product_name_detail);
        textViewProductName.setText(textoProductName);

        TextView textViewProductPrice = (TextView) view.findViewById(R.id.text_product_price_detail);
        textViewProductPrice.setText(textoProductPrice);

        ImageView imageViewImageId = (ImageView) view.findViewById(R.id.image_producto_detail);
        imageViewImageId.setImageResource(imageId);

        TextView textViewProductAdvertizer = (TextView) view.findViewById(R.id.text_product_advertizer);
        textViewProductAdvertizer.setText("Anunciante: " + textoProductAdvertizer);

        TextView textViewProductDescription = (TextView) view.findViewById(R.id.text_product_description);
        textViewProductDescription.setText(textoProductDescription);

        return view;
    }

}
