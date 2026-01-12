package com.example.bupacas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Altas.NuevoUsuario;
import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Misceláneo.Soporte;

public class Principal extends AppCompatActivity implements View.OnClickListener {

    LinearLayout proovedores, envios, pagos, inventarios;
    ImageView perfil, soporte, basurita, carpeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);

        perfil=findViewById(R.id.perfil);
        soporte=findViewById(R.id.soporte);
        proovedores=findViewById(R.id.proovedores);
        envios=findViewById(R.id.envios);
        pagos=findViewById(R.id.pagos);
        inventarios=findViewById(R.id.inventarios);
        carpeta=findViewById(R.id.carpeta);
        basurita=findViewById(R.id.basura);

        carpeta.setOnClickListener(this);
        basurita.setOnClickListener(this);
        envios.setOnClickListener(this);
        proovedores.setOnClickListener(this);
        soporte.setOnClickListener(this);
        perfil.setOnClickListener(this);
        pagos.setOnClickListener(this);
        inventarios.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        if (id == perfil.getId())
        {
            Intent intent= new Intent(this, NuevoUsuario.class);
            startActivity(intent);
        }
        else if (id== soporte.getId())
        {
            Intent intent = new Intent(this, Soporte.class);
            startActivity(intent);
        }
        else if(proovedores.getId() == id)
        {
            Intent intent= new Intent(this, Proveedores.class);
            startActivity(intent);
        }
        else if(envios.getId() == id)
        {
            Intent intent= new Intent(this, Envios.class);
            startActivity(intent);
        }
        else if(pagos.getId()== id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if (inventarios.getId()==id)
        {
            Intent intent= new Intent(this, Inventarios.class);
            startActivity(intent);
        }
        else if(basurita.getId()==id)
        {
            Intent intent=new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if(carpeta.getId()==id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
    }
}