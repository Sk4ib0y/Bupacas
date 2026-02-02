package com.bupacas.service.impl;

import com.bupacas.model.Usuario;
import com.bupacas.repository.UsuarioRepository;
import com.bupacas.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService
{
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAll()
    {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getById(Integer id)
    {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario)
    {
        return usuarioRepository.save( usuario );
    }

    @Override
    public void delete(Integer id)
    {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario update(Integer id, Usuario usuario)
    {
        Usuario aux = usuarioRepository.getById( id );
        usuario.setNombre( usuario.getNombre() );
        usuario.setCorreo(usuario.getCorreo());
        usuario.setPassword( usuario.getPassword() );
        usuario.setTipo( usuario.getTipo());
        usuarioRepository.save( aux );
        return aux;
    }
}
