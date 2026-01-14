package com.example.bupacas.Misceláneo;

import android.content.Context;
import android.content.SharedPreferences;

public class SesionManager
{

    private static final String NAME="sesion";
    private static final String LOGIN="login";
    private static  final String USER="usuario";


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public SesionManager(Context context) {
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        editor=preferences.edit();
    }

    public void login(String usuario)
    {
        editor.putBoolean(LOGIN, true);
        editor.putString(USER, usuario);
        editor.apply();
    }

    public void logout()
    {
        editor.clear();
        editor.apply();
    }

    public boolean isAdmin()
    {
        return preferences.getBoolean(LOGIN, false);
    }

    public String getUser()
    {
        return preferences.getString(USER, "Usuario");
    }
}