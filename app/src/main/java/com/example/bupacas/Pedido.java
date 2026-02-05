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

import com.example.bupacas.Adaptadores.AdaptadorPedidos;
import com.example.bupacas.Altas.PedidoAltas;
import com.example.bupacas.Endpoints.DTO.PedidoDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Endpoints.Service.PedidoService;
import com.example.bupacas.Misceláneo.Soporte;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pedido extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerPedidos;
    private AdaptadorPedidos adaptadorEnvio;
    private ArrayList<PedidoDTO> listaPedidos = new ArrayList<>();

    private ImageView atras, casita, soporte, mas;

    private PedidoService pedidoService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_envios);

        recyclerPedidos = findViewById(R.id.listita);
        atras = findViewById(R.id.atras);
        casita = findViewById(R.id.casita);
        soporte = findViewById(R.id.soporte);
        mas = findViewById(R.id.mas);

        recyclerPedidos.setLayoutManager(new LinearLayoutManager(this));
        adaptadorEnvio = new AdaptadorPedidos(this, listaPedidos);
        recyclerPedidos.setAdapter(adaptadorEnvio);


        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        soporte.setOnClickListener(this);
        mas.setOnClickListener(this);

        pedidoService = RetrofitClient.getPedidoService();

        cargarPedidos();
    }

    private void cargarPedidos() {
        Call<List<PedidoDTO>> call = pedidoService.getAllPedidos();

        call.enqueue(new Callback<List<PedidoDTO>>() {
            @Override
            public void onResponse(Call<List<PedidoDTO>> call, Response<List<PedidoDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaPedidos.clear();
                    listaPedidos.addAll(response.body());
                    adaptadorEnvio.actualizarLista(listaPedidos);
                    recyclerPedidos.requestLayout();
                } else {
                    Toast.makeText(Pedido.this, "Error al cargar pedidos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PedidoDTO>> call, Throwable t) {
                Toast.makeText(Pedido.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
        } else if (id == soporte.getId()) {
            startActivity(new Intent(this, Soporte.class));
        } else if (id == mas.getId()) {
            startActivity(new Intent(this, PedidoAltas.class));
        }
    }

    public void onPedidoDeleteClick(int pedidoId) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Pedido")
                .setMessage("¿Estás seguro de que deseas eliminar este pedido?")
                .setPositiveButton("Sí, eliminar", (dialog, which) -> eliminarPedido(pedidoId))
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void eliminarPedido ( int idPedido){
        Call<Void> call = pedidoService.deletePedido(idPedido);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Pedido.this, "Pedido eliminado correctamente", Toast.LENGTH_SHORT).show();
                    cargarPedidos();
                } else {
                    String errorMsg = "Error " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            errorMsg += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {
                    }
                    Toast.makeText(Pedido.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Pedido.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void send (PedidoDTO pedido, Intent intentito){
        Intent intent = new Intent(intentito);
        intent.putExtra("id", pedido.getId());
        intent.putExtra("cp", pedido.getCodPostal());
        intent.putExtra("destino", pedido.getDestino());
        intent.putExtra("fecha", pedido.getFecha());
        intent.putExtra("cliente", pedido.getIdCliente());
        intent.putExtra("proveedor", pedido.getIdProveedor());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarPedidos();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}