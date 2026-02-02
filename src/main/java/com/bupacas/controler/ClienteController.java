package com.bupacas.controler;

import com.bupacas.dto.ClienteDto;
import com.bupacas.model.Cliente;
import com.bupacas.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private List<ClienteDto> clienteDtos;

    public void loadList() {
        clienteDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            clienteDtos.add(
                    ClienteDto.builder()
                            .id(i++)
                            .rfc("Rfc " + i)
                            .nombre("Nombre " + i)
                            .empresa("Empresa " + i)
                            .zona("Zona " + i)
                            .build()
            );
        }
    }

    @RequestMapping("/cliente")
    public ResponseEntity<List<ClienteDto>> lista(@RequestParam (name = "rfc", defaultValue = "", required = false) String rfc)    {

        List<Cliente> clientees = clienteService.getAll( );
        if(clientees == null || clientees.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        if( rfc != null && !rfc.isEmpty()) {
            return ResponseEntity
                    .ok( clientees
                            .stream()
                            .filter(u -> u.getRfc().equals(rfc) )
                            .map( u -> ClienteDto.builder()
                                    .id(u.getId())
                                    .rfc(u.getRfc())
                                    .nombre(u.getNombre())
                                    .empresa(u.getEmpresa())
                                    .zona(u.getZona())
                                    .build())
                            .collect(Collectors.toList()));
        }

        return ResponseEntity
                .ok( clientees
                        .stream()
                        .map(u -> ClienteDto.builder()
                                .id(u.getId())
                                .rfc(u.getRfc())
                                .nombre(u.getNombre())
                                .empresa(u.getEmpresa())
                                .zona(u.getZona())
                                .build() )
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/cliente/{id}")
    public ResponseEntity<ClienteDto>getById(@PathVariable Integer id)    {

        Cliente u = clienteService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(ClienteDto.builder()
                .id(u.getId())
                .rfc(u.getRfc())
                .nombre(u.getNombre())
                .empresa(u.getEmpresa())
                .zona(u.getZona())
                .build( ) );
    }

    @PostMapping("/cliente")
    public ResponseEntity<ClienteDto> save(@RequestBody ClienteDto clienteDto) {

        Cliente u = Cliente
                .builder()
                .rfc( clienteDto.getRfc())
                .nombre( clienteDto.getNombre() )
                .empresa( clienteDto.getEmpresa())
                .zona( clienteDto.getZona() )
                .build();

        clienteService.save( u );

        return ResponseEntity.ok(ClienteDto.builder()
                .id(u.getId())
                .rfc(u.getRfc())
                .nombre(u.getNombre())
                .empresa(u.getEmpresa())
                .zona(u.getZona())
                .build( ) );
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<ClienteDto> delete(@PathVariable Integer id)    {
        clienteService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/cliente/{id}")
    public ResponseEntity<ClienteDto>update(@PathVariable Integer id, @RequestBody ClienteDto clienteDto)    {

        Cliente aux = clienteService.update( id, Cliente
                .builder()
                .rfc( clienteDto.getRfc())
                .nombre( clienteDto.getNombre() )
                .empresa( clienteDto.getEmpresa())
                .zona( clienteDto.getZona() )
                .build( ) );

        return ResponseEntity.ok(ClienteDto.builder()
                .id(aux.getId())
                .rfc(aux.getRfc())
                .nombre(aux.getNombre())
                .empresa(aux.getEmpresa())
                .zona(aux.getZona())
                .build( ) );
    }

}