package com.example.bupacas;

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

import com.example.bupacas.Adaptadores.AdaptadorInventario;
import com.example.bupacas.Endpoints.DTO.PapaDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Actions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Inventarios extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita;
    RecyclerView listita;
    AdaptadorInventario adaptadorInventario;
    List<PapaDTO> listapapas= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inventarios);

        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        listita=findViewById(R.id.listita);

        adaptadorInventario=new AdaptadorInventario(listapapas, this, new Actions() {
            @Override
            public void onEdit(int position) {

            }

            @Override
            public void onDelete(int position) {

            }
        });

        listita.setLayoutManager(new LinearLayoutManager(this));
        listita.setAdapter(adaptadorInventario);
        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        cargarInventario();
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

    }

    private void cargarInventario()
    {
        RetrofitClient.getPapaService().getAllPapas()
                .enqueue(new Callback<List<PapaDTO>>() {
                    @Override
                    public void onResponse(Call<List<PapaDTO>> call, Response<List<PapaDTO>> response) {
                        Log.d("INVENTARIO", "Código HTTP: " + response.code());
                        Log.d("INVENTARIO", "Respuesta exitosa? " + response.isSuccessful());

                        if (response.isSuccessful()) {
                            List<PapaDTO> body = response.body();
                            Log.d("INVENTARIO", "body == null? " + (body == null));
                            if (body != null) {
                                Log.d("INVENTARIO", "Cantidad de papas recibidas: " + body.size());
                                for (int i = 0; i < Math.min(3, body.size()); i++) {
                                    PapaDTO p = body.get(i);
                                    Log.d("INVENTARIO", "Papa " + i + ": " + p.getTipo() + " | " + p.getTamaño());
                                }
                            } else {
                                Log.w("INVENTARIO", "body es null aunque 200 OK");
                            }

                            if (body != null) {
                                listapapas.clear();
                                listapapas.addAll(body);
                                adaptadorInventario.notifyDataSetChanged();
                                Log.d("INVENTARIO", "Items agregados al adaptador, ahora hay: " + listapapas.size());
                            }
                        } else {
                            Log.e("INVENTARIO", "No exitoso - código: " + response.code());
                            if (response.errorBody() != null) {
                                try {
                                    String error = response.errorBody().string();
                                    Log.e("INVENTARIO", "Error body: " + error);
                                } catch (Exception e) {
                                    Log.e("INVENTARIO", "No se pudo leer errorBody", e);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PapaDTO>> call, Throwable throwable) {
                        Toast.makeText(Inventarios.this, "Error de conexión: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}