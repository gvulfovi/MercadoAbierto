package com.example.gabriel.mercadoabierto;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {

    private ProductListAdapter mProductListAdapter;
    private List<Product> mProductsList;
    private View mView;
    private OnItemSelectedListener onItemSelectedListener;

    // Con este metodo onAttach guardamos el contexto donde se encuentra el ListViewFragment
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try { // Se garantiza que se implemente la interfaz en el Activity que será invocado por
            // el Fragment
            this.onItemSelectedListener = (OnItemSelectedListener) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(context.toString() + " must implement OnItemSelectedListener");
        }
    }

    //Definicion de Interfaz Callback: interfaz creada para comunicar al MainActivity, que el producto
    // fue presionado en la list view del ListViewFragment
    public interface OnItemSelectedListener {

        public void onItemSelected(Product product, Integer position);
        public void onItemDeleted(Product product);
        public void onItemAdded(Product product);
    }

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       mView = inflater.inflate(R.layout.fragment_list_view, container, false);

        //Creacion de los datos en BD en Memoria
        ProductDAO productDAO = new ProductDAOInMemory();
        productDAO.cargaDeDatos();

        //Obtengo los datos de la BD en Memoria
        mProductsList = productDAO.getListProducts();

         // Creo el adapter y le envio los datos
        mProductListAdapter = new ProductListAdapter(this.getContext(), mProductsList);

        // Busco el List View
        ListView listView = (ListView) mView.findViewById(R.id.list_products);

        // Al list View le asigno un Adapter
        listView.setAdapter(mProductListAdapter);

        //Obtengo el 1er producto seleccionado de la lista para la primera vez
        Product product = mProductListAdapter.getItem(0);
        //Se notifica al MainActivity cual será el producto seleccionado la primera vez.
        //onItemSelectedListener.setSelectedProductItem(product);

        //Al list View (acccion de click corto) le asigno un Listener (Clase Anonima)
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override //Cuando se hace Click corto a una celda
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Obtengo el producto seleccionado de la lista
                Product product = mProductListAdapter.getItem(position);

                //Se notifica al MainActivity de la celda del ListView contenido en el Fragment fue Clickeado.
                onItemSelectedListener.onItemSelected(product, position);
            }
        });

        //Al list View (acccion de click largo) le asigno un Listener (Clase Anonima)
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override //Cuando se hace Click largo a una celda
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Product product = null;

                //Busqueda del Adapter.
                ProductListAdapter adapter = (ProductListAdapter) parent.getAdapter();

                //Obtengo la lista de Productos del Adapter
                List<Product> listProducts = adapter.getListProducts();

                //Remuevo el item
                listProducts.remove(position);

                //Notifico de la actualizacion de datos al adapter.
                adapter.notifyDataSetChanged();

                if (!listProducts.isEmpty()) {

                    //Obtengo el 1er producto seleccionado de la lista para la primera vez
                    product = mProductListAdapter.getItem(0);
                }

                //Se notifica al MainActivity de la celda del ListView que deberá mostrar por defecto.
                onItemSelectedListener.onItemDeleted(product);

                return true;
            }
        });

        //Busqueda del boton que adiciona nuevos productos
        Button buttonAdd = (Button) mView.findViewById(R.id.button_add);

        //Configurar el Boton "Add" producto
        buttonAdd.setOnClickListener (new View.OnClickListener() {

            @Override //Accciones al presionar el botton "Add"
            public void onClick(View v) {

                EditText editTextProductName = (EditText) mView.findViewById(R.id.edit_product_name);
                String textProductName = editTextProductName.getText().toString();

                if(textProductName.isEmpty()) {
                    Toast.makeText(getContext(), "Debe Ingresar un Nombre de Producto", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText editTextProductPrice = (EditText) mView.findViewById(R.id.edit_product_price);
                String textProductPrice = editTextProductPrice.getText().toString();

                if(textProductPrice.isEmpty()) {
                    Toast.makeText(getContext(), "Debe Ingresar un Precio", Toast.LENGTH_SHORT).show();
                    return;
                }
                Double productPrice = Double.parseDouble(textProductPrice);

                if(productPrice <= 0) {
                    Toast.makeText(getContext(), "Debe Ingresar un valor mayor que 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Creacion de un nuevo producto (Sin Imagen, Sin Descripcion y sin Anunciante
                Product product = new Product(textProductName,0,productPrice, "Sin Anunciante");
                product.setDescripcion("Sin descripcion");
                mProductsList.add(0, product);

                //Notificacion de cambios en el Adaptar.
                mProductListAdapter.notifyDataSetChanged();

                // Blanqueo los campos de ingreso de datos
                editTextProductName.setText("");
                editTextProductPrice.setText("");
                editTextProductName.requestFocus();

                //Se notifica al MainActivity de la celda del ListView contenido en el Fragment fue Clickeado.
                onItemSelectedListener.onItemAdded(product);

            }

        });

        return mView;
    }

}
