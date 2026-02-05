package com.example.bupacas.Endpoints.Retrofit;

import com.example.bupacas.Endpoints.DTO.ProveedorDTO;
import com.example.bupacas.Endpoints.Service.BancoService;
import com.example.bupacas.Endpoints.Service.ClienteService;
import com.example.bupacas.Endpoints.Service.GastoService;
import com.example.bupacas.Endpoints.Service.PapaService;
import com.example.bupacas.Endpoints.Service.PedidoService;
import com.example.bupacas.Endpoints.Service.ProductoService;
import com.example.bupacas.Endpoints.Service.ProveedorService;
import com.example.bupacas.Endpoints.Service.TelefonoClienteService;
import com.example.bupacas.Endpoints.Service.TelefonoProvService;
import com.example.bupacas.Endpoints.Service.UsuarioService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private static final String BASE_URL="http://192.168.100.11:8080/";

    private static Retrofit retrofit=null;

    public static Retrofit getRetrofitInstance()
    {
        if(retrofit==null)
        {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client=new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            retrofit=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static UsuarioService getUsuarioService()
    {
        return getRetrofitInstance().create(UsuarioService.class);
    }

    public static ProveedorService getProveedorService()
    {
        return getRetrofitInstance().create(ProveedorService.class);
    }

    public static ClienteService getClienteService()
    {
        return getRetrofitInstance().create(ClienteService.class);
    }
    public static PedidoService getPedidoService()
    {
        return getRetrofitInstance().create(PedidoService.class);
    }
    public static ProductoService getProductoService()
    {
        return getRetrofitInstance().create(ProductoService.class);
    }

    public static PapaService getPapaService()
    {
        return getRetrofitInstance().create(PapaService.class);
    }

    public static TelefonoProvService getTelefonoProvService()
    {
        return getRetrofitInstance().create(TelefonoProvService.class);
    }

    public static TelefonoClienteService getTelefonoClienteService()
    {
        return getRetrofitInstance().create(TelefonoClienteService.class);
    }

    public static BancoService getBancoService()
    {
        return getRetrofitInstance().create(BancoService.class);
    }

    public static GastoService getGastoService()
    {
        return getRetrofitInstance().create(GastoService.class);
    }
}
