package com.example.bupacas.Edit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.bupacas.Altas.ProductoAltas;
import com.example.bupacas.Endpoints.DTO.ProductoDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductoEdit extends AppCompatActivity implements View.OnClickListener {

    private EditText cantidad, empaque, merma, costoGanancia;
    private Button send;
    private ImageView casita, comentarios, volver;
    private String empaqueOriginal,mermaOriginal;
    private Integer cantidadOriginal;
    private String costoOriginal;
    private int idProducto=-1;
    private int idPedido=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_producto_altas);

        Intent intent=getIntent();
        empaqueOriginal=intent.getStringExtra("empaque");
        mermaOriginal=intent.getStringExtra("merma");
        cantidadOriginal=intent.getIntExtra("cantidad",-1);
        idProducto=intent.getIntExtra("id",-1);
        costoOriginal=intent.getStringExtra("costo");
        idPedido=intent.getIntExtra("idPedido", -1);
        Log.d("DEBUG_EDIT", "ID recibido en ProductoEdit: " + idProducto);

        cantidad = findViewById(R.id.et_cantidad);
        empaque = findViewById(R.id.et_empaque);
        merma = findViewById(R.id.et_merma);
        costoGanancia = findViewById(R.id.et_costo_ganancia);
        send = findViewById(R.id.send);
        volver = findViewById(R.id.atras);
        casita = findViewById(R.id.casita);
        comentarios = findViewById(R.id.soporte);

        empaque.setText(empaqueOriginal);
        merma.setText(mermaOriginal);
        cantidad.setText(String.valueOf(cantidadOriginal));
        costoGanancia.setText(costoOriginal);

        casita.setOnClickListener(this);
        comentarios.setOnClickListener(this);
        volver.setOnClickListener(this);
        send.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(casita.getId()==id)
        {
            startActivity(new Intent(this, Principal.class));
            finish();
        }
        else if(comentarios.getId()==id)
        {
            startActivity(new Intent(this, Soporte.class));
            finish();
        }
        else if(volver.getId()==id)
        {
            finish();
        }
        else if(send.getId()==id)
        {
            editarProducto();
        }
    }

    private void editarProducto()
    {
        String cantidadStr=cantidad.getText().toString().trim();
        String empaqueStr=empaque.getText().toString().trim();
        String mermaStr=merma.getText().toString().trim();
        String costoGananciaStr=costoGanancia.getText().toString().trim();

        int cantidadInt;
        BigDecimal costoGananciaDecimal = BigDecimal.ZERO;

        if (!TextUtils.isEmpty(costoGananciaStr)) {
            try {
                costoGananciaDecimal = new BigDecimal(costoGananciaStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "El formato de Costo/Ganancia no es correcto", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if(cantidadStr.isEmpty() || empaqueStr.isEmpty() || mermaStr.isEmpty() || costoGananciaStr.isEmpty())
        {
            Toast.makeText(this, "Favor de rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }

        try {
            cantidadInt = Integer.parseInt(cantidadStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Verifica que cantidad sea número válido", Toast.LENGTH_LONG).show();
            return;
        }

        ProductoDTO productoDTO=new ProductoDTO();
        productoDTO.setCantidad(cantidadInt);
        productoDTO.setEmpaque(empaqueStr);
        productoDTO.setMerma(mermaStr);
        productoDTO.setCostoGanancia(costoGananciaDecimal);
        productoDTO.setId(idProducto);

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Editando...");
        progressDialog.show();

        if (idProducto <= 0) {
            Toast.makeText(this, "Error: ID de producto inválido", Toast.LENGTH_LONG).show();
            return;
        }

        RetrofitClient.getProductoService().updateProducto(idProducto, productoDTO)
                .enqueue(new Callback<ProductoDTO>() {
                    @Override
                    public void onResponse(Call<ProductoDTO> call, Response<ProductoDTO> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful())
                        {
                            Toast.makeText(ProductoEdit.this, "Producto editado correctamente", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                        else {
                            Toast.makeText(ProductoEdit.this, "Error al editar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductoDTO> call, Throwable throwable) {
                        progressDialog.dismiss();
                        Toast.makeText(ProductoEdit.this, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}