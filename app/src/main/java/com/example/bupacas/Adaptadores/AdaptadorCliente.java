package com.example.bupacas.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bupacas.Datos.DatosProv;
import com.example.bupacas.Edit.ClienteEdit;
import com.example.bupacas.Edit.ProveedorEdit;
import com.example.bupacas.Endpoints.DTO.ClienteDTO;
import com.example.bupacas.Cliente;
import com.example.bupacas.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorCliente extends RecyclerView.Adapter<AdaptadorCliente.ViewHolder>  {


    private final Context context;
    private final List<ClienteDTO> listaCliente;

    public AdaptadorCliente(Context context, List<ClienteDTO> clienteDTOS) {
        this.context = context;
        this.listaCliente = new ArrayList<>(clienteDTOS);
    }

    @NonNull
    @Override
    public AdaptadorCliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.modeloclientes, parent, false);
        return new AdaptadorCliente.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCliente.ViewHolder holder, int position) {
        ClienteDTO clienteDTO = listaCliente.get(position);
        holder.tvRFC.setText(clienteDTO.getRfc_Clte());

        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context, DatosProv.class);
            ((Cliente) context).send(clienteDTO, intent);
        });

        holder.delete.setOnClickListener(v -> {
            if (context instanceof Cliente) {
                ((Cliente) context).onClienteDeleteClick(clienteDTO.getIdCliente());
            } else {
                Toast.makeText(context, "Error: contexto no es Cliente", Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(v -> {
            if (context instanceof Cliente) {
                Intent intent=new Intent(context, ClienteEdit.class);
                ((Cliente) context).send(clienteDTO, intent);
            }
            else {
                Toast.makeText(context, "No disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaCliente.size();
    }

    public void actualizarLista(List<ClienteDTO> nuevaLista) {
        listaCliente.clear();
        listaCliente.addAll(nuevaLista);
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

