package com.example.bupacas.Endpoints.Service;

import com.example.bupacas.Endpoints.DTO.ClienteDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClienteService {
    @GET("/Bupacas/api/cliente")
    Call<List<ClienteDTO>> getAllClientees();

    @GET("/Bupacas/api/cliente/{id}")
    Call<ClienteDTO>getClienteById(@Path("id")int id);

    @POST("/Bupacas/api/cliente")
    Call<ClienteDTO>createCliente(@Body ClienteDTO cliente);

    @PUT("/Bupacas/api/cliente/{id}")
    Call<ClienteDTO>updateCliente(@Path("id") int id, @Body ClienteDTO cliente);

    @DELETE("/Bupacas/api/cliente/{id}")
    Call<Void> deleteCliente(@Path("id") Integer id);
}
