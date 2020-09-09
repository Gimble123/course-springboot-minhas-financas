package com.estertz.minhasfinancas.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estertz.minhasfinancas.exception.RegraNegocioException;
import com.estertz.minhasfinancas.model.entity.Usuario;
import com.estertz.minhasfinancas.model.repository.UsuarioRepository;
import com.estertz.minhasfinancas.service.UsuarioService;


@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	
	private UsuarioRepository respository;
	
	@Autowired
	public UsuarioServiceImpl(UsuarioRepository respository) {
		super();
		this.respository = respository;
	}

	@Override
	public Usuario autenticar(String email, String senha) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario salvarUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarEmail(String email) {
		boolean existe = respository.existsByEmail(email);
		if(existe) {
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
		}
	}

}
