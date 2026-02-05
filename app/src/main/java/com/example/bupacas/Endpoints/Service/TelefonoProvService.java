package com.example.bupacas.Endpoints.Service;

import com.example.bupacas.Endpoints.DTO.TelefonoProvDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TelefonoProvService
{
    @GET("/Bupacas/api/telefonoProv")
    Call<List<TelefonoProvDTO>> getAllTelefonoProvs();

    @GET("/Bupacas/api/telefonoProv/{id}")
    Call<TelefonoProvDTO>getTelefonoProvById(@Path("id")int id);

    @POST("/Bupacas/api/telefonoProv")
    Call<TelefonoProvDTO>createTelefonoProv(@Body TelefonoProvDTO telefonoprov);

    @PUT("/Bupacas/api/telefonoProv/{id}")
    Call<TelefonoProvDTO>updateTelefonoProv(@Path("id") int id, @Body TelefonoProvDTO telefonoprov);

    @DELETE("/Bupacas/api/telefonoProv/{id}")
    Call<Void> deleteTelefonoProv(@Path("id") Integer id);

}
