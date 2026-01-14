package com.example.bupacas.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bupacas.R;

public class AdaptadorEnvios extends BaseAdapter
{

    Context contexto;
    String texto1[];
    int imagen[];
    LayoutInflater inflater;

    public AdaptadorEnvios(Context contexto, String[] texto1, int[] imagen)
    {
        this.contexto = contexto;
        this.texto1 = texto1;
        this.imagen = imagen;
        this.inflater= LayoutInflater.from(contexto);
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
        convertView = inflater.inflate(R.layout.modeloenvios, null);
        TextView textView= convertView.findViewById(R.id.textview);
        textView.setText(texto1[position]);
        return convertView;
    }
}
