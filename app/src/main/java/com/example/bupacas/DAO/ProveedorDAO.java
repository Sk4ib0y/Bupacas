package com.example.bupacas.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bupacas.Base.Base;

import java.util.ArrayList;

public class ProveedorDAO
{
    private Base dbHelper;

    public ProveedorDAO(Context context) {
        dbHelper=new Base(context);
    }

    public long insertarProveedor(String RFC, String nombre, String empresa, String zona)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues contentValues= new ContentValues();

        contentValues.put("RFC_prov", RFC);
        contentValues.put("nombre_prov", nombre);
        contentValues.put("empresa_prov", empresa);
        contentValues.put("zona_prov",zona);

        return db.insert("Proveedor", null, contentValues);
    }

    public ArrayList<String> verProveedores()
    {
        ArrayList<String> lista=new ArrayList<>();
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT idProveedor, RFC_prov, nombre_prov, empresa_prov, zona_prov FROM Proveedor", null);

        if(cursor.moveToFirst())
        {
            do {
                int id=cursor.getInt(cursor.getColumnIndexOrThrow("idProveedor"));
                String rfc=cursor.getString(cursor.getColumnIndexOrThrow("RFC_prov"));
                String nombre=cursor.getString(cursor.getColumnIndexOrThrow("nombre_prov"));
                String empresa=cursor.getString(cursor.getColumnIndexOrThrow("empresa_prov"));
                String zona=cursor.getString(cursor.getColumnIndexOrThrow("zona_prov"));

                lista.add(id+";"+rfc+";"+nombre+";"+empresa+";"+zona);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return lista;
    }

    public boolean delete(int id)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        int rows=db.delete("Proveedor",
                "idProveedor=?",
                new String[]{String.valueOf(id)}
        );
        return rows>0;
    }

    public boolean edit(int id, String RFC, String nombre, String empresa, String zona)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("RFC_prov", RFC);
        contentValues.put("nombre_prov", nombre);
        contentValues.put("empresa_prov", empresa);
        contentValues.put("zona_prov",zona);

        int rows=db.update("Proveedor",
                contentValues,
                "idProveedor=?",
                new String[]{String.valueOf(id)}
        );
        return rows>0;
    }

    public boolean existe(String nombre, String RFC)
    {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT idProveedor FROM Proveedor WHERE nombre_prov=? AND RFC_prov=?",
                new String[]{nombre, RFC});
        boolean existe=cursor.moveToFirst();
        cursor.close();
        return existe;
    }

}
