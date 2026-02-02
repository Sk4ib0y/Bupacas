package com.example.bupacas.Endpoints.Service;

import com.example.bupacas.Endpoints.DTO.ProveedorDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List;
public interface ProveedorService
{
    @GET("/Bupacas/api/proveedor")
    Call<List<ProveedorDTO>> getAllProveedores();

    @GET("/Bupacas/api/proveedor/{id}")
    Call<ProveedorDTO>getProveedorById(@Path("id")int id);

    @POST("/Bupacas/api/proveedor")
    Call<ProveedorDTO>createProveedor(@Body ProveedorDTO proveedor);

    @PUT("/Bupacas/api/proveedor/{id}")
    Call<ProveedorDTO>updateProveedor(@Path("id") int id, @Body ProveedorDTO proveedor);

    @DELETE("/Bupacas/api/proveedor/{id}")
    Call<Void> deleteProveedor(@Path("id") Integer id);

}