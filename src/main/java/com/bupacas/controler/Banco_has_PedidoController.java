package com.bupacas.controler;

import com.bupacas.dto.Banco_has_PedidoDto;
import com.bupacas.model.Banco_has_Pedido;
import com.bupacas.service.Banco_has_PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class Banco_has_PedidoController {

    private final Banco_has_PedidoService banco_has_PedidoService;

    @RequestMapping("/banco_has_Pedido")
    public ResponseEntity<List<Banco_has_PedidoDto>> lista()    {

        List<Banco_has_Pedido> banco_has_Pedidos = banco_has_PedidoService.getAll( );
        if(banco_has_Pedidos == null || banco_has_Pedidos.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity
                .ok( banco_has_Pedidos
                        .stream()
                        .map( u -> Banco_has_PedidoDto.builder()
                                .id(u.getId())
                                .idBanco(u.getIdBanco().getId())
                                .idPedido(u.getIdPedido().getId())
                                .build())
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/banco_has_Pedido/{id}")
    public ResponseEntity<Banco_has_PedidoDto>getById(@PathVariable Integer id)    {

        Banco_has_Pedido u = banco_has_PedidoService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(Banco_has_PedidoDto.builder()
                .id(u.getId())
                .idBanco(u.getIdBanco().getId())
                .idPedido(u.getIdPedido().getId())
                .build( ) );
    }

    @PostMapping("/banco_has_Pedido")
    public ResponseEntity<Banco_has_PedidoDto> save(@RequestBody Banco_has_PedidoDto banco_has_PedidoDto) {
        Banco_has_Pedido u = Banco_has_Pedido
                .builder()
                .build( );

        banco_has_PedidoService.save( u );

        return ResponseEntity.ok(Banco_has_PedidoDto.builder()
                .id(u.getId())
                .build( ) );
    }

    @DeleteMapping("/banco_has_Pedido/{id}")
    public ResponseEntity<Banco_has_PedidoDto> delete(@PathVariable Integer id)    {
        banco_has_PedidoService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/banco_has_Pedido/{id}")
    public ResponseEntity<Banco_has_PedidoDto>update(@PathVariable Integer id, @RequestBody Banco_has_PedidoDto banco_has_PedidoDto)    {

        Banco_has_Pedido aux = banco_has_PedidoService.update( id, Banco_has_Pedido
                .builder()
                .build( ) );

        return ResponseEntity.ok(Banco_has_PedidoDto.builder()
                .build( ) );
    }
}