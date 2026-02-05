package com.example.bupacas;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bupacas.Adaptadores.AdaptadorCliente;
import com.example.bupacas.Altas.ClienteAltas;
import com.example.bupacas.Endpoints.DTO.ClienteDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Endpoints.Service.ClienteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cliente extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerClientes;
    private AdaptadorCliente adaptadorCliente;
    private ArrayList<ClienteDTO> listaClientes = new ArrayList<>();

    private ImageView atras, casita, mas;

    private ClienteService clienteService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cliente);

        recyclerClientes = findViewById(R.id.listita);
        atras = findViewById(R.id.atras);
        casita = findViewById(R.id.casita);
        mas = findViewById(R.id.mas);

        recyclerClientes.setLayoutManager(new LinearLayoutManager(this));
        adaptadorCliente = new AdaptadorCliente(this, listaClientes);
        recyclerClientes.setAdapter(adaptadorCliente);


        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        mas.setOnClickListener(this);

        clienteService = RetrofitClient.getClienteService();

        cargarClientes();
    }

    private void cargarClientes() {
        Call<List<ClienteDTO>> call = clienteService.getAllClientees();

        call.enqueue(new Callback<List<ClienteDTO>>() {
            @Override
            public void onResponse(Call<List<ClienteDTO>> call, Response<List<ClienteDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listaClientes.clear();
                    listaClientes.addAll(response.body());
                    adaptadorCliente.actualizarLista(listaClientes);
                    recyclerClientes.requestLayout();
                } else {
                    Toast.makeText(Cliente.this, "Error al cargar clientes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ClienteDTO>> call, Throwable t) {
                Toast.makeText(Cliente.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
        }else if (id == mas.getId()) {
            startActivity(new Intent(this, ClienteAltas.class));
        }
    }

    public void onClienteDeleteClick(int clienteId) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Cliente")
                .setMessage("¿Estás seguro de que deseas eliminar este cliente?")
                .setPositiveButton("Sí, eliminar", (dialog, which) -> eliminarCliente(clienteId))
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

        private void eliminarCliente ( int idCliente){
            Call<Void> call = clienteService.deleteCliente(idCliente);

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(Cliente.this, "Cliente eliminado correctamente", Toast.LENGTH_SHORT).show();
                        cargarClientes();
                    } else {
                        String errorMsg = "Error " + response.code();
                        try {
                            if (response.errorBody() != null) {
                                errorMsg += " - " + response.errorBody().string();
                            }
                        } catch (Exception ignored) {
                        }
                        Toast.makeText(Cliente.this, errorMsg, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(Cliente.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }

        public void send (ClienteDTO cliente, Intent intentito){
            Intent intent = new Intent(intentito);
            intent.putExtra("id", cliente.getIdCliente());
            intent.putExtra("rfc", cliente.getRfc_Clte());
            intent.putExtra("nombre", cliente.getNombre_Clte());
            intent.putExtra("empresa", cliente.getEmpresa_Clte());
            intent.putExtra("zona", cliente.getZona_Clte());
            startActivity(intent);
        }

        @Override
        protected void onResume() {
            super.onResume();
            cargarClientes();
        }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
