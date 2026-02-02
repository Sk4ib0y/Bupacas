package com.example.bupacas.Datos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

public class DatosProv extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita, soporte;
    TextView nombre, datos;
    String nombreStr, rfc, empresa, zona;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_prov);
        atras=findViewById(R.id.atras);
        atras.setOnClickListener(this);
        casita=findViewById(R.id.casita);
        soporte=findViewById(R.id.soporte);

        nombre=findViewById(R.id.nombre);
        datos=findViewById(R.id.datos);

        casita.setOnClickListener(this);
        soporte.setOnClickListener(this);

        Intent intent=getIntent();
        id=intent.getIntExtra("id",-1);
        nombreStr=intent.getStringExtra("nombre");
        rfc=intent.getStringExtra("rfc");
        empresa=intent.getStringExtra("empresa");
        zona=intent.getStringExtra("zona");

        nombre.setText(nombreStr);

        datos.setText("RFC: "+rfc+"\nEmpresa: "+empresa+"\nZona: "+zona+"\nID: "+id);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id==atras.getId())
        {
            finish();
        }
        else if(id==casita.getId())
        {
            startActivity(new Intent(this, Principal.class));
        }
        else if(id==soporte.getId())
        {
            startActivity(new Intent(this, Soporte.class));
        }
    }
}