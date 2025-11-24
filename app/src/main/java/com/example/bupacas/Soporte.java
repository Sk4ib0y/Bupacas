package com.example.bupacas;

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

public class Soporte extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String texto[]={"Guía Rapida", "Preguntas Frecuentes", "Personalizar", "Reportar un error", "Contacto con Soporte", "Privacidad y seguridad"};
    ListView listita;
    ImageView atras;
    int imagen[]={R.drawable.guia, R.drawable.interrogacion, R.drawable.engrane, R.drawable.exclamancion, R.drawable.audifonos, R.drawable.escudito};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_soporte);

        listita=findViewById(R.id.listita);
        listita.setOnItemClickListener(this);
        atras=findViewById(R.id.atras);
        atras.setOnClickListener(v -> finish());
        Adaptador adaptador=new Adaptador(getApplicationContext(), texto, imagen);
        listita.setAdapter(adaptador);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}