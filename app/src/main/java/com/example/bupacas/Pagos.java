package com.example.bupacas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bupacas.Adaptadores.AdaptadorPagos;
import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Misceláneo.Soporte;

public class Pagos extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener , Actions {

    String texto[]={"BBVA", "Santander"}, monto[]={"$300.00", "$550.00"}, estado[]={"Sin Pagar", "En Proceso"};
    ImageView volver, mas, casita, comentarios;
    ListView listita;
    Actions actions;
    AdaptadorPagos adaptadorPagos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagos);

        volver=findViewById(R.id.atras);
        mas=findViewById(R.id.mas);
        casita=findViewById(R.id.casita);
        comentarios=findViewById(R.id.soporte);
        listita=findViewById(R.id.listita);
        actions=this;

        adaptadorPagos=new AdaptadorPagos(this, texto, estado, monto, getLayoutInflater(), actions);
        listita.setAdapter(adaptadorPagos);
        volver.setOnClickListener(this);
        mas.setOnClickListener(this);
        casita.setOnClickListener(this);
        comentarios.setOnClickListener(this);
        listita.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(volver.getId()==id)
        {
            finish();
        }
        else if(casita.getId()==id)
        {
            startActivity(new Intent(this, Principal.class));
        }
        else if(comentarios.getId()==id)
        {
            startActivity(new Intent(this, Soporte.class));
        }
        else if(mas.getId()==id)
        {
            startActivity(new Intent(this, NoDisponible.class));
        }
    }

    @Override
    public void onEdit(int position) {
        startActivity(new Intent(this, NoDisponible.class));
    }

    @Override
    public void onDelete(int position) {
        startActivity(new Intent(this, NoDisponible.class));
    }
}