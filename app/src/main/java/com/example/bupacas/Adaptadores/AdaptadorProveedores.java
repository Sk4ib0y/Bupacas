package com.example.bupacas.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bupacas.Misceláneo.Actions;
import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.Proveedores;
import com.example.bupacas.R;

public class AdaptadorProveedores extends BaseAdapter  {

        Context contexto;
        String texto1[];
        LayoutInflater inflater;
        Actions actions;

    public AdaptadorProveedores(Context contexto, String[] texto1, Actions actions)
        {
            this.contexto = contexto;
            this.texto1 = texto1;
            this.inflater= LayoutInflater.from(contexto);
            this.actions=actions;
        }

        @Override
        public int getCount() {
        return texto1.length;
    }

        @Override
        public long getItemId(int position) {
        return position;
    }

        @Override
        public Object getItem(int position) {
        return texto1[position];
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.modeloproveedores, null);

        TextView textView= convertView.findViewById(R.id.textview);
        ImageView imageView= convertView.findViewById(R.id.imageicon);
        ImageView borrar=convertView.findViewById(R.id.delete);
        ImageView edit=convertView.findViewById(R.id.edit);


        textView.setText(texto1[position]);
        imageView.setImageResource(R.drawable.iconoperfil);

        edit.setOnClickListener(v -> {
            actions.onEdit(position);
        });

        borrar.setOnClickListener(v -> {
            actions.onDelete(position);
        });

        return convertView;
    }

    public void UpdateProveedor(String[] nuevoRFC)
    {
        this.texto1=nuevoRFC;
    }

    }

