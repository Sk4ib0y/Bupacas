package com.example.bupacas.Altas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Endpoints.DTO.ClienteDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteAltas extends AppCompatActivity implements View.OnClickListener {

    EditText rfc, nombre, zona, empresa;
    ImageView atras, casita;
    Button añadir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente_altas);

        rfc=findViewById(R.id.rfc);
        nombre=findViewById(R.id.nombre);
        zona=findViewById(R.id.zona);
        atras=findViewById(R.id.atras);
        empresa=findViewById(R.id.empresa);
        añadir=findViewById(R.id.send);
        casita=findViewById(R.id.casita);

        casita.setOnClickListener(this);
        añadir.setOnClickListener(this);
        atras.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if(añadir.getId() == id)
        {
            crearCliente();
        }
        else if(casita.getId()==id)
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
        }

        else if(atras.getId()==id)
        {
            finish();
        }
    }

    private void crearCliente()
    {
        String nombreStr=nombre.getText().toString().trim();
        String RFCStr=rfc.getText().toString().trim();
        String empresaStr=empresa.getText().toString().trim();
        String zonaStr=zona.getText().toString().trim();

        if(TextUtils.isEmpty(nombreStr) || TextUtils.isEmpty(RFCStr) || TextUtils.isEmpty(empresaStr) || TextUtils.isEmpty(zonaStr))
        {
            Toast.makeText(this, "Todos los campos deben rellenarse", Toast.LENGTH_SHORT).show();
            return;
        }

        ClienteDTO clienteDTO=new ClienteDTO();
        clienteDTO.setEmpresa_Clte(empresaStr);
        clienteDTO.setNombre_Clte(nombreStr);
        clienteDTO.setRfc_Clte(RFCStr);
        clienteDTO.setZona_Clte(zonaStr);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Creando usuario...");
        progress.setCancelable(false);
        progress.show();

        Call<ClienteDTO> call = RetrofitClient.getClienteService().createCliente(clienteDTO);

        call.enqueue(new Callback<ClienteDTO>() {
            @Override
            public void onResponse(Call<ClienteDTO> call, Response<ClienteDTO> response) {
                progress.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ClienteAltas.this, "Cliente creado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String error = "Error al crear: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            error += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {}
                    Toast.makeText(ClienteAltas.this, error, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ClienteDTO> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ClienteAltas.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}