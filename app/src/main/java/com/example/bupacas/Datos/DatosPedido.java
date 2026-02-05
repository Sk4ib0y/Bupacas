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

import com.example.bupacas.Adaptadores.AdaptadorProducto;
import com.example.bupacas.Altas.ProductoAltas;
import com.example.bupacas.Edit.ProductoEdit;
import com.example.bupacas.Endpoints.DTO.ProductoDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatosPedido extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita;
    TextView id, cliente, proveedor, destino, fecha, cp;
    private int idInt, clienteId, proveedorId;
    private String destinoStr, fechaStr, cpStr;
    RecyclerView listita;
    Button añadir;
    List<ProductoDTO> listaProductos=new ArrayList<>();
    String tipo="Cliquea para definir la papa";
    AdaptadorProducto adaptadorProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_envio);

        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        id=findViewById(R.id.tv_id_pedido);
        cliente=findViewById(R.id.tv_cliente);
        proveedor=findViewById(R.id.tv_proveedor);
        destino=findViewById(R.id.tv_destino);
        fecha=findViewById(R.id.tv_fecha);
        cp=findViewById(R.id.tv_cod_postal);
        listita=findViewById(R.id.listita);

        listita.setLayoutManager(new LinearLayoutManager(this));
        añadir=findViewById(R.id.send);
        adaptadorProducto=new AdaptadorProducto(listaProductos, this, new Actions() {
            @Override
            public void onEdit(int position) {
                ProductoDTO productoDTO = listaProductos.get(position);
                Log.d("DEBUG_EDIT", "Producto a editar - ID: " + productoDTO.getId()
                        + " | Empaque: " + productoDTO.getEmpaque());

                if (productoDTO.getId() == null || productoDTO.getId() <= 0) {
                    Toast.makeText(DatosPedido.this, "Error: Producto sin ID válido", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(DatosPedido.this, ProductoEdit.class);
                send(productoDTO, intent);
            }

            @Override
            public void onDelete(int position) {
                ProductoDTO productoDTO=listaProductos.get(position);
                new AlertDialog.Builder(DatosPedido.this)
                        .setTitle("Eliminar producto")
                        .setMessage("¿Eliminar "+productoDTO.getEmpaque() +"?")
                        .setPositiveButton("Si",(d, w) -> {
                            eliminarProducto(productoDTO.getId(), position);
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        listita.setAdapter(adaptadorProducto);

        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        añadir.setOnClickListener(this);

        Intent intent=getIntent();
        destinoStr=intent.getStringExtra("destino");
        fechaStr=intent.getStringExtra("fecha");
        cpStr=intent.getStringExtra("cp");
        idInt=intent.getIntExtra("id", -1);
        clienteId=intent.getIntExtra("cliente", -1);
        proveedorId=intent.getIntExtra("proveedor", -1);
        tipo=intent.getStringExtra("tipo");

        destino.setText(destinoStr);
        fecha.setText(fechaStr);
        cp.setText(cpStr);
        id.setText(String.valueOf(idInt));
        cliente.setText(String.valueOf(clienteId));
        proveedor.setText(String.valueOf(proveedorId));

        cargarProductos();
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if(id==atras.getId())
        {
            finish();
        }
        else if(id==casita.getId())
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
            finish();
        }

        else if(id==añadir.getId())
        {
            Intent intent=new Intent(this, ProductoAltas.class);
            intent.putExtra("idPedido",idInt);
            intent.putExtra("idProveedor",proveedorId);
            startActivity(intent);
        }
    }

    private void cargarProductos() {
        if (idInt == -1) {
            Log.e("DATOS_PEDIDO", "ID inválido");
            Toast.makeText(this, "ID de pedido no válido", Toast.LENGTH_SHORT).show();
            return;
        }
        RetrofitClient.getProductoService().getProductosByPedido(idInt)
                .enqueue(new Callback<List<ProductoDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProductoDTO>> call, Response<List<ProductoDTO>> response) {

                        if (response.isSuccessful()) {
                            List<ProductoDTO> body = response.body();
                            for (ProductoDTO p : body) {
                                Log.d("DEBUG_PRODUCTOS", "Producto id=" + p.getId() + ", empaque=" + p.getEmpaque());
                            }
                            if (body != null) {
                                listaProductos.clear();
                                listaProductos.addAll(body);
                                adaptadorProducto.notifyDataSetChanged();

                                if (listaProductos.isEmpty()) {
                                    Toast.makeText(DatosPedido.this, "Este pedido no tiene productos aún", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Log.w("DATOS_PEDIDO", "Body es null aunque 200 OK");
                                Toast.makeText(DatosPedido.this, "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.e("DATOS_PEDIDO", "Error HTTP: " + response.code());
                            try {
                                String error = response.errorBody() != null ? response.errorBody().string() : "Sin detalles";
                                Log.e("DATOS_PEDIDO", "Error body: " + error);
                                Toast.makeText(DatosPedido.this, "Error servidor: " + response.code() + " - " + error, Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Log.e("DATOS_PEDIDO", "No se pudo leer error body", e);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProductoDTO>> call, Throwable t) {
                        Log.e("DATOS_PEDIDO", "Fallo total: " + t.getMessage(), t);
                        Toast.makeText(DatosPedido.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
    private void eliminarProducto(int id, int position)
    {
        RetrofitClient.getProductoService().deleteProducto(id)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        listaProductos.remove(position);
                        adaptadorProducto.notifyItemRemoved(position);
                        Toast.makeText(DatosPedido.this, "Se elimino el producto", Toast.LENGTH_SHORT).show();
                        cargarProductos();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        Toast.makeText(DatosPedido.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarProductos();
    }

    public void send(ProductoDTO productoDTO, Intent intent)
    {
        intent.putExtra("cantidad", productoDTO.getCantidad());
        intent.putExtra("merma", productoDTO.getMerma());
        intent.putExtra("empaque", productoDTO.getEmpaque());
        intent.putExtra("costo", productoDTO.getCostoGanancia().toPlainString());
        intent.putExtra("id", productoDTO.getId());
        intent.putExtra("idPedido",idInt);
        intent.putExtra("idProveedor",proveedorId);
        startActivity(intent);
    }



}