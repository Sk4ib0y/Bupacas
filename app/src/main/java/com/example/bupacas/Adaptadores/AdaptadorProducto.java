package com.example.bupacas.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bupacas.Datos.DatosPedido;
import com.example.bupacas.Datos.DatosProducto;
import com.example.bupacas.Edit.ProductoEdit;
import com.example.bupacas.Endpoints.DTO.ProductoDTO;
import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorProducto extends RecyclerView.Adapter<AdaptadorProducto.ViewHolder>
{
    private final List<ProductoDTO> listaProductos;
    private final Context context;
    private final Actions actions;

    public AdaptadorProducto(List<ProductoDTO> listaProductos, Context context, Actions actions) {
        this.listaProductos = listaProductos;
        this.context = context;
        this.actions = actions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.modeloproducto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductoDTO producto = listaProductos.get(position);

        holder.tvCantidad.setText("Cantidad: " + (producto.getCantidad() != null ? producto.getCantidad() : 0) + " kg");

        holder.tvEmpaque.setText("Empaque: " + (producto.getEmpaque() != null ? producto.getEmpaque() : "-"));

        holder.tvMerma.setText("Merma: " + (producto.getMerma() != null ? producto.getMerma() : "0%"));

        BigDecimal costo = producto.getCostoGanancia() != null ? producto.getCostoGanancia() : BigDecimal.ZERO;
        holder.tvCostoGanancia.setText(String.format("Costo/Ganancia: $%.2f", costo.doubleValue()));

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
            Intent intent= new Intent(context, DatosProducto.class);
            ((DatosPedido) context).send(producto, intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaProductos != null ? listaProductos.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvVariedad, tvCantidad, tvEmpaque, tvMerma, tvCostoGanancia;
        ImageView editar, eliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVariedad       = itemView.findViewById(R.id.tv_variedad);
            tvCantidad       = itemView.findViewById(R.id.tv_cantidad);
            tvEmpaque        = itemView.findViewById(R.id.tv_empaque);
            tvMerma          = itemView.findViewById(R.id.tv_merma);
            tvCostoGanancia  = itemView.findViewById(R.id.tv_costo_ganancia);
            editar           = itemView.findViewById(R.id.edit);
            eliminar         = itemView.findViewById(R.id.delete);
        }
    }
}