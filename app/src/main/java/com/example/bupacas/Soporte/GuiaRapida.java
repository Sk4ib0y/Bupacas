package com.example.bupacas.Soporte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bupacas.R;

public class GuiaRapida extends AppCompatActivity implements View.OnClickListener {

    ImageView atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_guia_rapida);

        atras=findViewById(R.id.atras);
        atras.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id==atras.getId())
        {
            finish();
        }
    }
}