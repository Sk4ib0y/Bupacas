package com.example.bupacas.Endpoints.Service;

import com.example.bupacas.Endpoints.DTO.UsuarioDTO;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List;
public interface UsuarioService
{
    @GET("/Bupacas/api/usuario")
    Call<List<UsuarioDTO>> getAllUsuarios();

    @GET("/Bupacas/api/usuario/{id}")
    Call<UsuarioDTO>getUsuarioById(@Path("id")int id);

    @POST("/Bupacas/api/usuario")
    Call<UsuarioDTO>createUsuario(@Body UsuarioDTO usuario);

    @PUT("/Bupacas/api/usuario/{id}")
    Call<UsuarioDTO>updateUsuario(@Path("id") int id, @Body UsuarioDTO usuario);

    @DELETE("/Bupacas/api/usuario/{id}")
    Call<Void> deleteUsuario(@Path("id") Integer id);

}
