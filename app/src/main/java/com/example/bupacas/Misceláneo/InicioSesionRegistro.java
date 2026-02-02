package com.example.bupacas.Misceláneo;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bupacas.Adaptadores.AdaptadorUsuario;
import com.example.bupacas.Endpoints.DTO.UsuarioDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Endpoints.Service.UsuarioService;
import com.example.bupacas.Principal;
import com.example.bupacas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioSesionRegistro extends AppCompatActivity implements View.OnClickListener {

    private FrameLayout logindatos, registrodatos;
    public LinearLayout contraseñadatos;
    private RecyclerView recyclerUsuarios;
    private Button registrob, loginsend, loginb, registrosend;
    public EditText contraseñalogin, usuario, gmailregistro, contraseñaregistro, confircontraseña;
    private SesionManager sesionManager;
    private ArrayList<UsuarioDTO> lista = new ArrayList<>();
    private AdaptadorUsuario adaptadorUsuario;
    public UsuarioDTO usuarioSeleccionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_sesion_registro);

        logindatos = findViewById(R.id.logindatos);
        registrodatos = findViewById(R.id.registrodatos);
        contraseñadatos = findViewById(R.id.contraseñadatos);
        recyclerUsuarios = findViewById(R.id.listita);
        registrob = findViewById(R.id.registrob);
        loginb = findViewById(R.id.iniciosesionb);
        loginsend = findViewById(R.id.iniciosesionsend);
        registrosend = findViewById(R.id.registrosend);
        contraseñalogin = findViewById(R.id.contraseña_login);
        usuario = findViewById(R.id.usuario);
        gmailregistro = findViewById(R.id.gmail_registro);
        contraseñaregistro = findViewById(R.id.contraseña_registro);
        confircontraseña = findViewById(R.id.confirmarcontraseña_registro);

        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));
        adaptadorUsuario = new AdaptadorUsuario(this, lista);
        recyclerUsuarios.setAdapter(adaptadorUsuario);

        sesionManager = new SesionManager(this);

        contraseñadatos.setVisibility(View.GONE);
        registrodatos.setVisibility(View.GONE);
        logindatos.setVisibility(View.VISIBLE);

        registrob.setOnClickListener(this);
        loginsend.setOnClickListener(this);
        registrosend.setOnClickListener(this);
        loginb.setOnClickListener(this);

        cargarUsuarios();

        if (sesionManager.isLoggedIn()) {
            startActivity(new Intent(this, Principal.class));
            finish();
        }

        // Manejar extras (opcional)
        String tipo = getIntent().getStringExtra("tipo");
        if (tipo != null) {
            if ("login".equals(tipo)) {
                logindatos.setVisibility(View.VISIBLE);
            } else if ("registro".equals(tipo)) {
                registrodatos.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == registrob.getId()) {
            logindatos.setVisibility(View.INVISIBLE);
            registrodatos.setVisibility(View.VISIBLE);
            contraseñadatos.setVisibility(View.GONE);
        } else if (id == loginb.getId()) {
            logindatos.setVisibility(View.VISIBLE);
            registrodatos.setVisibility(View.INVISIBLE);
            contraseñadatos.setVisibility(View.GONE);
        } else if (id == registrosend.getId()) {
            crearUsuario();
        } else if (id == loginsend.getId()) {
            if (usuarioSeleccionado == null) {
                Toast.makeText(this, "Selecciona un usuario de la lista", Toast.LENGTH_SHORT).show();
                return;
            }

            String contraseñaIngresada = contraseñalogin.getText().toString().trim();
            if (contraseñaIngresada.isEmpty()) {
                Toast.makeText(this, "Ingresa la contraseña", Toast.LENGTH_SHORT).show();
                return;
            }

            validarLogin(usuarioSeleccionado.getId(), contraseñaIngresada);
        }
    }

    private void cargarUsuarios() {
        UsuarioService service = RetrofitClient.getUsuarioService();
        Call<List<UsuarioDTO>> call = service.getAllUsuarios();

        call.enqueue(new Callback<List<UsuarioDTO>>() {
            @Override
            public void onResponse(Call<List<UsuarioDTO>> call, Response<List<UsuarioDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adaptadorUsuario.actualizarLista(response.body());
                } else {
                    Toast.makeText(InicioSesionRegistro.this, "Error al cargar usuarios", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UsuarioDTO>> call, Throwable t) {
                Toast.makeText(InicioSesionRegistro.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearUsuario() {
        String nombre = usuario.getText().toString().trim();
        String email = gmailregistro.getText().toString().trim();
        String password = contraseñaregistro.getText().toString().trim();
        String confirmPassword = confircontraseña.getText().toString().trim();

        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        UsuarioDTO nuevoUsuario = new UsuarioDTO();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setCorreo(email);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setTipo("USER");

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Creando usuario...");
        progress.setCancelable(false);
        progress.show();

        Call<UsuarioDTO> call = RetrofitClient.getUsuarioService().createUsuario(nuevoUsuario);

        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                progress.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(InicioSesionRegistro.this, "Usuario creado correctamente", Toast.LENGTH_SHORT).show();

                    // Limpiar formulario
                    usuario.setText("");
                    gmailregistro.setText("");
                    contraseñaregistro.setText("");
                    confircontraseña.setText("");

                    // Volver a panel de login
                    logindatos.setVisibility(View.VISIBLE);
                    registrodatos.setVisibility(View.GONE);

                    // Recargar lista
                    cargarUsuarios();
                } else {
                    String error = "Error al crear: " + response.code();
                    try {
                        if (response.errorBody() != null) {
                            error += " - " + response.errorBody().string();
                        }
                    } catch (Exception ignored) {}
                    Toast.makeText(InicioSesionRegistro.this, error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(InicioSesionRegistro.this, "Fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void validarLogin(Integer userId, String contraseñaIngresada) {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Validando...");
        progress.setCancelable(false);
        progress.show();

        Call<UsuarioDTO> call = RetrofitClient.getUsuarioService().getUsuarioById(userId);

        call.enqueue(new Callback<UsuarioDTO>() {
            @Override
            public void onResponse(Call<UsuarioDTO> call, Response<UsuarioDTO> response) {
                progress.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    UsuarioDTO usuario = response.body();
                    String passwordServer=usuario.getPassword();
                    if (passwordServer!=null && passwordServer.equals(contraseñaIngresada)) {
                        Toast.makeText(InicioSesionRegistro.this,
                                "¡Bienvenido " + usuario.getNombre() + "!", Toast.LENGTH_SHORT).show();

                        sesionManager.guardarSesion(usuario.getId(), usuario.getNombre(), false);

                        Intent intent = new Intent(InicioSesionRegistro.this, Principal.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(InicioSesionRegistro.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(InicioSesionRegistro.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UsuarioDTO> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(InicioSesionRegistro.this, "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });
    }
}