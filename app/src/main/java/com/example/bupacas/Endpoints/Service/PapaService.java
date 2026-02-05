package com.example.bupacas.Endpoints.Service;

import com.example.bupacas.Endpoints.DTO.PapaDTO;
import com.example.bupacas.Endpoints.DTO.ProductoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PapaService {
    @GET("/Bupacas/api/papa")
    Call<List<PapaDTO>> getAllPapas();

    @GET("/Bupacas/api/papa/{id}")
    Call<PapaDTO>getPapaById(@Path("id")int id);

    @POST("/Bupacas/api/papa")
    Call<PapaDTO>createPapa(@Body PapaDTO papa);

    @PUT("/Bupacas/api/papa/{id}")
    Call<PapaDTO>updatePapa(@Path("id") int id, @Body PapaDTO papa);

    @DELETE("/Bupacas/api/papa/{id}")
    Call<Void> deletePapa(@Path("id") Integer id);
    @GET("/Bupacas/api/papa/producto/{idProducto}")
    Call<List<PapaDTO>> getPapaByProducto(@Path("idProducto") Integer idProducto);
}