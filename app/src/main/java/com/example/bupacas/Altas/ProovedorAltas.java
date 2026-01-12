package com.example.bupacas.Altas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Principal;
import com.example.bupacas.R;
import com.example.bupacas.Misceláneo.Soporte;

public class ProovedorAltas extends AppCompatActivity implements View.OnClickListener {

    EditText rfc, nombre, zona, empresa;
    ImageView atras, casita, folder, soporte, basura;
    Button añadir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proovedor_form);

        rfc=findViewById(R.id.rfc);
        nombre=findViewById(R.id.nombre);
        zona=findViewById(R.id.zona);
        atras=findViewById(R.id.atras);
        empresa=findViewById(R.id.empresa);
        añadir=findViewById(R.id.send);
        casita=findViewById(R.id.casita);
        folder=findViewById(R.id.carpeta);
        soporte=findViewById(R.id.soporte);
        basura=findViewById(R.id.basura);

        casita.setOnClickListener(this);
        folder.setOnClickListener(this);
        soporte.setOnClickListener(this);
        basura.setOnClickListener(this);
        añadir.setOnClickListener(this);
        atras.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if(añadir.getId() == id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if(casita.getId()==id)
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
        }
        else if(folder.getId()==id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if(soporte.getId()==id)
        {
            Intent intent= new Intent(this, Soporte.class);
            startActivity(intent);
        }
        else if(basura.getId()==id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if(atras.getId()==id)
        {
            finish();
        }
    }
}