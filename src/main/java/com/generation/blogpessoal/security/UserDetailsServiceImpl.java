package com.generation.blogpessoal.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;

	@Override
	public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> optional = repository.findByUsuario(email);
		
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException("Usuário com email "+ email + "não encontrado!");
		} else {
			return new UserDetailsImpl(optional.get());
		}
	
	}

}
