package com.example.bupacas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NoDisponible extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita, comentarios, basurita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_no_disponible);

        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        comentarios=findViewById(R.id.soporte);
        basurita=findViewById(R.id.basura);

        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        comentarios.setOnClickListener(this);
        basurita.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if (atras.getId()==id)
        {
            finish();
        }
        else if (casita.getId()==id)
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
        }
        else if(comentarios.getId() == id)
        {
            Intent intent= new Intent(this, Soporte.class);
            startActivity(intent);
        }
        else if(basurita.getId() == id)
        {
            Toast.makeText(this, "No Disponible Aun", Toast.LENGTH_SHORT).show();
        }
    }
}