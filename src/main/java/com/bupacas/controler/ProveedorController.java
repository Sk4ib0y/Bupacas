package com.bupacas.controler;

import com.bupacas.dto.ProveedorDto;
import com.bupacas.model.Proveedor;
import com.bupacas.service.ProveedorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class ProveedorController {

    private final ProveedorService proveedorService;
    private List<ProveedorDto> proveedorDtos;

    public void loadList() {
        proveedorDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            proveedorDtos.add(
                    ProveedorDto.builder()
                            .id(i++)
                            .rfc("Rfc " + i)
                            .nombre("Nombre " + i)
                            .empresa("Empresa " + i)
                            .zona("Zona " + i)
                            .build()
            );
        }
    }

    @RequestMapping("/proveedor")
    public ResponseEntity<List<ProveedorDto>> lista(@RequestParam (name = "rfc", defaultValue = "", required = false) String rfc)    {

        List<Proveedor> proveedores = proveedorService.getAll( );
        if(proveedores == null || proveedores.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        if( rfc != null && !rfc.isEmpty()) {
            return ResponseEntity
                    .ok( proveedores
                            .stream()
                            .filter(u -> u.getRfc().equals(rfc) )
                            .map( u -> ProveedorDto.builder()
                                    .id(u.getId())
                                    .rfc(u.getRfc())
                                    .nombre(u.getNombre())
                                    .empresa(u.getEmpresa())
                                    .zona(u.getZona())
                                    .build())
                            .collect(Collectors.toList()));
        }

        return ResponseEntity
                .ok( proveedores
                        .stream()
                        .map(u -> ProveedorDto.builder()
                                .id(u.getId())
                                .rfc(u.getRfc())
                                .nombre(u.getNombre())
                                .empresa(u.getEmpresa())
                                .zona(u.getZona())
                                .build() )
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/proveedor/{id}")
    public ResponseEntity<ProveedorDto>getById(@PathVariable Integer id)    {

        Proveedor u = proveedorService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(ProveedorDto.builder()
                .id(u.getId())
                .rfc(u.getRfc())
                .nombre(u.getNombre())
                .empresa(u.getEmpresa())
                .zona(u.getZona())
                .build( ) );
    }

    @PostMapping("/proveedor")
    public ResponseEntity<ProveedorDto> save(@RequestBody ProveedorDto proveedorDto) {

        Proveedor u = Proveedor
                .builder()
                .rfc( proveedorDto.getRfc())
                .nombre( proveedorDto.getNombre() )
                .empresa( proveedorDto.getEmpresa())
                .zona( proveedorDto.getZona() )
                .build();

        proveedorService.save( u );

        return ResponseEntity.ok(ProveedorDto.builder()
                .id(u.getId())
                .rfc(u.getRfc())
                .nombre(u.getNombre())
                .empresa(u.getEmpresa())
                .zona(u.getZona())
                .build( ) );
    }

    @DeleteMapping("/proveedor/{id}")
    public ResponseEntity<ProveedorDto> delete(@PathVariable Integer id)    {
        proveedorService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/proveedor/{id}")
    public ResponseEntity<ProveedorDto>update(@PathVariable Integer id, @RequestBody ProveedorDto proveedorDto)    {

        Proveedor aux = proveedorService.update( id, Proveedor
                .builder()
                .rfc( proveedorDto.getRfc())
                .nombre( proveedorDto.getNombre() )
                .empresa( proveedorDto.getEmpresa())
                .zona( proveedorDto.getZona() )
                .build( ) );

        return ResponseEntity.ok(ProveedorDto.builder()
                .id(aux.getId())
                .rfc(aux.getRfc())
                .nombre(aux.getNombre())
                .empresa(aux.getEmpresa())
                .zona(aux.getZona())
                .build( ) );
    }

}