package com.example.bupacas.Datos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bupacas.Adaptadores.AdaptadorGastos;
import com.example.bupacas.Altas.GastoAltas;
import com.example.bupacas.Edit.GastoEdit;
import com.example.bupacas.Endpoints.DTO.GastoDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosBanco extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita;
    TextView id, proveedor, tipo, estado, cantidad;
    private int idInt, proveedorId;
    private String estadoStr, cantidadStr, tipoStr;
    RecyclerView listita;
    Button añadir;
    AdaptadorGastos adaptadorGastos;
    List<GastoDTO> listaGastos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_banco);

        atras = findViewById(R.id.atras);
        casita = findViewById(R.id.casita);
        id = findViewById(R.id.tv_id_banco);
        tipo = findViewById(R.id.tv_tipo);
        proveedor = findViewById(R.id.tv_proveedor);
        estado = findViewById(R.id.estado);
        cantidad = findViewById(R.id.tv_cantidad);
        listita = findViewById(R.id.listita);

        listita.setLayoutManager(new LinearLayoutManager(this));
        añadir = findViewById(R.id.send);
        adaptadorGastos = new AdaptadorGastos(listaGastos, this, new Actions() {
            @Override
            public void onEdit(int position) {
                GastoDTO gastoDTO = listaGastos.get(position);

                if (gastoDTO.getId() == null || gastoDTO.getId() <= 0) {
                    Toast.makeText(DatosBanco.this, "Error: Gasto sin ID válido", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(DatosBanco.this, GastoEdit.class);
                send(gastoDTO, intent);
                startActivity(intent);
            }

            @Override
            public void onDelete(int position) {
                GastoDTO gastoDTO = listaGastos.get(position);
                new AlertDialog.Builder(DatosBanco.this)
                        .setTitle("Eliminar gasto")
                        .setMessage("¿Eliminar " + gastoDTO.getTipo() + "?")
                        .setPositiveButton("Si", (d, w) -> {
                            eliminarGasto(gastoDTO.getId(), position);
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        listita.setAdapter(adaptadorGastos);

        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        añadir.setOnClickListener(this);

        Intent intent = getIntent();
        cantidadStr = intent.getStringExtra("cantidad");
        estadoStr = intent.getStringExtra("estado");
        tipoStr = intent.getStringExtra("tipo");
        idInt = intent.getIntExtra("id", -1);
        proveedorId = intent.getIntExtra("proveedor", -1);

        cantidad.setText(cantidadStr);
        estado.setText(estadoStr);
        tipo.setText(tipoStr);
        id.setText(String.valueOf(idInt));
        proveedor.setText(String.valueOf(proveedorId));

        cargarGastos();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == atras.getId()) {
            finish();
        } else if (id == casita.getId()) {
            Intent intent = new Intent(this, Principal.class);
            startActivity(intent);
            finish();
        } else if (id == añadir.getId()) {
            Intent intent = new Intent(this, GastoAltas.class);
            intent.putExtra("idBanco", idInt);
            intent.putExtra("idProveedor", proveedorId);
            startActivity(intent);
        }
    }

    private void cargarGastos() {
        if (idInt == -1) {
            Toast.makeText(this, "ID de banco no válido", Toast.LENGTH_SHORT).show();
            return;
        }
        RetrofitClient.getGastoService().getGastosByBanco(idInt)
                .enqueue(new Callback<List<GastoDTO>>() {
                    @Override
                    public void onResponse(Call<List<GastoDTO>> call, Response<List<GastoDTO>> response) {

                        if (response.isSuccessful()) {
                            List<GastoDTO> body = response.body();
                            for (GastoDTO g : body) {
                                Log.d("DEBUG_GASTOS", "Gasto id=" + g.getId() + ", cantidad=" + g.getCantidad());
                            }
                            if (body != null) {
                                listaGastos.clear();
                                listaGastos.addAll(body);
                                adaptadorGastos.notifyDataSetChanged();
                                actualizarSaldoNeto();

                                if (listaGastos.isEmpty()) {
                                    Toast.makeText(DatosBanco.this, "Este banco no tiene gastos pendientes", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(DatosBanco.this, "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("DATOS_Banco", "Error HTTP: " + response.code());
                            try {
                                String error = response.errorBody() != null ? response.errorBody().string() : "Sin detalles";
                                Log.e("DATOS_Banco", "Error body: " + error);
                                Toast.makeText(DatosBanco.this, "Error servidor: " + response.code() + " - " + error, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Log.e("DATOS_Banco", "No se pudo leer error body", e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GastoDTO>> call, Throwable t) {
                        Toast.makeText(DatosBanco.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void eliminarGasto(int id, int position) {
        RetrofitClient.getGastoService().deleteGasto(id)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        listaGastos.remove(position);
                        adaptadorGastos.notifyItemRemoved(position);
                        Toast.makeText(DatosBanco.this, "Se elimino el gasto", Toast.LENGTH_SHORT).show();
                        cargarGastos();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(DatosBanco.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarGastos();
    }

    public void send(GastoDTO gastoDTO, Intent intent) {
        intent.putExtra("cantidad", gastoDTO.getCantidad().toPlainString());
        intent.putExtra("tipo", gastoDTO.getTipo());
        intent.putExtra("id", gastoDTO.getId());
        intent.putExtra("idBanco", idInt);
        startActivity(intent);
    }

    private void actualizarSaldoNeto() {
        BigDecimal saldoInicial = new BigDecimal(cantidadStr.replace("$", "").trim()); // limpia si tiene $    }
        BigDecimal totalGastos = listaGastos.stream().map(g -> g.getCantidad() != null ? g.getCantidad()
                        : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal saldoNeto = saldoInicial.subtract(totalGastos);
        cantidad.setText("Saldo neto: $" + String.format("%.2f", saldoNeto.doubleValue()));
    }
}