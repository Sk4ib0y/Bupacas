package com.example.bupacas.Endpoints.Service;

import com.example.bupacas.Endpoints.DTO.PedidoDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PedidoService
{
    @GET("/Bupacas/api/pedido")
    Call<List<PedidoDTO>> getAllPedidoes();

    @GET("/Bupacas/api/pedido/{id}")
    Call<PedidoDTO>getPedidoById(@Path("id")int id);

    @POST("/Bupacas/api/pedido")
    Call<PedidoDTO>createPedido(@Body PedidoDTO pedido);

    @PUT("/Bupacas/api/pedido/{id}")
    Call<PedidoDTO>updatePedido(@Path("id") int id, @Body PedidoDTO pedido);

    @DELETE("/Bupacas/api/pedido/{id}")
    Call<Void> deletePedido(@Path("id") Integer id);
}
