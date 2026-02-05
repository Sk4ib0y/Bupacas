package com.example.bupacas.Endpoints.Service;

import com.example.bupacas.Endpoints.DTO.GastoDTO;
import com.example.bupacas.Endpoints.DTO.ProductoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GastoService
{
    @GET("/Bupacas/api/gasto")
    Call<List<GastoDTO>> getAllGastos();

    @GET("/Bupacas/api/gasto/{id}")
    Call<GastoDTO>getGastoById(@Path("id")int id);

    @POST("/Bupacas/api/gasto")
    Call<GastoDTO>createGasto(@Body GastoDTO gasto);

    @PUT("/Bupacas/api/gasto/{id}")
    Call<GastoDTO>updateGasto(@Path("id") int id, @Body GastoDTO gasto);

    @DELETE("/Bupacas/api/gasto/{id}")
    Call<Void> deleteGasto(@Path("id") Integer id);

    @GET("/Bupacas/api/gasto/banco/{idBanco}")
    Call<List<GastoDTO>> getGastosByBanco(@Path("idBanco") Integer idBanco);
}
