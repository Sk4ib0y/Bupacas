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

import com.example.bupacas.Adaptadores.AdaptadorPapa;
import com.example.bupacas.Altas.PapaAltas;
import com.example.bupacas.Endpoints.DTO.PapaDTO;
import com.example.bupacas.Endpoints.DTO.ProductoDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosProducto extends AppCompatActivity implements View.OnClickListener {

    Button añadir;
    TextView idTv, cantidadTv, empaqueTv, mermaTv, gananciaTv, proveedorTv;
    ImageView atras, casita, soporte;
    RecyclerView listita;
    private int idProducto, cantidad, proveedorId;
    private String empaqueStr,mermaStr,gananciaStr;
    private AdaptadorPapa adaptadorPapa;
    List<PapaDTO> listaPapas=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_producto);

        añadir=findViewById(R.id.send);
        idTv=findViewById(R.id.tv_id_producto);
        cantidadTv=findViewById(R.id.tv_cantidad);
        empaqueTv=findViewById(R.id.tv_empaque);
        mermaTv=findViewById(R.id.tv_merma);
        gananciaTv=findViewById(R.id.tv_ganancia);
        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        soporte=findViewById(R.id.soporte);
        proveedorTv=findViewById(R.id.tv_proveedor);
        listita=findViewById(R.id.listita);

        Intent intent=getIntent();
        idProducto=intent.getIntExtra("id", -1);
        cantidad=intent.getIntExtra("cantidad",-1);
        proveedorId=intent.getIntExtra("idProveedor",-1);
        empaqueStr=intent.getStringExtra("empaque");
        mermaStr=intent.getStringExtra("merma");
        gananciaStr=intent.getStringExtra("costo");

        idTv.setText(String.valueOf(idProducto));
        cantidadTv.setText(String.valueOf(cantidad));
        proveedorTv.setText(String.valueOf(proveedorId));
        empaqueTv.setText(empaqueStr);
        mermaTv.setText(mermaStr);
        gananciaTv.setText(gananciaStr);
        listita.setLayoutManager(new LinearLayoutManager(this));
        adaptadorPapa=new AdaptadorPapa(listaPapas, this, new Actions() {
            @Override
            public void onEdit(int position) {

            }

            @Override
            public void onDelete(int position) {
                PapaDTO papaDTO=listaPapas.get(position);
                new AlertDialog.Builder(DatosProducto.this)
                        .setTitle("Eliminar papa")
                        .setMessage("¿Eliminar "+papaDTO.getTipo() +"?")
                        .setPositiveButton("Si",(d, w) -> {
                            eliminarPapa(papaDTO.getId(), position);
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        listita.setAdapter(adaptadorPapa);
        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        añadir.setOnClickListener(this);
        soporte.setOnClickListener(this);

        cargarPapas();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id==atras.getId())
        {
            String tipo = !listaPapas.isEmpty() ? listaPapas.get(0).getTipo() : "Pendiente";
            Intent data = new Intent();
            data.putExtra("tipo", tipo);
            setResult(RESULT_OK, data);
            finish();
        }
        if(id==casita.getId())
        {
            startActivity(new Intent(this, Principal.class));
            finish();
        }
        if(id==soporte.getId())
        {
            startActivity(new Intent(this, Soporte.class));
        }
        if(id==añadir.getId())
        {
            Intent intent=new Intent(this, PapaAltas.class);
            intent.putExtra("idProducto", idProducto);
            intent.putExtra("idProveedor", proveedorId);
            startActivity(intent);
        }
    }

    private void eliminarPapa(int id, int position)
    {
        RetrofitClient.getPapaService().deletePapa(id)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        listaPapas.remove(position);
                        adaptadorPapa.notifyItemRemoved(position);
                        toggleBoton();
                        Toast.makeText(DatosProducto.this, "Se elimino el producto", Toast.LENGTH_SHORT).show();
                        cargarPapas();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(DatosProducto.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        cargarPapas();
    }

    private void cargarPapas()
    {
        if (idProducto == -1) {
            Toast.makeText(this, "Error: producto no válido", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        RetrofitClient.getPapaService().getPapaByProducto(idProducto)
                .enqueue(new Callback<List<PapaDTO>>() {
                    @Override
                    public void onResponse(Call<List<PapaDTO>> call, Response<List<PapaDTO>> response) {

                        if (response.isSuccessful()) {
                            List<PapaDTO> body = response.body();
                            if (body != null) {
                                listaPapas.clear();
                                listaPapas.addAll(body);
                                adaptadorPapa.notifyDataSetChanged();
                                toggleBoton();

                                if (listaPapas.isEmpty()) {
                                    Toast.makeText(DatosProducto.this, "Este pedido no tiene productos aún", Toast.LENGTH_LONG).show();
                                }
                            }
                        } else {
                            try {
                                String error = response.errorBody() != null ? response.errorBody().string() : "Sin detalles";
                                Toast.makeText(DatosProducto.this, "Error servidor: " + response.code() + " - " + error, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                            }
                        }
                    } @Override
                    public void onFailure(Call<List<PapaDTO>> call, Throwable t) {
                        Toast.makeText(DatosProducto.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void toggleBoton()
    {
        if(listaPapas.size() >=1)
        {
            añadir.setVisibility(View.GONE);
        }
        else {
            añadir.setVisibility(View.VISIBLE);
        }
    }
    }
