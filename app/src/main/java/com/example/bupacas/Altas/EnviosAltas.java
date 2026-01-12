package com.example.bupacas.Altas;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import java.util.Calendar;


public class EnviosAltas extends AppCompatActivity implements View.OnClickListener {

    EditText fecha;
    ImageView atras, casita, folder, basurita, comentarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_envios_form);

        fecha=findViewById(R.id.fecha);
        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        folder=findViewById(R.id.carpeta);
        basurita=findViewById(R.id.basura);
        comentarios=findViewById(R.id.soporte);
        Calendar calendar= Calendar.getInstance();

        fecha.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePicker = new DatePickerDialog(
                    this,
                    (view, y, m, d) -> {
                        String fechaStr = d + "/" + (m + 1) + "/" + y;
                        fecha.setText(fechaStr);
                    },
                    year, month, day
            );
            datePicker.show();
        });

        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        folder.setOnClickListener(this);
        basurita.setOnClickListener(this);
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
        else if(folder.getId()==id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if(basurita.getId()==id)
        {
            Intent intent= new Intent(this, NoDisponible.class);
            startActivity(intent);
        }
        else if(comentarios.getId()==id)
        {
            Intent intent= new Intent(this, Soporte.class);
            startActivity(intent);
        }
    }
}