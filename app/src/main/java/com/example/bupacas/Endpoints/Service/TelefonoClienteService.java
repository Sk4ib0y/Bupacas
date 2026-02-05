package com.example.bupacas.Endpoints.Service;

import com.example.bupacas.Endpoints.DTO.TelefonoClienteDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TelefonoClienteService
{
    @GET("/Bupacas/api/telefonoClte")
    Call<List<TelefonoClienteDTO>> getAllTelefonoCltes();

    @GET("/Bupacas/api/telefonoClte/{id}")
    Call<TelefonoClienteDTO>getTelefonoClteById(@Path("id")int id);

    @POST("/Bupacas/api/telefonoClte")
    Call<TelefonoClienteDTO>createTelefonoClte(@Body TelefonoClienteDTO telefonoclte);

    @PUT("/Bupacas/api/telefonoClte/{id}")
    Call<TelefonoClienteDTO>updateTelefonoClte(@Path("id") int id, @Body TelefonoClienteDTO telefonoclte);

    @DELETE("/Bupacas/api/telefonoClte/{id}")
    Call<Void> deleteTelefonoClte(@Path("id") Integer id);

}
