package com.bupacas.controler;

import com.bupacas.dto.ProductoDto;
import com.bupacas.model.Producto;
import com.bupacas.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @RequestMapping("/producto")
    public ResponseEntity<List<ProductoDto>> lista()    {

        List<Producto> productos = productoService.getAll( );
        if(productos == null || productos.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity
                .ok( productos
                        .stream()
                        .map( u -> ProductoDto.builder()
                                .id(u.getId())
                                .cantidad(u.getCantidad())
                                .costoGanancia(u.getCostoGanancia())
                                .empaque(u.getEmpaque())
                                .merma(u.getMerma())
                                .idPedido(u.getIdPedido().getId())
                                .build())
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/producto/{id}")
    public ResponseEntity<ProductoDto>getById(@PathVariable Integer id)    {

        Producto u = productoService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(ProductoDto.builder()
                .id(u.getId())
                .cantidad(u.getCantidad())
                .costoGanancia(u.getCostoGanancia())
                .empaque(u.getEmpaque())
                .merma(u.getMerma())
                .idPedido(u.getIdPedido().getId())
                .build( ) );
    }

    @PostMapping("/producto")
    public ResponseEntity<ProductoDto> save(@RequestBody ProductoDto productoDto) {
        Producto u = Producto
                .builder()
                .cantidad(productoDto.getCantidad())
                .costoGanancia(productoDto.getCostoGanancia())
                .empaque(productoDto.getEmpaque())
                .merma(productoDto.getMerma())
                .build( );

        productoService.save( u );

        return ResponseEntity.ok(ProductoDto.builder()
                .id(u.getId())
                .cantidad(u.getCantidad())
                .costoGanancia(u.getCostoGanancia())
                .empaque(u.getEmpaque())
                .merma(u.getMerma())
                .build( ) );
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity<ProductoDto> delete(@PathVariable Integer id)    {
        productoService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/producto/{id}")
    public ResponseEntity<ProductoDto>update(@PathVariable Integer id, @RequestBody ProductoDto productoDto)    {

        Producto aux = productoService.update( id, Producto
                .builder()
                .cantidad(productoDto.getCantidad())
                .costoGanancia(productoDto.getCostoGanancia())
                .empaque(productoDto.getEmpaque())
                .merma(productoDto.getMerma())
                .build( ) );

        return ResponseEntity.ok(ProductoDto.builder()
                .cantidad(aux.getCantidad())
                .costoGanancia(aux.getCostoGanancia())
                .empaque(aux.getEmpaque())
                .merma(aux.getMerma())
                .build( ) );
    }

}