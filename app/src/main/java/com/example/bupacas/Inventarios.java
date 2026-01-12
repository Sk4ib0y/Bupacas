package com.example.bupacas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Misceláneo.Soporte;

public class Inventarios extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita, comentarios, basurita, folder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventarios);

        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        comentarios=findViewById(R.id.soporte);
        basurita=findViewById(R.id.basura);
        folder=findViewById(R.id.carpeta);

        basurita.setOnClickListener(this);
        folder.setOnClickListener(this);
        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        comentarios.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(atras.getId()==id)
        {
            finish();
        }
        else if(casita.getId()==id)
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
        }
        else if(comentarios.getId()==id)
        {
            Intent intent= new Intent(this, Soporte.class);
            startActivity(intent);
        }
        else if(basurita.getId()==id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if(folder.getId()==id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
    }
}