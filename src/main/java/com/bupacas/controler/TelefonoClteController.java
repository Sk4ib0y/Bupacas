package com.bupacas.controler;

import com.bupacas.dto.TelefonoClteDto;
import com.bupacas.model.TelefonoClte;
import com.bupacas.service.TelefonoClteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class TelefonoClteController {

    private final TelefonoClteService telefonoClteService;

    @RequestMapping("/telefonoClte")
    public ResponseEntity<List<TelefonoClteDto>> lista()    {

        List<TelefonoClte> telefonoCltes = telefonoClteService.getAll( );
        if(telefonoCltes == null || telefonoCltes.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity
                .ok( telefonoCltes
                        .stream()
                        .map( u -> TelefonoClteDto.builder()
                                .id(u.getId())
                                .telefono(u.getTelefono())
                                .idClte(u.getIdClte().getId())
                                .build())
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/telefonoClte/{id}")
    public ResponseEntity<TelefonoClteDto>getById(@PathVariable Integer id)    {

        TelefonoClte u = telefonoClteService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(TelefonoClteDto.builder()
                .id(u.getId())
                .telefono(u.getTelefono())
                .idClte(u.getIdClte().getId())
                .build( ) );
    }

    @PostMapping("/telefonoClte")
    public ResponseEntity<TelefonoClteDto> save(@RequestBody TelefonoClteDto telefonoClteDto) {
        TelefonoClte u = TelefonoClte
                .builder()
                .telefono(telefonoClteDto.getTelefono())
                .build( );

        telefonoClteService.save( u );

        return ResponseEntity.ok(TelefonoClteDto.builder()
                .id(u.getId())
                .telefono(u.getTelefono())
                .build( ) );
    }

    @DeleteMapping("/telefonoClte/{id}")
    public ResponseEntity<TelefonoClteDto> delete(@PathVariable Integer id)    {
        telefonoClteService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/telefonoClte/{id}")
    public ResponseEntity<TelefonoClteDto>update(@PathVariable Integer id, @RequestBody TelefonoClteDto telefonoClteDto)    {

        TelefonoClte aux = telefonoClteService.update( id, TelefonoClte
                .builder()
                .telefono(telefonoClteDto.getTelefono())
                .build( ) );

        return ResponseEntity.ok(TelefonoClteDto.builder()
                .telefono(aux.getTelefono())
                .build( ) );
    }
}