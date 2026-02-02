package com.bupacas.controler;

import com.bupacas.dto.PedidoDto;
import com.bupacas.model.Cliente;
import com.bupacas.model.Pedido;
import com.bupacas.model.Proveedor;
import com.bupacas.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;

    @RequestMapping("/pedido")
    public ResponseEntity<List<PedidoDto>> lista()    {

        List<Pedido> pedidos = pedidoService.getAll( );
        if(pedidos == null || pedidos.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity
                .ok( pedidos
                        .stream()
                        .map( u -> PedidoDto.builder()
                                .id(u.getId())
                                .codPostal(u.getCodPostal())
                                .destino(u.getDestino())
                                .fecha(u.getFecha())
                                .idProveedor(u.getIdProveedor().getId())
                                .idCliente(u.getIdCliente().getId())
                                .build())
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/pedido/{id}")
    public ResponseEntity<PedidoDto>getById(@PathVariable Integer id)    {

        Pedido u = pedidoService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(PedidoDto.builder()
                .id(u.getId())
                .codPostal(u.getCodPostal())
                .destino(u.getDestino())
                .fecha(u.getFecha())
                .idProveedor(u.getIdProveedor().getId())
                .idCliente(u.getIdCliente().getId())
                .build( ) );
    }

    @PostMapping("/pedido")
    public ResponseEntity<PedidoDto> save(@RequestBody PedidoDto pedidoDto) {
        Pedido u = Pedido
                .builder()
                .codPostal(pedidoDto.getCodPostal())
                .destino(pedidoDto.getDestino())
                .fecha(pedidoDto.getFecha())
                .idProveedor(Proveedor.builder().id( pedidoDto.getIdProveedor() ).build() )
                .idCliente(Cliente.builder().id( pedidoDto.getIdCliente() ).build() )
                .build( );

        pedidoService.save( u );

        return ResponseEntity.ok(PedidoDto.builder()
                .id(u.getId())
                .codPostal(u.getCodPostal())
                .destino(u.getDestino())
                .fecha(u.getFecha())
                .idProveedor(u.getIdProveedor().getId())
                .idCliente(u.getIdCliente().getId())
                .build( ) );
    }

    @DeleteMapping("/pedido/{id}")
    public ResponseEntity<PedidoDto> delete(@PathVariable Integer id)    {
        pedidoService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/pedido/{id}")
    public ResponseEntity<PedidoDto>update(@PathVariable Integer id, @RequestBody PedidoDto pedidoDto)    {

        Pedido aux = pedidoService.update( id, Pedido
                .builder()
                .codPostal(pedidoDto.getCodPostal())
                .destino(pedidoDto.getDestino())
                .fecha(pedidoDto.getFecha())
                .build( ) );

        return ResponseEntity.ok(PedidoDto.builder()
                .codPostal(aux.getCodPostal())
                .destino(aux.getDestino())
                .fecha(aux.getFecha())
                .build( ) );
    }

}