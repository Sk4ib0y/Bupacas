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

import com.example.bupacas.Endpoints.DTO.PapaDTO;
import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.R;

import java.math.BigDecimal;
import java.util.List;

public class AdaptadorPapa extends RecyclerView.Adapter<AdaptadorPapa.ViewHolder> {
    private final List<PapaDTO> listaPapas;
    private final Context context;
    private final Actions actions;

    public AdaptadorPapa(List<PapaDTO> listaPapas, Context context, Actions actions) {
        this.listaPapas = listaPapas;
        this.context = context;
        this.actions = actions;
    }

    @NonNull
    @Override
    public AdaptadorPapa.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modelopapa, parent, false);
        return new AdaptadorPapa.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPapa.ViewHolder holder, int position) {
        PapaDTO papa = listaPapas.get(position);

        holder.tvTipo.setText("Tipo: " + (papa.getTipo()));

        holder.tvTamaño.setText("Tamaño: " + (papa.getTamaño()));

        holder.tvVariedad.setText("Variedad: " + (papa.getVariedad()));
        holder.tvProveedor.setText("ID del Proveedor: "+ (papa.getIdProveedor()) );

        holder.eliminar.setOnClickListener(v -> {
            if (actions != null) {
                actions.onDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPapas != null ? listaPapas.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvVariedad, tvTamaño, tvTipo, tvProveedor;
        ImageView eliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVariedad = itemView.findViewById(R.id.tv_variedad);
            tvTamaño = itemView.findViewById(R.id.tv_tamaño);
            tvTipo =itemView.findViewById(R.id.tv_tipo);
            eliminar = itemView.findViewById(R.id.delete);
            tvProveedor=itemView.findViewById(R.id.tv_proveedor);

        }
    }
}
