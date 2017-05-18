package com.example.gabriel.mercadoabierto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gabriel on 01/05/2017.
 */

public class ProductDAOInMemory implements ProductDAO {

    private List<Product> listProduct;

    public ProductDAOInMemory() {
        this.listProduct = new ArrayList<>();

    }

    @Override
    public void addRecord(Product product) {
        listProduct.add(product);
    }

    @Override
    public List<Product> getListProducts() {
        return new ArrayList(listProduct); //Clone
    }

    @Override
    public void cargaDeDatos() {

        Product product = new Product("Computadora Asus", R.drawable.computadora_asus, 8000.0, "Carlos Garcia");
        product.setDescripcion("Computador Marca Asus el mejor local de venta de PCs de Argentina");
        this.addRecord(product);

        product = new Product("Camara Kodak", R.drawable.camara_kodak, 2000.0, "Juan Lopez");
        product.setDescripcion("La mejor Camara Kodak para sacar Fotos Digiles");
        this.addRecord(product);

        product = new Product("Cuchillo Usado", R.drawable.cuchillo_usado, 200.0, "Pablo Gonzalez");
        product.setDescripcion("Cuchillo marca Cortlin Usado, muy buen estado. Bien afilado");
        this.addRecord(product);

        product = new Product("Automovil Ford Fiesta", R.drawable.ford_fiesta, 125000.0, "Leonardo Sanchez");
        product.setDescripcion("Venta de Automovial Ford Fiesta Modelo 2016");
        this.addRecord(product);

    }
}
