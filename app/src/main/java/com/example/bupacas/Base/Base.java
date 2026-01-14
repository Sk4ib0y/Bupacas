package com.example.bupacas.Base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Base extends SQLiteOpenHelper {

    private static final String DB_NAME= "Bupacas";
    private static final int DB_VERSION=2;

    private static final String CREATE_USUARIO =
            "CREATE TABLE Usuario (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "user TEXT NOT NULL, " +
                    "email TEXT NOT NULL, " +
                    "contrasena TEXT NOT NULL);";

    private static final String CREATE_CLIENTE =
            "CREATE TABLE Cliente (" +
                    "idCliente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "RFC_Clte TEXT, " +
                    "nombre_Clte TEXT, " +
                    "empresa_Clte TEXT, " +
                    "zona_Clte TEXT);";

    private static final String CREATE_TELEFONO_CLIENTE =
            "CREATE TABLE TelefonoClte (" +
                    "idTelefonoClte INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "telefono_Clte TEXT, " +
                    "Cliente_idCliente INTEGER, " +
                    "FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente(idCliente));";

    private static final String CREATE_PROVEEDOR =
            "CREATE TABLE Proveedor (" +
                    "idProveedor INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "RFC_prov TEXT, " +
                    "nombre_prov TEXT, " +
                    "empresa_prov TEXT, " +
                    "zona_prov TEXT);";

    private static final String CREATE_TELEFONO_PROVEEDOR =
            "CREATE TABLE TelefonoProv (" +
                    "idTelefonoProv INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "telefono_Prov TEXT, " +
                    "Proveedor_idProveedor INTEGER, " +
                    "FOREIGN KEY (Proveedor_idProveedor) REFERENCES Proveedor(idProveedor));";

    private static final String CREATE_PAPA =
            "CREATE TABLE Papa (" +
                    "idPapa INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "variedad TEXT, " +
                    "tipo TEXT, " +
                    "tamaño TEXT);";

    private static final String CREATE_PRODUCTO =
            "CREATE TABLE Producto (" +
                    "idProducto INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "empaque TEXT, " +
                    "Papa_idPapa INTEGER, " +
                    "FOREIGN KEY (Papa_idPapa) REFERENCES Papa(idPapa));";

    private static final String CREATE_PEDIDO =
            "CREATE TABLE Pedido (" +
                    "idPedido INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "fecha TEXT, " +
                    "destino TEXT, " +
                    "codPostal TEXT, " +
                    "Cliente_idCliente INTEGER, " +
                    "Proveedor_idProveedor INTEGER, " +
                    "FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente(idCliente), " +
                    "FOREIGN KEY (Proveedor_idProveedor) REFERENCES Proveedor(idProveedor));";

    private static final String CREATE_DETALLE_PEDIDO =
            "CREATE TABLE Detalle_Pedido (" +
                    "idDetallePedido INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cantidad INTEGER, " +
                    "merma REAL, " +
                    "costo_unitario REAL, " +
                    "precio_unitario REAL, " +
                    "Pedido_idPedido INTEGER, " +
                    "Producto_idProducto INTEGER, " +
                    "FOREIGN KEY (Pedido_idPedido) REFERENCES Pedido(idPedido), " +
                    "FOREIGN KEY (Producto_idProducto) REFERENCES Producto(idProducto));";

    private static final String CREATE_BANCO =
            "CREATE TABLE Banco (" +
                    "idBanco INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "tipo TEXT, " +
                    "estado TEXT);";

    private static final String CREATE_GASTOS =
            "CREATE TABLE Gastos (" +
                    "idGastos INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cantidad REAL, " +
                    "tipo TEXT, " +
                    "Banco_idBanco INTEGER, " +
                    "FOREIGN KEY (Banco_idBanco) REFERENCES Banco(idBanco));";

    private static final String CREATE_BANCO_PEDIDO =
            "CREATE TABLE Banco_has_Pedido (" +
                    "idBanco_has_Pedido INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "Banco_idBanco INTEGER, " +
                    "Pedido_idPedido INTEGER, " +
                    "FOREIGN KEY (Banco_idBanco) REFERENCES Banco(idBanco), " +
                    "FOREIGN KEY (Pedido_idPedido) REFERENCES Pedido(idPedido));";


    public Base(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USUARIO);
        db.execSQL(CREATE_CLIENTE);
        db.execSQL(CREATE_TELEFONO_CLIENTE);
        db.execSQL(CREATE_PROVEEDOR);
        db.execSQL(CREATE_TELEFONO_PROVEEDOR);
        db.execSQL(CREATE_PAPA);
        db.execSQL(CREATE_PRODUCTO);
        db.execSQL(CREATE_PEDIDO);
        db.execSQL(CREATE_DETALLE_PEDIDO);
        db.execSQL(CREATE_BANCO);
        db.execSQL(CREATE_GASTOS);
        db.execSQL(CREATE_BANCO_PEDIDO);    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Banco_has_Pedido");
        db.execSQL("DROP TABLE IF EXISTS Gastos");
        db.execSQL("DROP TABLE IF EXISTS Banco");
        db.execSQL("DROP TABLE IF EXISTS Detalle_Pedido");
        db.execSQL("DROP TABLE IF EXISTS Pedido");
        db.execSQL("DROP TABLE IF EXISTS Producto");
        db.execSQL("DROP TABLE IF EXISTS Papa");
        db.execSQL("DROP TABLE IF EXISTS TelefonoProv");
        db.execSQL("DROP TABLE IF EXISTS Proveedor");
        db.execSQL("DROP TABLE IF EXISTS TelefonoClte");
        db.execSQL("DROP TABLE IF EXISTS Cliente");
        db.execSQL("DROP TABLE IF EXISTS Usuario");
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }
}
