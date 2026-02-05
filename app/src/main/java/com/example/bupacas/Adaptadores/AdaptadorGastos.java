package com.example.bupacas.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bupacas.Datos.DatosPedido;
import com.example.bupacas.Datos.DatosProducto;
import com.example.bupacas.Endpoints.DTO.GastoDTO;
import com.example.bupacas.Endpoints.DTO.ProductoDTO;
import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.R;

import java.math.BigDecimal;
import java.util.List;

public class AdaptadorGastos extends RecyclerView.Adapter<AdaptadorGastos.ViewHolder>
{
    private final List<GastoDTO> listaGastos;
    private final Context context;
    private final Actions actions;

    public AdaptadorGastos(List<GastoDTO> listaGastos, Context context, Actions actions) {
        this.listaGastos = listaGastos;
        this.context = context;
        this.actions = actions;
    }

    @NonNull
    @Override
    public AdaptadorGastos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modelogastos, parent, false);
        return new AdaptadorGastos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorGastos.ViewHolder holder, int position) {
        GastoDTO gastoDTO = listaGastos.get(position);

        holder.tvCantidad.setText("Cantidad: " + gastoDTO.getCantidad());

        holder.tvTipo.setText((gastoDTO.getTipo() != null ? gastoDTO.getTipo() : "-"));

        holder.editar.setOnClickListener(v -> {
            if (actions != null) {
                actions.onEdit(position);
            }
        });

        holder.eliminar.setOnClickListener(v -> {
            if (actions != null) {
                actions.onDelete(position);
            }
        });

        holder.itemView.setOnClickListener(v->
        {
        });
    }

    @Override
    public int getItemCount() {
        return listaGastos != null ? listaGastos.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTipo, tvCantidad;
        ImageView editar, eliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCantidad       = itemView.findViewById(R.id.tv_cantidad_gasto);
            tvTipo  = itemView.findViewById(R.id.tv_tipo_gasto);
            editar           = itemView.findViewById(R.id.edit);
            eliminar         = itemView.findViewById(R.id.delete);
        }
    }
}