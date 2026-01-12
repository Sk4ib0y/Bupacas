package com.example.bupacas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Adaptadores.AdaptadorEnvios;
import com.example.bupacas.Altas.EnviosAltas;
import com.example.bupacas.Datos.DatosEnvio;
import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Misceláneo.Soporte;

import java.security.Principal;

public class Envios extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    ImageView atras, casita, carpeta, basura, soporte, mas;
    String texto[] = {"tilin"};
    ListView listita;
    int imagen[] = {R.drawable.papa};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_envios);

        atras= findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        carpeta=findViewById(R.id.carpeta);
        basura=findViewById(R.id.basura);
        mas=findViewById(R.id.mas);
        soporte=findViewById(R.id.soporte);
        casita.setOnClickListener(this);
        carpeta.setOnClickListener(this);
        mas.setOnClickListener(this);
        basura.setOnClickListener(this);
        soporte.setOnClickListener(this);
        listita = findViewById(R.id.listita);
        listita.setOnItemClickListener(this);
        atras.setOnClickListener(v -> finish());
        AdaptadorEnvios adaptador = new AdaptadorEnvios(getApplicationContext(), texto, imagen);
        listita.setAdapter(adaptador);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item=texto[position];

        Intent intent= new Intent(this, DatosEnvio.class);
        intent.putExtra("item",item);
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
        else if(mas.getId()==id)
        {
            Intent intent=new Intent(this, EnviosAltas.class);
            startActivity(intent);
        }
    }
}