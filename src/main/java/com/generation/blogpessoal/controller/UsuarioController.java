package com.generation.blogpessoal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blogpessoal.model.UsuarioLogin;

import java.util.Optional;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@PostMapping("/cadastro")
	public ResponseEntity<Usuario> cadastrar (@RequestBody Usuario usuario){
		return ResponseEntity.ok(service.cadastrarUsuario(usuario));
	}
	
	@PostMapping("/login")
	public ResponseEntity<UsuarioLogin> login (@RequestBody Optional<UsuarioLogin> usuarioLogin){
		return service.logar(usuarioLogin).map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}
}
