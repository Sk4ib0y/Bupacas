package com.example.bupacas;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bupacas.Adaptadores.AdaptadorBanco;
import com.example.bupacas.Altas.BancoAltas;
import com.example.bupacas.Endpoints.DTO.BancoDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Endpoints.Service.BancoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Banco extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerBancos;
    private AdaptadorBanco adaptadorBanco;
    private ArrayList<BancoDTO> listaBancos = new ArrayList<>();

    private ImageView atras, casita, mas;

    private BancoService bancoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagos);

        recyclerBancos = findViewById(R.id.listita);
        atras = findViewById(R.id.atras);
        casita = findViewById(R.id.casita);
        mas = findViewById(R.id.mas);

        recyclerBancos.setLayoutManager(new LinearLayoutManager(this));
        adaptadorBanco = new AdaptadorBanco(this, listaBancos);
        recyclerBancos.setAdapter(adaptadorBanco);


        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        mas.setOnClickListener(this);

        bancoService = RetrofitClient.getBancoService();

        cargarBancos();
    }

    private void cargarBancos() {
        Call<List<BancoDTO>> call = bancoService.getAllBancos();

        call.enqueue(new Callback<List<BancoDTO>>() {
            @Override
            public void onResponse(Call<List<BancoDTO>> call, Response<List<BancoDTO>> response) {
                Log.d("Bancos", "onResponse llamado - Código: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Bancos", "Datos recibidos: " + response.body().size() + " bancos");
                    listaBancos.clear();
                    listaBancos.addAll(response.body());
                    adaptadorBanco.actualizarLista(listaBancos);
                    recyclerBancos.requestLayout();
                } else {
                    Log.e("Bancos", "Respuesta no exitosa - Código: " + response.code());
                    Toast.makeText(Banco.this, "Error al cargar bancos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BancoDTO>> call, Throwable t) {
                Log.e("Banco", "Fallo en Retrofit: " + t.getMessage(), t);
                Toast.makeText(Banco.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == atras.getId()) {
            finish();
        } else if (id == casita.getId()) {
            startActivity(new Intent(this, Principal.class));
            finish();
        }  else if (id == mas.getId()) {
            startActivity(new Intent(this, BancoAltas.class));
        }
    }

    public void onBancoDeleteClick(int bancoId) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Banco")
                .setMessage("¿Estás seguro de que deseas eliminar este banco?")
                .setPositiveButton("Sí, eliminar", (dialog, which) -> eliminarBanco(bancoId))
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void eliminarBanco (int idBanco){
        Call<Void> call = bancoService.deleteBanco(idBanco);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Banco.this, "Banco eliminado correctamente", Toast.LENGTH_SHORT).show();
                    cargarBancos();
                } else {
                    String errorMsg = "Error " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            errorMsg += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {
                    }
                    Toast.makeText(Banco.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Banco.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void send (BancoDTO banco, Intent intentito){
        Intent intent = new Intent(intentito);
        intent.putExtra("id", banco.getId());
        intent.putExtra("tipo", banco.getTipo());
        intent.putExtra("estado", banco.getEstado());
        intent.putExtra("cantidad", banco.getCantidad() != null ? banco.getCantidad().toPlainString() : "0");
        intent.putExtra("proveedor", banco.getIdProv());
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        cargarBancos();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
