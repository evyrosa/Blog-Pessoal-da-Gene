package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.dto.UsuarioCredenciaisDTO;
import com.generation.blogpessoal.dto.UsuarioLoginDTO;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public Usuario cadastrarUsuario(Usuario usuario) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		String senhaCriptografada = encoder.encode(usuario.getSenha());
		usuario.setSenha(senhaCriptografada);
		
		return repository.save(usuario);
	}
	
	public UsuarioCredenciaisDTO logar (UsuarioLoginDTO usuario){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> optional = repository.findByUsuario(usuario.getUsuario());
		
		if(optional.isPresent()) {
			if(encoder.matches(optional.get().getSenha(), usuario.getSenha())) {
				UsuarioCredenciaisDTO credenciaisDTO = new UsuarioCredenciaisDTO(
						optional.get().getId(),
						optional.get().getUsuario(),
						gerarToken(usuario.getUsuario(), optional.get().getSenha())
						);
				return credenciaisDTO;
			} else {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "A senha está incorreta!");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O email não existe no sistema!");
		}
	}

	private String gerarToken(String usuario, String senha) {
		String token = usuario + ":" + senha; // email@email.com:od84yjro28t74yjto87ey
		byte[] tokenCodificado = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		String autenticacao = "Basic " + new String (tokenCodificado); // Basic 9875j3948iydy4rj34yt893j5j
		return autenticacao;
	}
}
