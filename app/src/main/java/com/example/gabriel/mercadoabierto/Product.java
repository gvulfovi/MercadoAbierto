package com.example.gabriel.mercadoabierto;

/**
 * Created by Gabriel on 28/04/2017.
 */

public class Product {

    String mProductName;
    Integer mImageId;
    Double mPrice;
    String mAdvertizer;
    String mDescripcion;

    public Product(String productName, Integer imageId, Double price, String advertizer) {
        this.mProductName = productName;

        if(!imageId.equals(0)) {
            this.mImageId = imageId;
        } else {
            this.mImageId = R.drawable.image_desconocido;
        }

        this.mPrice = price;
        this.mAdvertizer = advertizer;
    }

    public String getProductName() {
        return mProductName;
    }

    public Integer getImageId() {
        return mImageId;
    }

    public Double getPrice() {
        return mPrice;
    }

    public String getAdvertizer() {
        return mAdvertizer;
    }

    public String getDescripcion() {
        return mDescripcion;
    }

    public void setImageId(Integer imageId) {
        mImageId = imageId;
    }

    public void setDescripcion(String descripcion) {
        this.mDescripcion = descripcion;
    }
}
