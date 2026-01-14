package com.example.bupacas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.R;

public class AdaptadorPagos extends BaseAdapter
{
    Context context;
    String textoStr[], estadoStr[], montoStr[];
    LayoutInflater layoutInflater;
    Actions actions;

    public AdaptadorPagos(Context context, String[] textoStr, String[] estado, String[] monto, LayoutInflater layoutInflater, Actions actions) {
        this.context = context;
        this.textoStr = textoStr;
        this.estadoStr = estado;
        this.montoStr = monto;
        this.layoutInflater = layoutInflater;
        this.actions = actions;
    }

    @Override
    public int getCount() {
        return textoStr.length;
    }

    @Override
    public Object getItem(int position) {
        return textoStr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView=layoutInflater.inflate(R.layout.modelopagos, null );

        TextView texto=convertView.findViewById(R.id.texto);
        TextView monto=convertView.findViewById(R.id.monto);
        TextView estado=convertView.findViewById(R.id.estado);
        ImageView imageView=convertView.findViewById(R.id.icono);

        ImageView borrar=convertView.findViewById(R.id.basura);
        ImageView edit=convertView.findViewById(R.id.editar);


        texto.setText(textoStr[position]);
        monto.setText(montoStr[position]);
        estado.setText(estadoStr[position]);
        imageView.setImageResource(R.drawable.logo);

        edit.setOnClickListener(v -> {
            actions.onEdit(position);
        });

        borrar.setOnClickListener(v -> {
            actions.onDelete(position);
        });
        return convertView;
    }
}
