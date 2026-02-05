package com.example.bupacas.Misceláneo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Principal;
import com.example.bupacas.R;

public class NoDisponible extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_no_disponible);

        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);

        atras.setOnClickListener(this);
        casita.setOnClickListener(this);

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

    }
}