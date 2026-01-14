package com.example.bupacas.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bupacas.Base.Base;

public class UsuarioDAO
{
    private Base dbHelper;

    public UsuarioDAO(Context context) {
        dbHelper = new Base(context);
    }

    public long insertarUsuario(String user, String email, String contrasena)
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();

        ContentValues values= new ContentValues();

        values.put("user", user);
        values.put("email", email);
        values.put("contrasena",contrasena);

        return  db.insert("usuario",null,values);
    }

    public boolean login(String usuario, String contrasena)
    {
        SQLiteDatabase db=dbHelper.getReadableDatabase();

        Cursor cursor=db.rawQuery(
                "SELECT id FROM Usuario WHERE user=? AND contrasena=?",
                new String[]{usuario, contrasena}
        );
        boolean existe=cursor.moveToFirst();
        cursor.close();
        return existe;
    }
}
