package com.example.bupacas.Altas;

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

import com.example.bupacas.Endpoints.DTO.PapaDTO;
import com.example.bupacas.Endpoints.DTO.ProductoDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Misceláneo.Soporte;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PapaAltas extends AppCompatActivity implements View.OnClickListener {

    ImageView atras, casita, soporte;
    EditText variedad, tipo, tamaño;
    Button send;
    private int idProducto, idProveedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_papa_altas);

        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        soporte=findViewById(R.id.soporte);
        variedad=findViewById(R.id.et_variedad);
        tipo=findViewById(R.id.et_tipo);
        tamaño=findViewById(R.id.et_tamaño);
        send=findViewById(R.id.send);

        Intent intent=getIntent();
        idProducto=intent.getIntExtra("idProducto", -1);
        idProveedor=intent.getIntExtra("idProveedor", -1);

        casita.setOnClickListener(this);
        soporte.setOnClickListener(this);
        atras.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id==casita.getId())
        {
            startActivity(new Intent(this, Principal.class));
            finish();
        }
        else if(soporte.getId()==id)
        {
            startActivity(new Intent(this, Soporte.class));
            finish();
        }
        else if(atras.getId()==id)
        {
            finish();
        }
        else if(send.getId()==id)
        {
            crearPapa();
        }
    }

    private void crearPapa()
    {
        String variedadStr=variedad.getText().toString().trim();
        String tipoStr=tipo.getText().toString().trim();
        String tamañoStr=tipo.getText().toString().trim();

        int cantidadInt;
        BigDecimal costoGananciaDecimal = BigDecimal.ZERO;

        if(variedadStr.isEmpty() || tipoStr.isEmpty() || tamañoStr.isEmpty())
        {
            Toast.makeText(this, "Favor de rellenar todos los campos", Toast.LENGTH_SHORT).show();
        }

        PapaDTO papaDTO=new PapaDTO();
        papaDTO.setIdProveedor(idProveedor);
        papaDTO.setTipo(tipoStr);
        papaDTO.setTamaño(tamañoStr);
        papaDTO.setVariedad(variedadStr);
        papaDTO.setIdProducto(idProducto);

        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Guardando...");
        progressDialog.show();

        RetrofitClient.getPapaService().createPapa(papaDTO)
                .enqueue(new Callback<PapaDTO>() {
                    @Override
                    public void onResponse(Call<PapaDTO> call, Response<PapaDTO> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful())
                        {
                            Toast.makeText(PapaAltas.this, "Papa añadida correctamente", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);
                            finish();
                        }
                        else {
                            Toast.makeText(PapaAltas.this, "Error al guardar", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PapaDTO> call, Throwable throwable) {
                        progressDialog.dismiss();
                        Toast.makeText(PapaAltas.this, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}