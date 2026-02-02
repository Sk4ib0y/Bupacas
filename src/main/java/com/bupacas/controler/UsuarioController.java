package com.bupacas.controler;

import com.bupacas.dto.UsuarioDto;
import com.bupacas.model.Usuario;
import com.bupacas.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Bupacas/api")
@RestController
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private List<UsuarioDto> usuarioDtos;

    public void loadList() {
        usuarioDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            usuarioDtos.add(
                    UsuarioDto.builder()
                            .id(i++)
                            .nombre("Nombre " + i)
                            .correo("Correo ")
                            .password("Password " + i)
                            .tipo("Tipo " + i)
                            .build()
            );
        }
    }

    @RequestMapping("/usuario")
    public ResponseEntity<List<UsuarioDto>> lista(@RequestParam (name = "nombre", defaultValue = "", required = false) String nombre)    {

        List<Usuario> usuarios = usuarioService.getAll( );
        if(usuarios == null || usuarios.size()== 0) {
            return ResponseEntity.notFound( ).build( );
        }
        if( nombre != null && !nombre.isEmpty()) {
            return ResponseEntity
                    .ok( usuarios
                            .stream()
                            .filter(u -> u.getNombre().equals(nombre) )
                            .map( u -> UsuarioDto.builder()
                                    .id(u.getId())
                                    .nombre(u.getNombre())
                                    .correo(u.getCorreo())
                                    .password(u.getPassword())
                                    .tipo(u.getTipo())
                                    .build())
                            .collect(Collectors.toList()));
        }

        return ResponseEntity
                .ok( usuarios
                        .stream()
                        .map(u -> UsuarioDto.builder()
                                .id(u.getId())
                                .nombre(u.getNombre())
                                .correo(u.getCorreo())
                                .password(u.getPassword())
                                .tipo(u.getTipo())
                                .build() )
                        .collect(Collectors.toList()));
    }

    @RequestMapping("/usuario/{id}")
    public ResponseEntity<UsuarioDto>getById(@PathVariable Integer id)    {

        Usuario u = usuarioService.getById( id );
        if(u == null ) {
            return  ResponseEntity.notFound( ).build( );
        }
        return ResponseEntity.ok(UsuarioDto.builder()
                .id( u.getId())
                .nombre(u.getNombre())
                .correo(u.getCorreo())
                .password(u.getPassword())
                .tipo(u.getTipo())
                .build( ) );
    }

    @PostMapping("/usuario")
    public ResponseEntity<UsuarioDto> save(@RequestBody UsuarioDto usuarioDto) {

        Usuario u = Usuario
                .builder()
                .nombre( usuarioDto.getNombre() )
                .correo( usuarioDto.getCorreo())
                .password( usuarioDto.getPassword())
                .tipo( usuarioDto.getTipo())
                .build( );

        usuarioService.save( u );

        return ResponseEntity.ok(UsuarioDto.builder()
                .id( u.getId())
                .nombre(u.getNombre())
                .correo(u.getCorreo())
                .password(u.getPassword())
                .tipo(u.getTipo())
                .build( ) );
    }

    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<UsuarioDto> delete(@PathVariable Integer id)    {
        usuarioService.delete( id );
        return ResponseEntity.noContent( ).build( );
    }

    @PutMapping("/usuario/{id}")
    public ResponseEntity<UsuarioDto>update( @PathVariable Integer id, @RequestBody UsuarioDto usuarioDto)    {

        Usuario aux = usuarioService.update( id, Usuario
                .builder()
                .nombre( usuarioDto.getNombre() )
                .correo( usuarioDto.getCorreo())
                .password( usuarioDto.getPassword())
                .tipo( usuarioDto.getTipo())
                .build( ) );

        return ResponseEntity.ok(UsuarioDto.builder()
                .id( aux.getId( ) )
                .nombre( aux.getNombre( ) )
                .correo(aux.getCorreo())
                .password( aux.getPassword( ) )
                .tipo(aux.getTipo())
                .build( ) );
    }

}