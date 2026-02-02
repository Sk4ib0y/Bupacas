package com.example.bupacas.Edit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.bupacas.Altas.ProovedorAltas;
import com.example.bupacas.Endpoints.DTO.ProveedorDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProveedorEdit extends AppCompatActivity implements View.OnClickListener {


    EditText rfc, nombre, zona, empresa;
    ImageView atras, casita, soporte;
    Button editar;
    String rfcOriginal, nombreOriginal, empresaOriginal, zonaOriginal;
    int idProveedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proveedor_edit);

        rfc=findViewById(R.id.rfc);
        nombre=findViewById(R.id.nombre);
        zona=findViewById(R.id.zona);
        atras=findViewById(R.id.atras);
        empresa=findViewById(R.id.empresa);
        editar=findViewById(R.id.send);
        casita=findViewById(R.id.casita);
        soporte=findViewById(R.id.soporte);

        casita.setOnClickListener(this);
        soporte.setOnClickListener(this);
        editar.setOnClickListener(this);
        atras.setOnClickListener(this);

        Intent intent=getIntent();
        rfcOriginal=intent.getStringExtra("rfc");
        nombreOriginal=intent.getStringExtra("nombre");
        empresaOriginal=intent.getStringExtra("empresa");
        zonaOriginal=intent.getStringExtra("zona");
        idProveedor=intent.getIntExtra("id",-1);

        rfc.setText(rfcOriginal);
        nombre.setText(nombreOriginal);
        empresa.setText(empresaOriginal);
        zona.setText(zonaOriginal);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if(editar.getId() == id)
        {
            editarProveedor();
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

    private void editarProveedor()
    {
        String nombreStr=nombre.getText().toString().trim();
        String RFCStr=rfc.getText().toString().trim();
        String empresaStr=empresa.getText().toString().trim();
        String zonaStr=zona.getText().toString().trim();
        if(TextUtils.isEmpty(nombreStr) || TextUtils.isEmpty(RFCStr) || TextUtils.isEmpty(empresaStr) || TextUtils.isEmpty(zonaStr))
        {
            Toast.makeText(this, "Todos los campos deben rellenarse", Toast.LENGTH_SHORT).show();
            return;
        }

        ProveedorDTO proveedorDTO=new ProveedorDTO();
        proveedorDTO.setEmpresa_prov(empresaStr);
        proveedorDTO.setNombre_prov(nombreStr);
        proveedorDTO.setRFC_prov(RFCStr);
        proveedorDTO.setZona_prov(zonaStr);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Editando usuario...");
        progress.setCancelable(false);
        progress.show();

        Call<ProveedorDTO> call = RetrofitClient.getProveedorService().updateProveedor(idProveedor, proveedorDTO);

            call.enqueue(new Callback<ProveedorDTO>() {
                @Override
                public void onResponse(Call<ProveedorDTO> call, Response<ProveedorDTO> response) {
                    progress.dismiss();
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(ProveedorEdit.this, "Proveedor editado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String error = "Error al editar: " + response.code();
                        try {
                            if (response.errorBody() != null) {
                                error += " - " + response.errorBody().string();
                            }
                        } catch (Exception ignored) {}
                        Toast.makeText(ProveedorEdit.this, error, Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<ProveedorDTO> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(ProveedorEdit.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
    }
}