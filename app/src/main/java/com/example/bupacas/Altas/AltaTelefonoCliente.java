package com.example.bupacas.Altas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Endpoints.DTO.TelefonoClienteDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltaTelefonoCliente extends AppCompatActivity implements View.OnClickListener{
    ImageView casita, comentarios, volver;
    EditText telefono;
    Button send;
    private int idCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alta_telefono_cliente);

        casita=findViewById(R.id.casita);
        comentarios=findViewById(R.id.soporte);
        volver=findViewById(R.id.atras);
        telefono=findViewById(R.id.et_telefono);
        send=findViewById(R.id.send);

        send.setOnClickListener(this);
        comentarios.setOnClickListener(this);
        casita.setOnClickListener(this);
        volver.setOnClickListener(this);

        Intent intent=getIntent();
        idCliente=intent.getIntExtra("idCliente",-1);
        Log.d("ALTA_TELEFONO_CLTE", "ID Cliente recibido en Alta: " + idCliente);

        if (idCliente <= 0) {
            Toast.makeText(this, "Error: ID de cliente no válido (" + idCliente + ")", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id==send.getId())
        {
            crearTelefono();
        }
        if(id==comentarios.getId())
        {
            startActivity(new Intent(this, Soporte.class));
        }
        if(id== casita.getId())
        {
            startActivity(new Intent(this, Principal.class));
            finish();
        }
        if(volver.getId()==id)
        {
            finish();
        }
    }

    private void crearTelefono()
    {
        String telefonoStr=telefono.getText().toString().trim();
        if(telefonoStr.isEmpty())
        {
            Toast.makeText(this, "Favor de escribir un telefono", Toast.LENGTH_SHORT).show();
        }
        TelefonoClienteDTO telefonoClienteDTO=new TelefonoClienteDTO();
        telefonoClienteDTO.setTelefono(telefonoStr);
        telefonoClienteDTO.setIdClte(idCliente);

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Guardando...");
        progressDialog.show();

        RetrofitClient.getTelefonoClienteService().createTelefonoClte(telefonoClienteDTO)
                .enqueue(new Callback<TelefonoClienteDTO>() {
                    @Override
                    public void onResponse(Call<TelefonoClienteDTO> call, Response<TelefonoClienteDTO> response) {
                        progressDialog.dismiss();

                        Log.d("RETROFIT_TELEFONO", "Código HTTP: " + response.code());
                        Log.d("RETROFIT_TELEFONO", "isSuccessful: " + response.isSuccessful());

                        if (response.isSuccessful()) {
                            Toast.makeText(AltaTelefonoCliente.this, "Teléfono añadido correctamente", Toast.LENGTH_SHORT).show();

                            TelefonoClienteDTO creado = response.body();
                            Intent data = new Intent();
                            data.putExtra("nuevoTelefono", telefonoStr);
                            if (creado != null && creado.getId() != null) {
                                data.putExtra("idTelefono", creado.getId());
                            } else {
                                Log.w("RETROFIT_TELEFONO", "ID del teléfono creado vino null");
                            }
                            setResult(RESULT_OK, data);
                            finish();
                        } else {
                            String mensajeError = "Error al guardar - Código: " + response.code();

                            try {
                                if (response.errorBody() != null) {
                                    String errorBodyStr = response.errorBody().string();
                                    Log.e("RETROFIT_TELEFONO", "Error body: " + errorBodyStr);
                                    mensajeError += " → " + errorBodyStr;
                                }
                            } catch (Exception e) {
                                Log.e("RETROFIT_TELEFONO", "No se pudo leer errorBody", e);
                            }

                            Toast.makeText(AltaTelefonoCliente.this, mensajeError, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TelefonoClienteDTO> call, Throwable throwable) {
                        progressDialog.dismiss();
                        Toast.makeText(AltaTelefonoCliente.this, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}