package com.example.bupacas.Edit;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Endpoints.DTO.ClienteDTO;
import com.example.bupacas.Endpoints.DTO.PedidoDTO;
import com.example.bupacas.Endpoints.DTO.ProveedorDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Principal;
import com.example.bupacas.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoEdit extends AppCompatActivity implements View.OnClickListener {

    EditText etFecha, etDestino, etCp;
    Button send;
    ImageView atras, casita;
    List<ClienteDTO> clientes;
    List<ProveedorDTO> proveedores;
    Spinner spinnerCliente, spinnerProveedor;
    private boolean clientesCargados=false;
    private boolean proveedoresCargados=false;
    private int idPedido, idClienteAnterior, idProveedorAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_edit);

        spinnerCliente=findViewById(R.id.spinnerCliente);
        spinnerProveedor=findViewById(R.id.spinnerProveedor);
        cargarClientes();
        cargarProveedores();

        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        etDestino=findViewById(R.id.destino);
        etCp=findViewById(R.id.cp);
        etFecha = findViewById(R.id.etFecha);
        send=findViewById(R.id.send);

        Intent intent= getIntent();
        idPedido=intent.getIntExtra("id",-1);
        String CPOriginal=intent.getStringExtra("cp");
        String destinoOriginal=intent.getStringExtra("destino");
        String fechaOriginal=intent.getStringExtra("fecha");
        idClienteAnterior=intent.getIntExtra("idCliente",-1);
        idProveedorAnterior=intent.getIntExtra("idProveedor",-1);

        etDestino.setText(destinoOriginal);
        etCp.setText(CPOriginal);
        etFecha.setText(fechaOriginal);

        atras.setOnClickListener(this);
        casita.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();

        send.setOnClickListener(v->
        {editarPedido();});

        etFecha.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    PedidoEdit.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String fecha = selectedYear + "-" +
                                String.format("%02d", selectedMonth + 1) + "-" +
                                String.format("%02d", selectedDay);
                        etFecha.setText(fecha);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });


    }

    private void cargarClientes() {
        RetrofitClient.getClienteService().getAllClientees()
                .enqueue(new Callback<List<ClienteDTO>>() {
                    @Override
                    public void onResponse(Call<List<ClienteDTO>> call,
                                           Response<List<ClienteDTO>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            clientes = response.body();
                            cargarSpinnerClientes();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClienteDTO>> call, Throwable t) {
                        Toast.makeText(PedidoEdit.this, "Error cargando clientes", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(PedidoEdit.this, "Error cargando proveedores", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cargarSpinnerClientes() {
        List<String> listaClientes = new ArrayList<>();
        for (ClienteDTO c : clientes) {
            listaClientes.add("ID: "+c.getIdCliente() + " - RFC: " + c.getRfc_Clte());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listaClientes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCliente.setAdapter(adapter);
        clientesCargados=true;
        seleccionarClienteAnterior();
    }

    private void cargarSpinnerProveedores() {
        List<String> listaProveedores = new ArrayList<>();
        for (ProveedorDTO p : proveedores) {
            listaProveedores.add("ID: "+p.getIdProveedor() + " - RFC: " + p.getRFC_prov());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listaProveedores
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerProveedor.setAdapter(adapter);
        proveedoresCargados=true;
        seleccionarProveedorAnterior();
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();

        if(id==casita.getId())
        {
            startActivity(new Intent(this, Principal.class));
            finish();
        }

        else if(id==atras.getId())
        {
            finish();
        }

    }

    private void editarPedido() {
        String fechaStr = etFecha.getText().toString().trim();
        String destinoStr = etDestino.getText().toString().trim();
        String cpStr = etCp.getText().toString().trim();
        ClienteDTO clienteSeleccionado = clientes.get(spinnerCliente.getSelectedItemPosition());
        ProveedorDTO proveedorSeleccionado = proveedores.get(spinnerProveedor.getSelectedItemPosition());

        if (TextUtils.isEmpty(fechaStr) || TextUtils.isEmpty(destinoStr) || TextUtils.isEmpty(cpStr)) {
            Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        } else if (clienteSeleccionado == null || proveedorSeleccionado == null) {
            Toast.makeText(this, "Selecciona un cliente y proveedor", Toast.LENGTH_SHORT).show();
            return;
        }

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setCodPostal(cpStr);
        pedidoDTO.setDestino(destinoStr);
        pedidoDTO.setFecha(fechaStr);
        pedidoDTO.setIdProveedor(proveedorSeleccionado.getIdProveedor());
        pedidoDTO.setIdCliente(clienteSeleccionado.getIdCliente());

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Editando pedido...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<PedidoDTO> call = RetrofitClient.getPedidoService().updatePedido(idPedido, pedidoDTO);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        call.enqueue(new Callback<PedidoDTO>() {
            @Override
            public void onResponse(Call<PedidoDTO> call, Response<PedidoDTO> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(PedidoEdit.this, "Pedido editado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    try {
                        if (response.errorBody() != null) {
                            String err = response.errorBody().string();
                            Toast.makeText(PedidoEdit.this, "Error servidor: " + err, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        Log.e("RETROFIT_PEDIDO", "Error al leer errorBody", e);
                    }
                }
            }

            @Override
            public void onFailure(Call<PedidoDTO> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PedidoEdit.this, "Fallo conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void seleccionarClienteAnterior() {
        if (idClienteAnterior == -1 || !clientesCargados || clientes == null) return;

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getIdCliente() == idClienteAnterior) {
                spinnerCliente.setSelection(i);
                break;
            }
        }
    }

    private void seleccionarProveedorAnterior() {
        if (idProveedorAnterior == -1 || !proveedoresCargados || proveedores == null) return;

        for (int i = 0; i < proveedores.size(); i++) {
            if (proveedores.get(i).getIdProveedor() == idProveedorAnterior) {
                spinnerProveedor.setSelection(i);
                break;
            }
        }
    }
}

