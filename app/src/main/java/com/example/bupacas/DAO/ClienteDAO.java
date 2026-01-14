package com.example.bupacas.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bupacas.Base.Base;

import java.util.ArrayList;

public class ClienteDAO
{
    private Base dbHelper;

    public ClienteDAO(Context context) {
        dbHelper=new Base(context);
    }

    public long insertarCliente(String RFC, String nombre, String empresa, String zona)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues contentValues= new ContentValues();

        contentValues.put("RFC_Clte", RFC);
        contentValues.put("nombre_Clte", nombre);
        contentValues.put("empresa_Clte", empresa);
        contentValues.put("zona_Clte", zona);

        return db.insert("Cliente", null, contentValues);
    }

    public ArrayList<String> verClientes() {
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT idCliente, RFC_Clte, nombre_Clte, empresa_Clte, zona_Clte FROM Cliente", null);

        if(cursor.moveToFirst())
        {
            do {
                int id=cursor.getInt(cursor.getColumnIndexOrThrow("idCliente"));
                String RFC=cursor.getString(cursor.getColumnIndexOrThrow("RFC_Clte"));
                String nombre=cursor.getString(cursor.getColumnIndexOrThrow("nombre_Clte"));
                String empresa=cursor.getString(cursor.getColumnIndexOrThrow("empresa_Clte"));
                String zona=cursor.getString(cursor.getColumnIndexOrThrow("zona_Clte"));
                lista.add(id+";"+RFC+";"+nombre+";"+empresa+";"+zona);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public boolean delete(int id)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int rows=db.delete("Cliente",
                "idCliente=?",
                new String[]{String.valueOf(id)}
        );
        return rows>0;
    }

    public boolean edit(int id, String RFC, String nombre, String empresa, String zona)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("RFC_Clte", RFC);
        contentValues.put("nombre_Clte", nombre);
        contentValues.put("empresa_Clte", empresa);
        contentValues.put("zona_Clte", zona);

        int rows=db.update("Cliente",
                contentValues,
                "idCliente=?",
                new String[]{String.valueOf(id)}
        );
        return rows>0;
    }
}
