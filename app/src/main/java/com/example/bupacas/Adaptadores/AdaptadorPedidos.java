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

import com.example.bupacas.Datos.DatosPedido;
import com.example.bupacas.Edit.PedidoEdit;
import com.example.bupacas.Endpoints.DTO.PedidoDTO;
import com.example.bupacas.Pedido;
import com.example.bupacas.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.ViewHolder>  {


    private final Context context;
    private final List<PedidoDTO> listaPedido;

    public AdaptadorPedidos(Context context, List<PedidoDTO> pedidoDTOS) {
        this.context = context;
        this.listaPedido = new ArrayList<>(pedidoDTOS);
    }

    @NonNull
    @Override
    public AdaptadorPedidos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.modeloenvios, parent, false);
        return new AdaptadorPedidos.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPedidos.ViewHolder holder, int position) {
        PedidoDTO pedidoDTO = listaPedido.get(position);
        holder.textView.setText(pedidoDTO.getDestino());

        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(context, DatosPedido.class);
            ((Pedido) context).send(pedidoDTO, intent);
        });

        holder.delete.setOnClickListener(v -> {
            if (context instanceof Pedido) {
                ((Pedido) context).onPedidoDeleteClick(pedidoDTO.getId());
            } else {
                Toast.makeText(context, "Error: contexto no es Pedido", Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(v -> {
            if (context instanceof Pedido) {
                Intent intent=new Intent(context, PedidoEdit.class);
                ((Pedido) context).send(pedidoDTO, intent);
            }
            else {
                Toast.makeText(context, "No disponible", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaPedido.size();
    }

    public void actualizarLista(List<PedidoDTO> nuevaLista) {
        listaPedido.clear();
        listaPedido.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ImageView delete;
        ImageView edit;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);
            delete = itemView.findViewById(R.id.delete);
            edit=itemView.findViewById(R.id.edit);
        }
    }


}
