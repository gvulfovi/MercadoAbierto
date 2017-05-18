package com.example.gabriel.mercadoabierto;

import java.util.List;

/**
 * Created by Gabriel on 01/05/2017.
 */

public interface ProductDAO {

    //public Product getProduct();
    public void addRecord(Product product);
    public List<Product> getListProducts();
    public void cargaDeDatos();

}
