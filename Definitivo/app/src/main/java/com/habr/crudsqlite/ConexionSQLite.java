package com.habr.crudsqlite;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import java.util.ArrayList;

public class ConexionSQLite extends SQLiteOpenHelper {

    boolean estadoDelete = true;

    ArrayList<String> listaArticulos;
    ArrayList<String> listaCategorias;
    ArrayList<String> listaregistros;


    ArrayList<Dto> articulosList;
    ArrayList<Dto> categoriaList;
    ArrayList<Dto> registrosList;

    public ConexionSQLite(Context context) {
        super(context, "admonistra.bd", null, 1);
    }

    @Override
      public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tb_productos(codigo INTEGER NOT NULL PRIMARY KEY, descripcion VARCHAR(50) NOT NULL, precio real NOT NULL, idcategoria integer(11) NOT NULL, FOREIGN KEY(idcategoria) REFERENCES tb_categorias (idcategoria))");
        db.execSQL("CREATE TABLE tb_categorias (idcategoria INTEGER(11) NOT NULL PRIMARY KEY, nombrecategoria VARCHAR(50) NOT NULL, estadocategoria INTEGER(11) NOT NULL, fecharegistro datetime NOT NULL)");

        db.execSQL("INSERT INTO tb_productos VALUES(4, 'procesador', 200, 1), (5,'cpu', 150, 3)");
        db.execSQL("INSERT INTO tb_categorias VALUES (1, 'DELL', 1,datetime('now', 'localtime')), (2, 'HP', 1, datetime('now', 'localtime')), (3, 'asus', 2, datetime('now', 'localtime')), (4, 'Lenovo', 1, datetime('now','localtime'))");

      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          db.execSQL("drop table if exists tb_productos");
          db.execSQL("drop table if exists tb_categorias");
          onCreate(db);

      }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-Mm-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    public SQLiteDatabase db() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db;
    }
    public boolean InsertarTradicional(Dto datos) {
        boolean estado = true;
        int resultado;

        try {
            int codigo = datos.getCodigo();
            String descripcion = datos.getDescripcion();
            double precio = datos.getPrecio();
            int idcategoria = datos.getIdcategoria();

            Cursor fila = db().rawQuery("Select codigo from tb_productos  Where codigo ='" + codigo + "'", null);
            if (fila.moveToFirst() == true) {
                estado = false;

            } else {
                String SQL = "Insert Into tb_productos \n" +
                        "(codigo, descripcion, precio,idcategoria) \n" +
                        "Values \n " +
                        "('" + String.valueOf(codigo) + "', '" + descripcion + "', '" + String.valueOf(precio) + "','" + String.valueOf(idcategoria) + "');";

                db().execSQL(SQL);
                db().close();

                estado = true;
            }
        } catch (Exception e) {
            estado = false;
            Log.e("error", e.toString());
        }
        return estado;
    }

    public boolean insertardatos(Dto datos) {
        boolean estado = true;
        int resultado;
        ContentValues registro = new ContentValues();
        try {
            registro.put("codigo", datos.getCodigo());
            registro.put("descripcion", datos.getDescripcion());
            registro.put("precio", datos.getPrecio());
            registro.put("idcategoria", datos.getIdcategoria());
            Cursor fila = db().rawQuery("select codigo from tb_productos where codigo ='"
                    + datos.getCodigo() + "'", null);
            if (fila.moveToFirst() == true) {
                estado = false;
            } else {
                resultado = (int) db().insert("tb_productos", null, registro);
                if (resultado > 0) estado = true;
                else estado = false;
            }
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean InsertRegister(Dto datos) {
        boolean estado = true;
        int resultado;
        try {
            int codigo = datos.getCodigo();
            String descripcion = datos.getDescripcion();
            double precio = datos.getPrecio();
            int id = datos.getIdcategoria();

            Cursor fila = db().rawQuery("select codigo from tb_productos where codigo='"
                    + datos.getCodigo() + "'", null);
            if (fila.moveToFirst() == true) {
                estado = false;
            } else {
                String SQL = "INSERT INTO tb_productos \n" +
                        "(codigo,descripcion,precio)\n" +
                        "VALUES \n" +
                        "(?,?,?);";

                db().execSQL(SQL, new String[]{
                        String.valueOf(codigo), descripcion, String.valueOf(precio), String.valueOf(id)
                });
                estado = true;
            }
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean consultaCodigo(Dto datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int codigo = datos.getCodigo();

            Cursor fila = db.rawQuery("select codigo, descripcion, precio, idcategoria from tb_productos + where codigo=" + codigo, null);
            if (fila.moveToFirst()) {
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                datos.setIdcategoria(Integer.parseInt(fila.getString(3)));

                estado = true;
            } else {
                estado = false;
            }
            db.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean consultaArticulos(Dto datos) {
        boolean estado = true;
        int resultado;

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String[] parametro = {String.valueOf(datos.getCodigo())};
            String[] campos = {"codigo", "descripcion", "precio", "idcategoria"};
            Cursor fila = db.query("tb_productos", campos, "codigo=?", parametro, null, null, null);
            if (fila.moveToFirst()) {
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                datos.setIdcategoria(Integer.parseInt(fila.getString(3)));
                estado = true;
            } else {
                estado = false;
            }
            fila.close();
            db.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean consultarDescripcion(Dto datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String descripcion = datos.getDescripcion();
            Cursor fila = db.rawQuery("select codigo, descripcion, precio, idcategoria from tb_productos where descripcion ='" + descripcion + "'", null);
            if (fila.moveToFirst()) {
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                datos.setIdcategoria(Integer.parseInt(fila.getString(3)));

                estado = true;
            } else {
                estado = false;
            }
            db.close();
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public boolean bajaCodigo(final Context context, final Dto datos) {

        estadoDelete = true;
        try {

            int codigo = datos.getCodigo();
            Cursor fila = db().rawQuery("select * from tb_productos where codigo=" + codigo, null);
            if (fila.moveToFirst()) {
                datos.setCodigo(Integer.parseInt(fila.getString(0)));
                datos.setDescripcion(fila.getString(1));
                datos.setPrecio(Double.parseDouble(fila.getString(2)));
                datos.setIdcategoria(Integer.parseInt(fila.getString(3)));

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setIcon(R.drawable.ic_delete);
                builder.setTitle("Warning");
                builder.setMessage("¿Esta seguro de borrar el registro? \n Código:" +
                        datos.getCodigo() + "\nDescripción: " + datos.getDescripcion() + "\nidcategoria: " + datos.getIdcategoria());
                builder.setCancelable(false);
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int codigo = datos.getCodigo();
                        int cant = db().delete("tb_productos", "codigo=" + codigo, null);

                        if (cant > 0) {
                            estadoDelete = true;
                            Toast.makeText(context, "Registro eliminado satisfactoriamente.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            estadoDelete = false;
                        }
                        db().close();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                ;
            } else {
                Toast.makeText(context, "No hay resultados encontrados para la busqueda" +
                        "especifica.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            estadoDelete = false;
            Log.e("Error.", e.toString());
        }
        return estadoDelete;
    }

    public boolean modificar(Dto datos) {
        boolean estado = true;
        int resultado;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            int codigo = datos.getCodigo();
            String descripcion = datos.getDescripcion();
            double precio = datos.getPrecio();
            int idcategoria = datos.getIdcategoria();

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("descripcion", descripcion);
            registro.put("precio", precio);
            registro.put("idcategoria", idcategoria);

            int cant = (int) db.update("tb_productos", registro, "codigo=" + codigo, null);

            db.close();
            if (cant > 0) estado = true;
            else estado = false;
        } catch (Exception e) {
            estado = false;
            Log.e("error.", e.toString());
        }
        return estado;
    }

    public ArrayList<Dto> consultaListaArticulos() {
        boolean estado = false;

        SQLiteDatabase db = this.getWritableDatabase();

        Dto articulos = null;

        articulosList = new ArrayList<Dto>();

        try {
            Cursor fila = db.rawQuery("select * from tb_productos", null);
            while (fila.moveToNext()) {
                articulos = new Dto();
                articulos.setCodigo(fila.getInt(0));
                articulos.setDescripcion(fila.getString(1));
                articulos.setPrecio(fila.getDouble(2));
                articulos.setIdcategoria(fila.getInt(3));

                articulosList.add(articulos);

                Log.i("codigo", String.valueOf(articulos.getCodigo()));
                Log.i("descripcion", articulos.getDescripcion().toString());
                Log.i("precio", String.valueOf(articulos.getPrecio()));
                Log.i("idcategoria", String.valueOf(articulos.getIdcategoria()));

            }
            obtenerListaArticulos();
        } catch (Exception e) {

        }
        return articulosList;
    }


    public ArrayList<String> obtenerListaArticulos() {
        listaArticulos = new ArrayList<String>();
        listaArticulos.add("Seleccione");

        for (int i = 0; i < articulosList.size(); i++) {
            listaArticulos.add(articulosList.get(i).getCodigo() + "~"
                    + articulosList.get(i).getDescripcion() + "~"+
                    articulosList.get(i).getPrecio()+ " " + articulosList.get(i).getIdcategoria());
        }
        return listaArticulos;
    }

    public ArrayList<Dto> registros() {
        boolean estado = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Dto registros = null;

        registrosList = new ArrayList<Dto>();

        try {
            Cursor fila = db.rawQuery("SELECT * FROM tb_productos INNER JOIN tb_categorias ON tb_productos.codigo = tb_categorias.idcategoria;", null);
              while (fila.moveToNext()) {
                registros = new Dto();
               registros.setCodigo(fila.getInt(0));
                registros.setDescripcion(fila.getString(1));
                registros.setPrecio(fila.getDouble(2));
                registros.setIdcategoria(fila.getInt(3));
                registros.setNombrecategoria(fila.getString(4));
                registros.setEstadocategoria(fila.getInt(5));
                  registrosList.add(registros);

                Log.i("codigo", String.valueOf(registros.getCodigo()));
                Log.i("descripcion", registros.getDescripcion().toString());
                Log.i("precio", String.valueOf(registros.getPrecio()));
               Log.i("idcategoria", String.valueOf(registros.getIdcategoria()));
                Log.i("nombrecategoria", registros.getNombrecategoria().toString());
                Log.i("estadocategoria", String.valueOf(registros.getEstadocategoria()));
            }
            obtenerListaregistros();
        } catch (Exception e) {

       }
        return registrosList;
    }

    public ArrayList<String> registros1() {
        boolean estado = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Dto registros = null;
        registrosList = new ArrayList<Dto>();
        try {
            Cursor fila = db.rawQuery("SELECT * FROM tb_productos INNER JOIN tb_categorias ON tb_productos.codigo = tb_categorias.idcategoria;", null);
            while (fila.moveToNext()) {
                registros = new Dto();
                registros.setCodigo(fila.getInt(0));
                registros.setDescripcion(fila.getString(1));
                registros.setPrecio(fila.getDouble(2));
                registros.setIdcategoria(fila.getInt(3));
                registros.setNombrecategoria(fila.getString(4));
                registros.setEstadocategoria(fila.getInt(5));

                registrosList.add(registros);

            }
            listaregistros = new ArrayList<String>();

            for (int i = 0; i <= registrosList.size(); i++) {
                listaregistros.add(registrosList.get(i).getCodigo() + "~" + registrosList.get(i).getDescripcion() + "~" + registrosList.get(i).getPrecio() + "~" + registrosList.get(i).getIdcategoria() + "~" + registrosList.get(i).getNombrecategoria() + "" + registrosList.get(i).getEstadocategoria());
            }
        } catch (Exception e) {
        }
        return listaregistros;
    }

    private ArrayList<String> obtenerListaregistros() {
        listaregistros = new ArrayList<String>();
        listaregistros.add("registros");


        for (int i = 0; i < registrosList.size(); i++) {
            listaregistros.add(registrosList.get(i).getCodigo() + "~" +
                    registrosList.get(i).getDescripcion() + "~" + registrosList.get(i).getPrecio() + "~~"
                    + registrosList.get(i).getIdcategoria() + "~" + registrosList.get(i).getNombrecategoria() +
                    "~" + registrosList.get(i).getEstadocategoria());
        }
        return listaregistros;

    }

    public ArrayList<String> obtenerListaCategorias () {
        listaCategorias = new ArrayList<String>();
        listaCategorias.add("Seleccionar");

        for (int i = 0; i < categoriaList.size(); i++) {
            listaCategorias.add(categoriaList.get(i).getIdcategoria() + "~" +
                    "~" + categoriaList.get(i).getNombrecategoria() + "~" + categoriaList.get(i).getEstadocategoria());
        }
        return listaCategorias;
    }
    public ArrayList<Dto> consultalistacategoria() {
        boolean estado = false;

        SQLiteDatabase db = this.getWritableDatabase();

        Dto categoria = null;
        categoriaList = new ArrayList<Dto>();

        try {
            Cursor fila = db.rawQuery("select * from tb_categorias", null);
            while (fila.moveToNext()) {
                categoria = new Dto();
                categoria.setIdcategoria(fila.getInt(0));
                categoria.setNombrecategoria(fila.getString(1));
                categoria.setEstadocategoria(fila.getInt(2));

                categoriaList.add(categoria);

                Log.i("idcategoria", String.valueOf(categoria.getIdcategoria()));
                Log.i("nombrecategoria", categoria.getNombrecategoria().toString());
                Log.i("estadocategoria", String.valueOf(categoria.getEstadocategoria()));

            }
            obtenerListaCategorias();
        } catch (Exception e) {

        }
        return categoriaList;
    }



    public ArrayList<String> consultaListaCategorias1() {
        boolean estado = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Dto categoria = null;
        categoriaList = new ArrayList<Dto>();
        try {
            Cursor fila = db.rawQuery("select * from tb_categorias", null);
            while (fila.moveToNext()) {
                categoria = new Dto();
                categoria.setIdcategoria(fila.getInt(0));
                categoria.setNombrecategoria(fila.getString(1));
                categoria.setEstadocategoria(fila.getInt(2));

                categoriaList.add(categoria);
            }
            listaCategorias = new ArrayList<String>();

            for (int i = 0; i <= categoriaList.size(); i++) {
                listaCategorias.add(categoriaList.get(i).getIdcategoria() + "~" + categoriaList.get(i).getNombrecategoria() + "" + categoriaList.get(i).getEstadocategoria());
            }
        } catch (Exception e) {
        }
        return listaCategorias;
    }

    public ArrayList<String> consultaListaArticulos1() {
        boolean estado = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Dto articulos = null;
        articulosList = new ArrayList<Dto>();
        try {
            Cursor fila = db.rawQuery("select * from tb_productos", null);
            while (fila.moveToNext()) {
                articulos = new Dto();
                articulos.setCodigo(fila.getInt(0));
                articulos.setDescripcion(fila.getString(1));
                articulos.setPrecio(fila.getDouble(2));
                articulos.setIdcategoria(fila.getInt(3));

                articulosList.add(articulos);
            }

            listaArticulos = new ArrayList<String>();

            for (int i = 0; i <= articulosList.size(); i++) {
                listaArticulos.add(articulosList.get(i).getCodigo() + "~" + articulosList.get(i).getDescripcion() + "" + articulosList.get(i).getPrecio()+ " "+ articulosList.get(i).getIdcategoria());
            }
        } catch (Exception e) {

        }
        return listaArticulos;
    }
}
