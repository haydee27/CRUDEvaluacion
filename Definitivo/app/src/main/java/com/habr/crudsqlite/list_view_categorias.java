package com.habr.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.io.Serializable;

public class list_view_categorias extends AppCompatActivity {

    private ListView listView1;
    private ArrayAdapter adaptador;


    private ConexionSQLite conexion = new ConexionSQLite(this);

    String[] version = {"Aestro", "Blender", "CupCake", "Donut", "Eclair", "Froyo", "GingerBread", "HoneyComb", "IceCream Sandwich",
            "Jelly Bean", "KitKat", "Lolipop", "Marshmallow", "Nought", "Oreo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_categorias);


        listView1 = (ListView)findViewById(R.id.listView1);

        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, conexion.consultaListaCategorias1());
        listView1.setAdapter(adaptador);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String informacion;
                informacion = "Código: " + conexion.consultaListaArticulos().get(position).getCodigo() + "\n";
                informacion += "Descripción: " + conexion.consultaListaArticulos().get(position).getDescripcion() + "\n";
                informacion += "Precio: " + conexion.consultaListaArticulos().get(position).getPrecio() + "\n";
                informacion += "Idcategoria: " + conexion.consultaListaArticulos().get(position).getIdcategoria() + "\n";
                informacion += "Nombre Categoria: " + conexion.consultalistacategoria().get(position).getNombrecategoria()+"\n";
                informacion +="Estado categoria: " + conexion.consultalistacategoria().get(position).getEstadocategoria()+"\n";

            }
        });
    }
}