package com.example.bupacas.Edit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bupacas.DAO.ProveedorDAO;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

public class ProveedorEdit extends AppCompatActivity implements View.OnClickListener {


    EditText rfc, nombre, zona, empresa;
    ImageView atras, casita, soporte;
    Button editar;
    String rfcOriginal, nombreOriginal, empresaOriginal, zonaOriginal;
    int idProveedor;
    ProveedorDAO proveedorDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proveedor_edit);

        rfc=findViewById(R.id.rfc);
        nombre=findViewById(R.id.nombre);
        zona=findViewById(R.id.zona);
        atras=findViewById(R.id.atras);
        empresa=findViewById(R.id.empresa);
        editar=findViewById(R.id.send);
        casita=findViewById(R.id.casita);
        soporte=findViewById(R.id.soporte);

        casita.setOnClickListener(this);
        soporte.setOnClickListener(this);
        editar.setOnClickListener(this);
        atras.setOnClickListener(this);

        Intent intent=getIntent();
        rfcOriginal=intent.getStringExtra("rfc");
        nombreOriginal=intent.getStringExtra("nombre");
        empresaOriginal=intent.getStringExtra("empresa");
        zonaOriginal=intent.getStringExtra("zona");
        idProveedor=intent.getIntExtra("id",-1);

        rfc.setText(rfcOriginal);
        nombre.setText(nombreOriginal);
        empresa.setText(empresaOriginal);
        zona.setText(zonaOriginal);


        proveedorDAO=new ProveedorDAO(this);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if(editar.getId() == id)
        {
            String rfcStr=rfc.getText().toString().trim();
            String nombreStr=nombre.getText().toString().trim();
            String empresaStr=empresa.getText().toString().trim();
            String zonaStr=zona.getText().toString().trim();

            if(rfcStr.isEmpty() || nombreStr.isEmpty() || empresaStr.isEmpty() || zonaStr.isEmpty())
            {
                Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean resultado=proveedorDAO.edit(idProveedor, rfcStr, nombreStr, empresaStr, zonaStr);

            if(resultado)
            {
                Toast.makeText(this, "¡Proveedor editado correctamente!", Toast.LENGTH_SHORT).show();
                finish();
            }
            else
            {
                Toast.makeText(this, "Error al editar el proveedor", Toast.LENGTH_SHORT).show();
            }
        }
        else if(casita.getId()==id)
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
        }
        else if(soporte.getId()==id)
        {
            Intent intent= new Intent(this, Soporte.class);
            startActivity(intent);
        }
        else if(atras.getId()==id)
        {
            finish();
        }
    }
}