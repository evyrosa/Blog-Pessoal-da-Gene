package com.generation.blogpessoal.service;

import java.nio.charset.Charset;
import java.util.Optional;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	private static String criptografarSenha(String senha){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(senha);
	}
	
	public Usuario cadastrarUsuario(Usuario usuario) {
		
		usuario.setSenha(criptografarSenha(usuario.getSenha()));
		
		return repository.save(usuario);
	}
	
	public Optional<UsuarioLogin> logar (Optional<UsuarioLogin> usuario){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<Usuario> optional = repository.findByUsuario(usuario.get().getUsuario());
		
		if(optional.isPresent()) {
			if(encoder.matches(usuario.get().getSenha(), optional.get().getSenha())) {

				usuario.get().setId(optional.get().getId());
				usuario.get().setNome(optional.get().getNome());
				usuario.get().setToken(gerarToken(usuario.get().getUsuario(), usuario.get().getSenha()));
				usuario.get().setFoto(optional.get().getFoto());
				usuario.get().setTipo(optional.get().getTipo());
				
				return usuario;
			} 
		} 
		return null;
	}

	private String gerarToken(String usuario, String senha) {
		String token = usuario + ":" + senha; // totoro@totoro.com:senhadototoro
		byte[] tokenCodificado = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII")));
		String autenticacao = "Basic " + new String (tokenCodificado); // Basic 9875j3948iydy4rj34yt893j5j
		return autenticacao;
	}

	public Optional<Usuario> atualizarUsuario (Usuario usuarioAlterado){
		Optional<Usuario> optional = repository.findById(usuarioAlterado.getId());

		if (optional.isPresent()){
			usuarioAlterado.setSenha(criptografarSenha(usuarioAlterado.getSenha()));
			return Optional.ofNullable(repository.save(usuarioAlterado));
		} else {
			return Optional.empty();
		}
	}

}
