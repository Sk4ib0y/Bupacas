package com.bupacas.service.impl;

import com.bupacas.model.Proveedor;
import com.bupacas.repository.ProveedorRepository;
import com.bupacas.service.ProveedorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProveedorServiceImpl implements ProveedorService
{
    private final ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> getAll()
    {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor getById(Integer id)
    {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public Proveedor save(Proveedor proveedor)
    {
        return proveedorRepository.save(proveedor);
    }

    @Override
    public void delete(Integer id)
    {
        proveedorRepository.deleteById(id);
    }

    @Override
    public Proveedor update(Integer id, Proveedor proveedor) {
        Proveedor aux = proveedorRepository.getById( id );
        proveedor.setRfc( proveedor.getRfc());
        proveedor.setNombre( proveedor.getNombre() );
        proveedor.setEmpresa( proveedor.getEmpresa() );
        proveedor.setZona( proveedor.getZona() );
        proveedorRepository.save( aux );
        return aux;
    }
}
