package com.habr.crudsqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class detalles_categoria extends AppCompatActivity {

     private TextView tv_idcategoria, tv_nombrecategoria, tv_estadocategoria, tv_fecha1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_categoria);

        tv_idcategoria=(TextView) findViewById(R.id.idcate);
        tv_nombrecategoria=(TextView) findViewById(R.id.nombrecate);
        tv_estadocategoria=(TextView) findViewById(R.id.estadocate);
        tv_fecha1=(TextView) findViewById(R.id.tv_fechaR);


        Bundle objeto = getIntent().getExtras();
        Dto dato= null;
        if(objeto != null) {
            dato = (Dto) objeto.getSerializable("registro");
            tv_idcategoria.setText("" + dato.getIdcategoria());
            tv_nombrecategoria.setText(dato.getNombrecategoria());
            tv_estadocategoria.setText(dato.getEstadocategoria());
            tv_fecha1.setText("Fecha de creacion: " + getDateTime());
        }

    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a", Locale.getDefault() );
        Date date = new Date();
        return dateFormat.format(date);
    }
}