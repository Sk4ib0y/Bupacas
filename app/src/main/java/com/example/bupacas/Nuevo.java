package com.example.bupacas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Nuevo extends AppCompatActivity implements View.OnClickListener {

    Button cancelar, guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_nuevo);

        cancelar=findViewById(R.id.cancelar);
        cancelar.setOnClickListener(this);

        guardar=findViewById(R.id.guardar);
        guardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(cancelar.getId() == v.getId())
        {
            finish();
        }
        else if(guardar.getId() == v.getId())
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
    }
}