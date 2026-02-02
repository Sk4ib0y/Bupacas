package com.bupacas.controler;

import com.bupacas.dto.BancoDto;
import com.bupacas.model.Banco;
import com.bupacas.service.BancoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class BancoController {

    private final BancoService bancoService;

    @RequestMapping("/banco")
    public ResponseEntity<List<BancoDto>> lista()    {

        List<Banco> bancos = bancoService.getAll( );
        if(bancos == null || bancos.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity
                .ok( bancos
                        .stream()
                        .map( u -> BancoDto.builder()
                                .id(u.getId())
                                .cantidad(u.getCantidad())
                                .tipo(u.getTipo())
                                .estado(u.getEstado())
                                .idProv(u.getIdProv().getId())
                                .build())
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/banco/{id}")
    public ResponseEntity<BancoDto>getById(@PathVariable Integer id)    {

        Banco u = bancoService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(BancoDto.builder()
                .id(u.getId())
                .cantidad(u.getCantidad())
                .tipo(u.getTipo())
                .estado(u.getEstado())
                .idProv(u.getIdProv().getId())
                .build( ) );
    }

    @PostMapping("/banco")
    public ResponseEntity<BancoDto> save(@RequestBody BancoDto bancoDto) {
        Banco u = Banco
                .builder()
                .cantidad(bancoDto.getCantidad())
                .tipo(bancoDto.getTipo())
                .estado(bancoDto.getEstado())
                .build( );

        bancoService.save( u );

        return ResponseEntity.ok(BancoDto.builder()
                .id(u.getId())
                .cantidad(u.getCantidad())
                .tipo(u.getTipo())
                .estado(u.getEstado())
                .build( ) );
    }

    @DeleteMapping("/banco/{id}")
    public ResponseEntity<BancoDto> delete(@PathVariable Integer id)    {
        bancoService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/banco/{id}")
    public ResponseEntity<BancoDto>update(@PathVariable Integer id, @RequestBody BancoDto bancoDto)    {

        Banco aux = bancoService.update( id, Banco
                .builder()
                .cantidad(bancoDto.getCantidad())
                .tipo(bancoDto.getTipo())
                .estado(bancoDto.getEstado())
                .build( ) );

        return ResponseEntity.ok(BancoDto.builder()
                .cantidad(aux.getCantidad())
                .tipo(aux.getTipo())
                .estado(aux.getEstado())
                .build( ) );
    }

}