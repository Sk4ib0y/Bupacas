package com.bupacas.controler;

import com.bupacas.dto.TelefonoProvDto;
import com.bupacas.model.TelefonoProv;
import com.bupacas.service.TelefonoProvService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class TelefonoProvController {

    private final TelefonoProvService telefonoProvService;

    @RequestMapping("/telefonoProv")
    public ResponseEntity<List<TelefonoProvDto>> lista()    {

        List<TelefonoProv> telefonoProvs = telefonoProvService.getAll( );
        if(telefonoProvs == null || telefonoProvs.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity
                .ok( telefonoProvs
                        .stream()
                        .map( u -> TelefonoProvDto.builder()
                                .id(u.getId())
                                .telefono(u.getTelefono())
                                .idProv(u.getIdProv().getId())
                                .build())
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/telefonoProv/{id}")
    public ResponseEntity<TelefonoProvDto>getById(@PathVariable Integer id)    {

        TelefonoProv u = telefonoProvService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(TelefonoProvDto.builder()
                .id(u.getId())
                .telefono(u.getTelefono())
                .idProv(u.getIdProv().getId())
                .build( ) );
    }

    @PostMapping("/telefonoProv")
    public ResponseEntity<TelefonoProvDto> save(@RequestBody TelefonoProvDto telefonoProvDto) {
        TelefonoProv u = TelefonoProv
                .builder()
                .telefono(telefonoProvDto.getTelefono())
                .build( );

        telefonoProvService.save( u );

        return ResponseEntity.ok(TelefonoProvDto.builder()
                .id(u.getId())
                .telefono(u.getTelefono())
                .build( ) );
    }

    @DeleteMapping("/telefonoProv/{id}")
    public ResponseEntity<TelefonoProvDto> delete(@PathVariable Integer id)    {
        telefonoProvService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/telefonoProv/{id}")
    public ResponseEntity<TelefonoProvDto>update(@PathVariable Integer id, @RequestBody TelefonoProvDto telefonoProvDto)    {

        TelefonoProv aux = telefonoProvService.update( id, TelefonoProv
                .builder()
                .telefono(telefonoProvDto.getTelefono())
                .build( ) );

        return ResponseEntity.ok(TelefonoProvDto.builder()
                .telefono(aux.getTelefono())
                .build( ) );
    }
}