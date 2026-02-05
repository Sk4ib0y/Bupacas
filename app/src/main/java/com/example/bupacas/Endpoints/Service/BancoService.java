package com.example.bupacas.Endpoints.Service;

import com.example.bupacas.Endpoints.DTO.BancoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BancoService 
{
    @GET("/Bupacas/api/banco")
    Call<List<BancoDTO>> getAllBancos();

    @GET("/Bupacas/api/banco/{id}")
    Call<BancoDTO>getBancoById(@Path("id")int id);

    @POST("/Bupacas/api/banco")
    Call<BancoDTO>createBanco(@Body BancoDTO banco);

    @PUT("/Bupacas/api/banco/{id}")
    Call<BancoDTO>updateBanco(@Path("id") int id, @Body BancoDTO banco);

    @DELETE("/Bupacas/api/banco/{id}")
    Call<Void> deleteBanco(@Path("id") Integer id);

}