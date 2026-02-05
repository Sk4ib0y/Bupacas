package com.example.bupacas.Edit;

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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bupacas.Altas.GastoAltas;
import com.example.bupacas.Endpoints.DTO.GastoDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GastoEdit extends AppCompatActivity implements View.OnClickListener {

    ImageView casita, comentarios, volver;
    EditText cantidad, tipo;
    Button send;
    private int idBanco,idOg;
    String tipoStrOG, cantidadStrOG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gasto_edit);

        casita=findViewById(R.id.casita);
        comentarios=findViewById(R.id.soporte);
        volver=findViewById(R.id.atras);
        cantidad=findViewById(R.id.cantidad);
        tipo=findViewById(R.id.tipo);
        send=findViewById(R.id.send);

        send.setOnClickListener(this);
        comentarios.setOnClickListener(this);
        casita.setOnClickListener(this);
        volver.setOnClickListener(this);

        Intent intent=getIntent();
        idBanco=intent.getIntExtra("idBanco",-1);
        tipoStrOG=intent.getStringExtra("tipo");
        cantidadStrOG=intent.getStringExtra("cantidad");
        idOg=intent.getIntExtra("id",-1);

        tipo.setText(tipoStrOG);
        cantidad.setText(cantidadStrOG);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id==send.getId())
        {
            editarGasto();
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

    private void editarGasto()
    {
        String tipoStr=tipo.getText().toString().trim();
        String cantidadStr=cantidad.getText().toString().trim();

        if(tipoStr.isEmpty() || cantidadStr.isEmpty())
        {
            Toast.makeText(this, "Favor de llenar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        BigDecimal cantidadBD=new BigDecimal(cantidadStr);

        GastoDTO gastoDTO=new GastoDTO();
        gastoDTO.setCantidad(cantidadBD);
        gastoDTO.setIdBanco(idBanco);
        gastoDTO.setTipo(tipoStr);

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Editando...");
        progressDialog.show();

        RetrofitClient.getGastoService().updateGasto(idOg,gastoDTO)
                .enqueue(new Callback<GastoDTO>() {
                    @Override
                    public void onResponse(Call<GastoDTO> call, Response<GastoDTO> response) {
                        progressDialog.dismiss();


                        if (response.isSuccessful()) {
                            Toast.makeText(GastoEdit.this, "Gasto editado correctamente", Toast.LENGTH_SHORT).show();
                            GastoDTO creado = response.body();
                            if (creado != null && creado.getId() != null) {
                                finish();
                            }
                        } else {
                            String mensajeError = "Error al editar - Código: " + response.code();

                            try {
                                if (response.errorBody() != null) {
                                    String errorBodyStr = response.errorBody().string();
                                    mensajeError += " → " + errorBodyStr;
                                }
                            } catch (Exception e) {
                                Log.e("RETROFIT_TELEFONO", "No se pudo leer errorBody", e);
                            }

                            Toast.makeText(GastoEdit.this, mensajeError, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GastoDTO> call, Throwable throwable) {
                        progressDialog.dismiss();
                        Toast.makeText(GastoEdit.this, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}