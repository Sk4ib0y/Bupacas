package com.bupacas.controler;

import com.bupacas.dto.GastoDto;
import com.bupacas.model.Gasto;
import com.bupacas.service.GastoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class GastoController {

    private final GastoService gastoService;

    @RequestMapping("/gasto")
    public ResponseEntity<List<GastoDto>> lista()    {

        List<Gasto> gastos = gastoService.getAll( );
        if(gastos == null || gastos.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity
                .ok( gastos
                        .stream()
                        .map( u -> GastoDto.builder()
                                .id(u.getId())
                                .cantidad(u.getCantidad())
                                .tipo(u.getTipo())
                                .idBanco(u.getIdBanco().getId())
                                .build())
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/gasto/{id}")
    public ResponseEntity<GastoDto>getById(@PathVariable Integer id)    {

        Gasto u = gastoService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(GastoDto.builder()
                .id(u.getId())
                .cantidad(u.getCantidad())
                .tipo(u.getTipo())
                .idBanco(u.getIdBanco().getId())
                .build( ) );
    }

    @PostMapping("/gasto")
    public ResponseEntity<GastoDto> save(@RequestBody GastoDto gastoDto) {
        Gasto u = Gasto
                .builder()
                .cantidad(gastoDto.getCantidad())
                .tipo(gastoDto.getTipo())
                .build( );

        gastoService.save( u );

        return ResponseEntity.ok(GastoDto.builder()
                .id(u.getId())
                .cantidad(u.getCantidad())
                .tipo(u.getTipo())
                .build( ) );
    }

    @DeleteMapping("/gasto/{id}")
    public ResponseEntity<GastoDto> delete(@PathVariable Integer id)    {
        gastoService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/gasto/{id}")
    public ResponseEntity<GastoDto>update(@PathVariable Integer id, @RequestBody GastoDto gastoDto)    {

        Gasto aux = gastoService.update( id, Gasto
                .builder()
                .cantidad(gastoDto.getCantidad())
                .tipo(gastoDto.getTipo())
                .build( ) );

        return ResponseEntity.ok(GastoDto.builder()
                .cantidad(aux.getCantidad())
                .tipo(aux.getTipo())
                .build( ) );
    }

}