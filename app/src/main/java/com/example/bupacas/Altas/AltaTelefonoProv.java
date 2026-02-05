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

import com.example.bupacas.Endpoints.DTO.TelefonoProvDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AltaTelefonoProv extends AppCompatActivity implements View.OnClickListener {

    ImageView casita, volver;
    EditText telefono;
    Button send;
    private int idProv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_alta_telefono_prov);

        casita=findViewById(R.id.casita);
        volver=findViewById(R.id.atras);
        telefono=findViewById(R.id.et_telefono);
        send=findViewById(R.id.send);

        send.setOnClickListener(this);
        casita.setOnClickListener(this);
        volver.setOnClickListener(this);

        Intent intent=getIntent();
        idProv=intent.getIntExtra("idProv",-1);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id==send.getId())
        {
         crearTelefono();
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
        TelefonoProvDTO telefonoProvDTO=new TelefonoProvDTO();
        telefonoProvDTO.setTelefono(telefonoStr);
        telefonoProvDTO.setIdProv(idProv);

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Guardando...");
        progressDialog.show();

        RetrofitClient.getTelefonoProvService().createTelefonoProv(telefonoProvDTO)
                .enqueue(new Callback<TelefonoProvDTO>() {
                    @Override
                    public void onResponse(Call<TelefonoProvDTO> call, Response<TelefonoProvDTO> response) {
                        progressDialog.dismiss();

                        Log.d("RETROFIT_TELEFONO", "Código HTTP: " + response.code());
                        Log.d("RETROFIT_TELEFONO", "isSuccessful: " + response.isSuccessful());

                        if (response.isSuccessful()) {
                            Toast.makeText(AltaTelefonoProv.this, "Teléfono añadido correctamente", Toast.LENGTH_SHORT).show();

                            TelefonoProvDTO creado = response.body();
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
                            // Aquí está el problema → imprime todo lo posible
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

                            Toast.makeText(AltaTelefonoProv.this, mensajeError, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TelefonoProvDTO> call, Throwable throwable) {
                        progressDialog.dismiss();
                        Toast.makeText(AltaTelefonoProv.this, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}