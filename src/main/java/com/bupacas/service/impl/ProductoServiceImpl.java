package com.bupacas.service.impl;

import com.bupacas.model.Producto;
import com.bupacas.repository.ProductoRepository;
import com.bupacas.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService
{
    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> getAll()
    {
        return productoRepository.findAll();
    }

    @Override
    public Producto getById(Integer id)
    {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto save(Producto producto)
    {
        return productoRepository.save(producto);
    }

    @Override
    public void delete(Integer id)
    {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto update(Integer id, Producto producto)
    {
        Producto aux = productoRepository.getById( id );
        producto.setCantidad(producto.getCantidad());
        producto.setCostoGanancia(producto.getCostoGanancia());
        producto.setEmpaque(producto.getEmpaque());
        producto.setMerma(producto.getMerma());
        productoRepository.save( aux );
        return aux;
    }
}
