package com.example.bupacas;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bupacas.Adaptadores.AdaptadorProveedores;
import com.example.bupacas.Altas.ProovedorAltas;
import com.example.bupacas.Edit.ProveedorEdit;
import com.example.bupacas.Endpoints.DTO.ProveedorDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Endpoints.Service.ProveedorService;
import com.example.bupacas.Misceláneo.Soporte;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Proveedores extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerProveedores;
    private AdaptadorProveedores adaptadorProveedores;
    private ArrayList<ProveedorDTO> listaProveedores = new ArrayList<>();

    private ImageView atras, casita, soporte, mas;

    private ProveedorService proveedorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proveedores);

        recyclerProveedores = findViewById(R.id.listita); // o R.id.recyclerProveedores si cambiaste ID
        atras = findViewById(R.id.atras);
        casita = findViewById(R.id.casita);
        soporte = findViewById(R.id.soporte);
        mas = findViewById(R.id.mas);

        recyclerProveedores.setLayoutManager(new LinearLayoutManager(this));
        adaptadorProveedores = new AdaptadorProveedores(this, listaProveedores);
        recyclerProveedores.setAdapter(adaptadorProveedores);

        // Listeners
        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        soporte.setOnClickListener(this);
        mas.setOnClickListener(this);

        proveedorService = RetrofitClient.getProveedorService();

        cargarProveedores();
    }

    private void cargarProveedores() {
        Call<List<ProveedorDTO>> call = proveedorService.getAllProveedores();

        call.enqueue(new Callback<List<ProveedorDTO>>() {
            @Override
            public void onResponse(Call<List<ProveedorDTO>> call, Response<List<ProveedorDTO>> response) {
                Log.d("PROVEEDORES", "onResponse llamado - Código: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("PROVEEDORES", "Datos recibidos: " + response.body().size() + " proveedores");
                    listaProveedores.clear();
                    listaProveedores.addAll(response.body());
                    adaptadorProveedores.actualizarLista(listaProveedores);
                    recyclerProveedores.requestLayout();
                } else {
                    Log.e("PROVEEDORES", "Respuesta no exitosa - Código: " + response.code());
                    Toast.makeText(Proveedores.this, "Error al cargar proveedores", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProveedorDTO>> call, Throwable t) {
                Log.e("PROVEEDORES", "Fallo en Retrofit: " + t.getMessage(), t);
                Toast.makeText(Proveedores.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
            startActivity(new Intent(this, ProovedorAltas.class));
        }
    }

    public void onProveedorDeleteClick(int proveedorId) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Proveedor")
                .setMessage("¿Estás seguro de que deseas eliminar este proveedor?")
                .setPositiveButton("Sí, eliminar", (dialog, which) -> eliminarProveedor(proveedorId))
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void eliminarProveedor(int idProveedor) {
        Call<Void> call = proveedorService.deleteProveedor(idProveedor);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(Proveedores.this, "Proveedor eliminado correctamente", Toast.LENGTH_SHORT).show();
                    cargarProveedores(); // recarga la lista → desaparece el item
                } else {
                    String errorMsg = "Error " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            errorMsg += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {}
                    Toast.makeText(Proveedores.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(Proveedores.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void send(ProveedorDTO proveedor, Intent intentito) {
        Intent intent = new Intent(intentito);
        intent.putExtra("id", proveedor.getIdProveedor());
        intent.putExtra("rfc", proveedor.getRFC_prov());
        intent.putExtra("nombre", proveedor.getNombre_prov());
        intent.putExtra("empresa", proveedor.getEmpresa_prov());
        intent.putExtra("zona", proveedor.getZona_prov());
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarProveedores();
    }
}