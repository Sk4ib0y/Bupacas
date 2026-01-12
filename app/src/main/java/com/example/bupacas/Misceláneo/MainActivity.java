package com.example.bupacas.Misceláneo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button botoninicio;
    TextView textito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        botoninicio=findViewById(R.id.iniciosesion);
        textito=findViewById(R.id.registro);

        botoninicio.setOnClickListener(this);
        textito.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(botoninicio.getId() == v.getId())
        {
            Intent intent= new Intent(this, InicioSesionRegistro.class);
            intent.putExtra("tipo", "login");
            startActivity(intent);
        }
        else if (textito.getId() == v.getId())
        {
            Intent intent= new Intent(this, InicioSesionRegistro.class);
            intent.putExtra("tipo", "registro");
            startActivity(intent);
        }
    }
}