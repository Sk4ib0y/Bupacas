package com.example.bupacas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bupacas.Adaptadores.AdaptadorProveedores;
import com.example.bupacas.Altas.ProovedorAltas;
import com.example.bupacas.DAO.ProveedorDAO;
import com.example.bupacas.Datos.DatosProv;
import com.example.bupacas.Edit.ProveedorEdit;
import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Misceláneo.Soporte;

import java.security.Principal;
import java.util.ArrayList;

public class Proveedores extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, Actions {

    String rfc[], nombre[], empresa[], zona[];
    ListView listita;
    ImageView atras, casita, soporte, mas;
    Actions actions;
    int ids[], proveedorSeleccionado=-1;
    ProveedorDAO proveedorDAO;
    AdaptadorProveedores adaptadorProveedores;
    Button cancelar, eliminar;
    FrameLayout deleteLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_proveedores);

        atras=findViewById(R.id.atras);
        casita=findViewById(R.id.casita);
        soporte=findViewById(R.id.soporte);
        mas=findViewById(R.id.mas);

        deleteLayout=findViewById(R.id.deleteLayout);
        cancelar=findViewById(R.id.cancelar);
        eliminar=findViewById(R.id.eliminar);

        proveedorDAO=new ProveedorDAO(this);
        actions=this;
        mas.setOnClickListener(this);
        casita.setOnClickListener(this);
        soporte.setOnClickListener(this);
        cancelar.setOnClickListener(this);
        eliminar.setOnClickListener(this);
        listita=findViewById(R.id.listita);
        listita.setOnItemClickListener(this);
        atras.setOnClickListener(v -> finish());

        rfc=new String[0];
        nombre=new String[0];
        empresa=new String[0];
        zona=new String[0];
        adaptadorProveedores=new AdaptadorProveedores(getApplicationContext(), rfc, actions);
        listita.setAdapter(adaptadorProveedores);
        refreshProveedores();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this, DatosProv.class);
        intent.putExtra("id", ids[position]);
        intent.putExtra("rfc", rfc[position]);
        intent.putExtra("nombre", nombre[position]);
        intent.putExtra("empresa", empresa[position]);
        intent.putExtra("zona", zona[position]);
        startActivity(intent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();

        if(id==soporte.getId())
        {
            Intent intent= new Intent(this, Soporte.class);
            startActivity(intent);
        }
        else if(id==casita.getId())
        {
            Intent intent= new Intent(this, Principal.class);
            startActivity(intent);
        }
        else if(mas.getId() == id)
        {
            Intent intent= new Intent(this, ProovedorAltas.class);
            startActivity(intent);
        }
        else if(cancelar.getId()==id)
        {
            toggleDeleteLayout();
        }
        else if(eliminar.getId()==id)
        {
            int idReal=ids[proveedorSeleccionado];
            boolean borrado=proveedorDAO.delete(idReal);
            if(borrado)
            {
                Toast.makeText(this, "Proveedor borrado correctamente", Toast.LENGTH_SHORT).show();
                toggleDeleteLayout();
                refreshProveedores();
            }
            else
            {
                Toast.makeText(this, "Error al eliminar al proveedor", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onEdit(int position) {
        Intent intent=new Intent(this, ProveedorEdit.class);
        intent.putExtra("id", ids[position]);
        intent.putExtra("rfc", rfc[position]);
        intent.putExtra("nombre", nombre[position]);
        intent.putExtra("empresa", empresa[position]);
        intent.putExtra("zona", zona[position]);
        startActivity(intent);
    }

    @Override
    public void onDelete(int position) {
        proveedorSeleccionado=position;
        toggleDeleteLayout();
    }

    private void refreshProveedores()
    {
        ArrayList<String> list=proveedorDAO.verProveedores();
        rfc=new String[list.size()];
        ids=new int[list.size()];
        nombre=new String[list.size()];
        empresa=new String[list.size()];
        zona =new String[list.size()];

        for (int i=0;i<list.size();i++)
        {
            String[] partes=list.get(i).split(";");
            ids[i]=Integer.parseInt(partes[0]);
            rfc[i]=partes[1];
            nombre[i]=partes[2];
            empresa[i]=partes[3];
            zona[i]=partes[4];
        }
        adaptadorProveedores.UpdateProveedor(rfc);
        adaptadorProveedores.notifyDataSetChanged();

    }

    private void toggleDeleteLayout()
    {
        if(deleteLayout.getVisibility()==View.VISIBLE)
        {
            deleteLayout.setVisibility(View.GONE);
        }
        else {
            deleteLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshProveedores();
    }
}