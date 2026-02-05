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

import com.example.bupacas.Endpoints.DTO.GastoDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GastoEdit extends AppCompatActivity implements View.OnClickListener {

    ImageView casita, volver;
    EditText etCantidad, etTipo;
    Button btnGuardar;
    private int idBanco, idGasto;
    private GastoDTO gastoOriginal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gasto_edit);

        casita = findViewById(R.id.casita);
        volver = findViewById(R.id.atras);
        etCantidad = findViewById(R.id.cantidad);
        etTipo = findViewById(R.id.tipo);
        btnGuardar = findViewById(R.id.send);

        btnGuardar.setOnClickListener(this);
        casita.setOnClickListener(this);
        volver.setOnClickListener(this);

        Intent intent = getIntent();
        idBanco = intent.getIntExtra("idBanco", -1);
        idGasto = intent.getIntExtra("id", -1);
        String tipoOG = intent.getStringExtra("tipo");
        String cantidadOG = intent.getStringExtra("cantidad");

        if (idGasto == -1 || idBanco == -1) {
            Toast.makeText(this, "Datos incompletos", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        etTipo.setText(tipoOG != null ? tipoOG : "");
        etCantidad.setText(cantidadOG != null ? cantidadOG : "");

        btnGuardar.setText("Guardar Cambios");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == btnGuardar.getId()) {
            editarGasto();
        } else if (id == casita.getId()) {
            startActivity(new Intent(this, Principal.class));
            finishAffinity();
        } else if (id == volver.getId()) {
            finish();
        }
    }

    private void editarGasto() {
        String tipoStr = etTipo.getText().toString().trim();
        String cantidadStr = etCantidad.getText().toString().trim();

        if (tipoStr.isEmpty() || cantidadStr.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        BigDecimal cantidadBD;
        try {
            cantidadBD = new BigDecimal(cantidadStr);
        } catch (NumberFormatException e) {
            etCantidad.setError("Número inválido (ej: 450.50)");
            etCantidad.requestFocus();
            return;
        }

        GastoDTO gastoEditado = new GastoDTO();
        gastoEditado.setId(idGasto);
        gastoEditado.setCantidad(cantidadBD);
        gastoEditado.setTipo(tipoStr);
        gastoEditado.setIdBanco(idBanco);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Actualizando gasto...");
        progress.setCancelable(false);
        progress.show();

        RetrofitClient.getGastoService().updateGasto(idGasto, gastoEditado)
                .enqueue(new Callback<GastoDTO>() {
                    @Override
                    public void onResponse(Call<GastoDTO> call, Response<GastoDTO> response) {
                        progress.dismiss();

                        if (response.isSuccessful() && response.body() != null) {
                            Toast.makeText(GastoEdit.this, "Gasto actualizado correctamente", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            String errorMsg = "Error " + response.code();
                            try {
                                if (response.errorBody() != null) {
                                    errorMsg += " - " + response.errorBody().string();
                                }
                            } catch (Exception ignored) {}
                            Toast.makeText(GastoEdit.this, errorMsg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GastoDTO> call, Throwable t) {
                        progress.dismiss();
                        Toast.makeText(GastoEdit.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}