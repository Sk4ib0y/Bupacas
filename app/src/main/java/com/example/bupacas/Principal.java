package com.example.bupacas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Altas.NuevoUsuario;
import com.example.bupacas.Misceláneo.InicioSesionRegistro;
import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Misceláneo.SesionManager;
import com.example.bupacas.Misceláneo.Soporte;

public class Principal extends AppCompatActivity implements View.OnClickListener {

    LinearLayout proovedores, envios, pagos, inventarios, cliente;
    ImageView perfil, soporte;
    Button logoutb, cancelar;
    FrameLayout logout;
    SesionManager sesionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);

        perfil=findViewById(R.id.perfil);
        soporte=findViewById(R.id.soporte);
        proovedores=findViewById(R.id.proovedores);
        envios=findViewById(R.id.envios);
        pagos=findViewById(R.id.pagos);
        inventarios=findViewById(R.id.inventarios);
        logout=findViewById(R.id.signoutlayout);
        cancelar=findViewById(R.id.cancelar);
        cliente=findViewById(R.id.clientes);
        logoutb=findViewById(R.id.logoutb);


        cancelar.setOnClickListener(this);
        logoutb.setOnClickListener(this);
        envios.setOnClickListener(this);
        proovedores.setOnClickListener(this);
        soporte.setOnClickListener(this);
        perfil.setOnClickListener(this);
        pagos.setOnClickListener(this);
        cliente.setOnClickListener(this);
        inventarios.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        if (id == perfil.getId())
        {
            toggleLayout();
        }

        else if(id ==cancelar.getId())
        {
            toggleLayout();
        }

        else if(id==logoutb.getId())
        {
            sesionManager=new SesionManager(this);
            sesionManager.logout();
            Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, InicioSesionRegistro.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        else if (id== soporte.getId())
        {
            Intent intent = new Intent(this, Soporte.class);
            startActivity(intent);
        }
        else if(proovedores.getId() == id)
        {
            Intent intent= new Intent(this, Proveedores.class);
            startActivity(intent);
        }
        else if(envios.getId() == id)
        {
            Intent intent= new Intent(this, Envios.class);
            startActivity(intent);
        }
        else if(pagos.getId()== id)
        {
            Intent intent= new Intent(this, Pagos.class);
            startActivity(intent);
        }
        else if (inventarios.getId()==id)
        {
            Intent intent= new Intent(this, Inventarios.class);
            startActivity(intent);
        }
        else if(cliente.getId()==id)
        {
            Intent intent=new Intent(this, Cliente.class);
            startActivity(intent);
        }

    }

    private void toggleLayout()
    {
        if(logout.getVisibility()==View.VISIBLE)
        {
            logout.setVisibility(View.GONE);
        }
        else
        {
            logout.setVisibility(View.VISIBLE);
        }
    }
}