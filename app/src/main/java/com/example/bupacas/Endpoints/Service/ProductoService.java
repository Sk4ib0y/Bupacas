package com.example.bupacas.Endpoints.Service;

import com.example.bupacas.Endpoints.DTO.ProductoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ProductoService 
{
    @GET("/Bupacas/api/producto")
    Call<List<ProductoDTO>> getAllProductos();

    @GET("/Bupacas/api/producto/{id}")
    Call<ProductoDTO>getProductoById(@Path("id")int id);

    @POST("/Bupacas/api/producto")
    Call<ProductoDTO>createProducto(@Body ProductoDTO producto);

    @PUT("/Bupacas/api/producto/{id}")
    Call<ProductoDTO>updateProducto(@Path("id") int id, @Body ProductoDTO producto);

    @DELETE("/Bupacas/api/producto/{id}")
    Call<Void> deleteProducto(@Path("id") Integer id);

    @GET("/Bupacas/api/producto/pedido/{idPedido}")
    Call<List<ProductoDTO>> getProductosByPedido(@Path("idPedido") Integer idPedido);}
