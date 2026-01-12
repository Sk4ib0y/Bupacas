package com.example.bupacas.Misceláneo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Adaptadores.AdaptadorSoporte;
import com.example.bupacas.R;
import com.example.bupacas.Soporte.ContactoSoporte;
import com.example.bupacas.Soporte.GuiaRapida;
import com.example.bupacas.Soporte.Privacidad;
import com.example.bupacas.Soporte.ReportarError;

public class Soporte extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String texto[]={"Guía Rapida", "Reportar un error", "Contacto con Soporte", "Privacidad y seguridad"};
    ListView listita;
    ImageView atras;
    int imagen[]={R.drawable.guia, R.drawable.interrogacion, R.drawable.exclamancion, R.drawable.audifonos, R.drawable.escudito};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_soporte);

        listita=findViewById(R.id.listita);
        listita.setOnItemClickListener(this);
        atras=findViewById(R.id.atras);
        atras.setOnClickListener(v -> finish());
        AdaptadorSoporte adaptadorSoporte =new AdaptadorSoporte(getApplicationContext(), texto, imagen);
        listita.setAdapter(adaptadorSoporte);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(position)
        {
            case 0:
                startActivity(new Intent(this, GuiaRapida.class));
                break;
            case 1:
                startActivity(new Intent(this, ReportarError.class));
                break;
            case 2:
                startActivity(new Intent(this, ContactoSoporte.class));
                break;
            case 3:
                startActivity(new Intent(this, Privacidad.class));
                break;
        }
    }
}