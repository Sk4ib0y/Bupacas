package com.example.bupacas.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bupacas.Datos.DatosProv;
import com.example.bupacas.Edit.ProveedorEdit;
import com.example.bupacas.Endpoints.DTO.ProveedorDTO;
import com.example.bupacas.Endpoints.DTO.UsuarioDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Endpoints.Service.ProveedorService;
import com.example.bupacas.Endpoints.Service.UsuarioService;
import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.Misceláneo.InicioSesionRegistro;
import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Proveedores;
import com.example.bupacas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdaptadorProveedores extends RecyclerView.Adapter<AdaptadorProveedores.ViewHolder>  {


    private final Context context;
    private final List<ProveedorDTO> listaProveedor;

    public AdaptadorProveedores(Context context, List<ProveedorDTO> proveedorDTOS) {
        this.context = context;
        this.listaProveedor = new ArrayList<>(proveedorDTOS);
    }

    @NonNull
    @Override
    public AdaptadorProveedores.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.modeloproveedores, parent, false);
        return new AdaptadorProveedores.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProveedorDTO proveedorDTO = listaProveedor.get(position);
        holder.tvRFC.setText(proveedorDTO.getRFC_prov());

        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context, DatosProv.class);
            ((Proveedores) context).send(proveedorDTO, intent);
        });

        holder.delete.setOnClickListener(v -> {
            if (context instanceof Proveedores) {
                ((Proveedores) context).onProveedorDeleteClick(proveedorDTO.getIdProveedor());
            } else {
                Toast.makeText(context, "Error: contexto no es Proveedores", Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(v -> {
            if (context instanceof Proveedores) {
                Intent intent=new Intent(context, ProveedorEdit.class);
                ((Proveedores) context).send(proveedorDTO, intent);
            }
            else {
                Toast.makeText(context, "No disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaProveedor.size();
    }

    public void actualizarLista(List<ProveedorDTO> nuevaLista) {
        listaProveedor.clear();
        listaProveedor.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvRFC;
        ImageView delete;
        ImageView edit;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageicon);
            tvRFC = itemView.findViewById(R.id.tv_rfc);
            delete = itemView.findViewById(R.id.delete);
            edit=itemView.findViewById(R.id.edit);
        }
    }


}
