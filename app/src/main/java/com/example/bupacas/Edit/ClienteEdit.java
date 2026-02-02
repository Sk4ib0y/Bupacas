package com.example.bupacas.Edit;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bupacas.Endpoints.DTO.ClienteDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClienteEdit extends AppCompatActivity implements View.OnClickListener {


    EditText rfc, nombre, zona, empresa;
    ImageView atras, casita, soporte;
    Button editar;
    String rfcOriginal, nombreOriginal, empresaOriginal, zonaOriginal;
    int idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente_edit);

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
        idCliente=intent.getIntExtra("id",-1);

        rfc.setText(rfcOriginal);
        nombre.setText(nombreOriginal);
        empresa.setText(empresaOriginal);
        zona.setText(zonaOriginal);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if(editar.getId() == id)
        {
            editarCliente();
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

    private void editarCliente()
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
        progress.setMessage("Editando cliente...");
        progress.setCancelable(false);
        progress.show();

        Call<ClienteDTO> call = RetrofitClient.getClienteService().updateCliente(idCliente, clienteDTO);

        call.enqueue(new Callback<ClienteDTO>() {
            @Override
            public void onResponse(Call<ClienteDTO> call, Response<ClienteDTO> response) {
                progress.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(ClienteEdit.this, "Cliente editado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String error = "Error al editar: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            error += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {}
                    Toast.makeText(ClienteEdit.this, error, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ClienteDTO> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(ClienteEdit.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}