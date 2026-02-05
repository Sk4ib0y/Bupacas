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

import com.example.bupacas.Datos.DatosBanco;
import com.example.bupacas.Edit.BancoEdit;
import com.example.bupacas.Endpoints.DTO.BancoDTO;
import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Banco;
import com.example.bupacas.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorBanco extends RecyclerView.Adapter<AdaptadorBanco.ViewHolder> {

    private final Context context;
    private final List<BancoDTO> listaBanco;

    public AdaptadorBanco(Context context, List<BancoDTO> bancoDTOS) {
        this.context = context;
        this.listaBanco = new ArrayList<>(bancoDTOS);
    }

    @NonNull
    @Override
    public AdaptadorBanco.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.modelobancos, parent, false);
        return new AdaptadorBanco.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorBanco.ViewHolder holder, int position) {
        BancoDTO bancoDTO = listaBanco.get(position);
        holder.tvEstado.setText(bancoDTO.getEstado());
        holder.tvTipo.setText(bancoDTO.getTipo());
        holder.tvCantidad.setText(String.valueOf(bancoDTO.getCantidad()));

        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context, DatosBanco.class);
            ((Banco) context).send(bancoDTO, intent);
        });

        holder.delete.setOnClickListener(v -> {
            if (context instanceof Banco) {
                ((Banco) context).onBancoDeleteClick(bancoDTO.getId());
            } else {
                Toast.makeText(context, "Error: contexto no es Banco", Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(v -> {
            if (context instanceof Banco) {
                Intent intent=new Intent(context, BancoEdit.class);
                ((Banco) context).send(bancoDTO, intent);
            }
            else {
                Toast.makeText(context, "No disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaBanco.size();
    }

    public void actualizarLista(List<BancoDTO> nuevaLista) {
        listaBanco.clear();
        listaBanco.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTipo, tvCantidad, tvEstado;
        ImageView delete;
        ImageView edit;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTipo = itemView.findViewById(R.id.tv_tipo);
            tvCantidad=itemView.findViewById(R.id.tv_cantidad);
            tvEstado=itemView.findViewById(R.id.tv_estado);
            delete = itemView.findViewById(R.id.delete);
            edit=itemView.findViewById(R.id.edit);
        }
    }


}