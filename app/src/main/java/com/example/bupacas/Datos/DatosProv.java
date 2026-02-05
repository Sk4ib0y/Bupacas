package com.example.bupacas.Datos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Altas.AltaTelefonoProv;
import com.example.bupacas.Endpoints.DTO.TelefonoProvDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosProv extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita;
    TextView nombre, datos, telefono;
    String nombreStr, rfc, empresa, zona;
    int idProv;
    int idTelefono=-1;
    String nuevoTelefono;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_prov);
        atras=findViewById(R.id.atras);
        atras.setOnClickListener(this);
        casita=findViewById(R.id.casita);
        telefono=findViewById(R.id.telefono);
        nombre=findViewById(R.id.nombre);
        datos=findViewById(R.id.datos);

        telefono.setOnClickListener(this);
        casita.setOnClickListener(this);

        Intent intent=getIntent();
        idProv=intent.getIntExtra("id",-1);
        nombreStr=intent.getStringExtra("nombre");
        rfc=intent.getStringExtra("rfc");
        empresa=intent.getStringExtra("empresa");
        zona=intent.getStringExtra("zona");

        nombre.setText(nombreStr);

        datos.setText("RFC: "+rfc+"\nEmpresa: "+empresa+"\nZona: "+zona+"\nID: "+idProv);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id==atras.getId())
        {
            finish();
        }
        else if(id==casita.getId())
        {
            startActivity(new Intent(this, Principal.class));
        }

        else if(id==telefono.getId() && telefono.getText().equals("Cliquea para añadir un teléfono"))
        {
            Intent intentito=new Intent(this, AltaTelefonoProv.class);
            intentito.putExtra("idProv", idProv);
            startActivityForResult(intentito,17);
        }
        else if (id==telefono.getId() && !telefono.getText().equals("Cliquea para añadir un teléfono"))
        {
            TelefonoProvDTO telefonoProvDTO=new TelefonoProvDTO();
            new AlertDialog.Builder(DatosProv.this)
                    .setTitle("Eliminar telefono")
                    .setMessage("¿Eliminar "+nuevoTelefono +"?")
                    .setPositiveButton("Si",(d, w) -> {
                        eliminarTelefono();
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 17 && resultCode == RESULT_OK && data != null) {
            nuevoTelefono = data.getStringExtra("nuevoTelefono");
            idTelefono = data.getIntExtra("idTelefono", -1);
            if (nuevoTelefono != null && !nuevoTelefono.isEmpty()) {
                telefono.setText("Teléfono: " + nuevoTelefono);
            } else {
                telefono.setText("Cliquea para añadir un teléfono");
            }
        }
    }
    private void eliminarTelefono() {
        if (idTelefono <= 0) {
            Toast.makeText(this, "No hay teléfono para eliminar", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitClient.getTelefonoProvService().deleteTelefonoProv(idTelefono)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            telefono.setText("Cliquea para añadir un teléfono");
                            idTelefono = -1;
                            Toast.makeText(DatosProv.this, "Teléfono eliminado", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DatosProv.this, "Error al eliminar (código: " + response.code() + ")", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(DatosProv.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    }
