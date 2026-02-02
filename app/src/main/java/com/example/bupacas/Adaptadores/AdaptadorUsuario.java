package com.example.bupacas.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bupacas.Endpoints.DTO.UsuarioDTO;
import com.example.bupacas.Endpoints.Retrofit.RetrofitClient;
import com.example.bupacas.Endpoints.Service.UsuarioService;
import com.example.bupacas.Misceláneo.InicioSesionRegistro;
import com.example.bupacas.Misceláneo.NoDisponible;
import com.example.bupacas.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdaptadorUsuario extends RecyclerView.Adapter<AdaptadorUsuario.ViewHolder> {

    private final Context context;
    private final List<UsuarioDTO> listaUsuario;

    public AdaptadorUsuario(Context context, List<UsuarioDTO> usuarioDTOS) {
        this.context = context;
        this.listaUsuario = new ArrayList<>(usuarioDTOS);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.modelousuario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UsuarioDTO usuarioDTO = listaUsuario.get(position);

        holder.tvNombre.setText(usuarioDTO.getNombre());
        holder.tvEmail.setText(usuarioDTO.getCorreo());

        holder.itemView.setOnClickListener(v -> {
            Log.d("RecyclerDebug", "Clic en posición: " + position);
            Toast.makeText(context, "Seleccionado: " + usuarioDTO.getNombre(), Toast.LENGTH_SHORT).show();

            if (context instanceof InicioSesionRegistro) {
                InicioSesionRegistro activity = (InicioSesionRegistro) context;
                activity.usuarioSeleccionado = usuarioDTO;
                activity.contraseñadatos.setVisibility(View.VISIBLE);
                activity.contraseñalogin.setText("");
                activity.contraseñalogin.requestFocus();
            }
        });


        holder.delete.setOnClickListener(v -> {
            eliminarUsuario(usuarioDTO.getId(), position);
        });
    }

    @Override
    public int getItemCount() {
        return listaUsuario.size();
    }

    public void actualizarLista(List<UsuarioDTO> nuevaLista) {
        listaUsuario.clear();
        listaUsuario.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvNombre;
        TextView tvEmail;
        ImageView delete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageicon);
            tvNombre = itemView.findViewById(R.id.tv_nombre_o_email);
            tvEmail = itemView.findViewById(R.id.tv_email);
            delete = itemView.findViewById(R.id.delete);
        }
    }

    private void eliminarUsuario(Integer id, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Eliminar usuario")
                .setMessage("¿Estás seguro de que deseas eliminar este usuario?")
                .setPositiveButton("Sí, eliminar", (dialog, which) -> {

                    Toast.makeText(context, "Eliminando usuario...", Toast.LENGTH_SHORT).show();

                    UsuarioService usuarioService = RetrofitClient.getUsuarioService();
                    Call<Void> call = usuarioService.deleteUsuario(id);

                    call.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "Usuario eliminado correctamente", Toast.LENGTH_SHORT).show();

                                listaUsuario.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, listaUsuario.size());
                            } else {
                                try {
                                    String errorMsg = response.errorBody() != null
                                            ? response.errorBody().string()
                                            : "Error " + response.code();
                                    Toast.makeText(context, "No se pudo eliminar: " + errorMsg, Toast.LENGTH_LONG).show();
                                } catch (Exception e) {
                                    Toast.makeText(context, "Error al procesar respuesta", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(context,
                                    "Fallo de conexión: " + t.getMessage(),
                                    Toast.LENGTH_LONG).show();
                            Log.e("EliminarUsuario", "Error en DELETE", t);
                        }
                    });
                })
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}