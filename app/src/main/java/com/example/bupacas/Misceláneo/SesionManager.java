package com.example.bupacas.Misceláneo;

import android.content.Context;
import android.content.SharedPreferences;

public class SesionManager {
    private static final String PREF_NAME = "SesionPrefs";
    private static final String KEY_ID = "usuario_id";
    private static final String KEY_NOMBRE = "usuario_nombre";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_IS_ADMIN = "is_admin";

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    public SesionManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void guardarSesion(Integer id, String nombre, boolean isAdmin) {
        editor.putInt(KEY_ID, id);
        editor.putString(KEY_NOMBRE, nombre);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.putBoolean(KEY_IS_ADMIN, isAdmin);
        editor.apply();
    }

    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }


    public void logout() {
        editor.clear();
        editor.apply();
    }
}