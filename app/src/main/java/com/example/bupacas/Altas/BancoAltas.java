package com.example.bupacas.Altas;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Endpoints.DTO.BancoDTO;
import com.example.bupacas.Endpoints.DTO.ProveedorDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BancoAltas extends AppCompatActivity implements View.OnClickListener {

    EditText estado, tipo, cantidad;
    ImageView atras, casita, soporte;
    Button añadir;
    Spinner spinnerProveedores;
    List<ProveedorDTO>proveedores;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_banco_altas);

        estado=findViewById(R.id.estado);
        tipo=findViewById(R.id.tipo);
        cantidad=findViewById(R.id.cantidad);
        atras=findViewById(R.id.atras);
        añadir=findViewById(R.id.send);
        casita=findViewById(R.id.casita);
        soporte=findViewById(R.id.soporte);
        spinnerProveedores =findViewById(R.id.spinnerProveedor);

        casita.setOnClickListener(this);
        soporte.setOnClickListener(this);
        añadir.setOnClickListener(this);
        atras.setOnClickListener(this);

        cargarProveedores();
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if(añadir.getId() == id)
        {
            crearBanco();
        }
        else if(casita.getId()==id)
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
        }
        else if(soporte.getId()==id)
        {
            Intent intent= new Intent(this, Soporte.class);
            startActivity(intent);
        }
        else if(atras.getId()==id)
        {
            finish();
        }
    }

    private void crearBanco()
    {
        String estadoStr=estado.getText().toString().trim();
        String cantidadStr=cantidad.getText().toString().trim();
        String tipoStr=tipo.getText().toString().trim();
        ProveedorDTO proveedorSeleccionado=proveedores.get(spinnerProveedores.getSelectedItemPosition());

        if(TextUtils.isEmpty(estadoStr) || TextUtils.isEmpty(cantidadStr) || TextUtils.isEmpty(tipoStr))
        {
            Toast.makeText(this, "Todos los campos deben rellenarse", Toast.LENGTH_SHORT).show();
            return;
        }
        BigDecimal cantidadBigDecimal;
        try {
            cantidadBigDecimal = new BigDecimal(cantidadStr);

            if (cantidadBigDecimal.compareTo(BigDecimal.ZERO) <= 0) {
                Toast.makeText(this, "La cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show();  
                cantidad.requestFocus();
                return;
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Formato inválido, debe ser numerico", Toast.LENGTH_SHORT).show();
            cantidad.requestFocus();
            return;
        }

        if(proveedorSeleccionado==null)
        {
            Toast.makeText(this, "Favor de seleccionar un proveedor", Toast.LENGTH_SHORT).show();
            return;
        }

        int idProv=proveedorSeleccionado.getIdProveedor();
        BancoDTO bancoDTO=new BancoDTO();
        bancoDTO.setCantidad(cantidadBigDecimal);
        bancoDTO.setEstado(estadoStr);
        bancoDTO.setTipo(tipoStr);
        bancoDTO.setIdProv(idProv);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Creando banco...");
        progress.setCancelable(false);
        progress.show();

        Call<BancoDTO> call = RetrofitClient.getBancoService().createBanco(bancoDTO);

        call.enqueue(new Callback<BancoDTO>() {
            @Override
            public void onResponse(Call<BancoDTO> call, Response<BancoDTO> response) {
                progress.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(BancoAltas.this, "Banco creado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String error = "Error al crear: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            error += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {}
                    Toast.makeText(BancoAltas.this, error, Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<BancoDTO> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(BancoAltas.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void cargarProveedores() {
        RetrofitClient.getProveedorService().getAllProveedores()
                .enqueue(new Callback<List<ProveedorDTO>>() {
                    @Override
                    public void onResponse(Call<List<ProveedorDTO>> call,
                                           Response<List<ProveedorDTO>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            proveedores = response.body();
                            cargarSpinnerProveedores();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ProveedorDTO>> call, Throwable t) {
                        Toast.makeText(BancoAltas.this, "Error cargando spinnerProveedores", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cargarSpinnerProveedores() {
        List<String> listaProveedores = new ArrayList<>();
        for (ProveedorDTO p : proveedores) {
            listaProveedores.add("RFC: "+p.getRFC_prov() + " - Nombre: " + p.getNombre_prov());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listaProveedores
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProveedores.setAdapter(adapter);
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
