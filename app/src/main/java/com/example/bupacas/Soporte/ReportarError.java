package com.example.bupacas.Soporte;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bupacas.R;

public class ReportarError extends AppCompatActivity implements View.OnClickListener {

    ImageView atras;
    Button enviar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reportar_error);
       
        atras=findViewById(R.id.atras);
        enviar=findViewById(R.id.send);
        
        atras.setOnClickListener(this);
        enviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==atras.getId())
        {
            finish();
        }
        else if(id==enviar.getId())
        {
            finish();
            Toast.makeText(this, "Reporte enviado correctamente", Toast.LENGTH_SHORT).show();
        }
    }
}