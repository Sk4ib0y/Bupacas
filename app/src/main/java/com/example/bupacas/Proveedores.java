package com.example.bupacas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Adaptadores.AdaptadorProveedores;
import com.example.bupacas.Altas.ProovedorAltas;
import com.example.bupacas.Datos.DatosProv;
import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Misceláneo.Soporte;

import java.security.Principal;

public class Proveedores extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    String texto[]={"tilin"};
    ListView listita;
    ImageView atras, casita, carpeta, basura, soporte, mas;
    int imagen[]={R.drawable.papa};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proveedores);

        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        carpeta=findViewById(R.id.carpeta);
        basura=findViewById(R.id.basura);
        soporte=findViewById(R.id.soporte);
        mas=findViewById(R.id.mas);

        mas.setOnClickListener(this);
        casita.setOnClickListener(this);
        carpeta.setOnClickListener(this);
        basura.setOnClickListener(this);
        soporte.setOnClickListener(this);
        listita=findViewById(R.id.listita);
        listita.setOnItemClickListener(this);
        atras.setOnClickListener(v -> finish());
        AdaptadorProveedores adaptador=new AdaptadorProveedores(getApplicationContext(), texto, imagen);
        listita.setAdapter(adaptador);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this, DatosProv.class);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if(id==soporte.getId())
        {
            Intent intent= new Intent(this, Soporte.class);
            startActivity(intent);
        }
        else if(id==casita.getId())
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
        }
        else if(basura.getId()== id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if(carpeta.getId()== id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if(mas.getId() == id)
        {
            Intent intent= new Intent(this, ProovedorAltas.class);
            startActivity(intent);
        }
    }
}