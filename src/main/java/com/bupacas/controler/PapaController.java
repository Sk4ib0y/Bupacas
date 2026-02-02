package com.bupacas.controler;

import com.bupacas.dto.PapaDto;
import com.bupacas.model.Cliente;
import com.bupacas.model.Papa;
import com.bupacas.model.Producto;
import com.bupacas.model.Proveedor;
import com.bupacas.service.PapaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class PapaController {

    private final PapaService papaService;

    @RequestMapping("/papa")
    public ResponseEntity<List<PapaDto>> lista()    {

        List<Papa> papas = papaService.getAll( );
        if(papas == null || papas.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity
                .ok( papas
                        .stream()
                        .map( u -> PapaDto.builder()
                                .id(u.getId())
                                .tipo(u.getTipo())
                                .tamaño(u.getTamaño())
                                .variedad(u.getVariedad())
                                .idProveedor(u.getIdProveedor().getId())
                                .idProducto(u.getIdProducto().getId())
                                .build())
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/papa/{id}")
    public ResponseEntity<PapaDto>getById(@PathVariable Integer id)    {

        Papa u = papaService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(PapaDto.builder()
                .id(u.getId())
                .tipo(u.getTipo())
                .tamaño(u.getTamaño())
                .variedad(u.getVariedad())
                .idProveedor(u.getIdProveedor().getId())
                .idProducto(u.getIdProducto().getId())
                .build( ) );
    }

    @PostMapping("/papa")
    public ResponseEntity<PapaDto> save(@RequestBody PapaDto papaDto) {
        Papa u = Papa
                .builder()
                .tipo(papaDto.getTipo())
                .tamaño(papaDto.getTamaño())
                .variedad(papaDto.getVariedad())
                .idProveedor(Proveedor.builder().id( papaDto.getIdProveedor() ).build() )
                .idProducto(Producto.builder().id( papaDto.getIdProducto() ).build() )
                .build( );

        papaService.save( u );

        return ResponseEntity.ok(PapaDto.builder()
                .id(u.getId())
                .tipo(u.getTipo())
                .tamaño(u.getTamaño())
                .variedad(u.getVariedad())
                .idProveedor(u.getIdProveedor().getId())
                .idProducto(u.getIdProducto().getId())
                .build( ) );
    }

    @DeleteMapping("/papa/{id}")
    public ResponseEntity<PapaDto> delete(@PathVariable Integer id)    {
        papaService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/papa/{id}")
    public ResponseEntity<PapaDto>update(@PathVariable Integer id, @RequestBody PapaDto papaDto)    {

        Papa aux = papaService.update( id, Papa
                .builder()
                .tipo(papaDto.getTipo())
                .tamaño(papaDto.getTamaño())
                .variedad(papaDto.getVariedad())
                .build( ) );

        return ResponseEntity.ok(PapaDto.builder()
                .tipo(aux.getTipo())
                .tamaño(aux.getTamaño())
                .variedad(aux.getVariedad())
                .build( ) );
    }

}