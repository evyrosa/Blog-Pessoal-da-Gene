package com.generation.blogpessoal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.UsuarioLogin;
import com.generation.blogpessoal.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.generation.blogpessoal.model.Usuario;
import com.generation.blogpessoal.service.UsuarioService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioRepository repository;
	
	@PostMapping("/cadastro")
	public ResponseEntity<Usuario> cadastrar (@RequestBody Usuario usuario){
		return ResponseEntity.ok(service.cadastrarUsuario(usuario));
	}
	
	@PostMapping("/login")
	public ResponseEntity<UsuarioLogin> login (@RequestBody Optional<UsuarioLogin> usuarioLogin){
		return service.logar(usuarioLogin).map(resp -> ResponseEntity.ok(resp))
			.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@GetMapping
	public ResponseEntity<List<Usuario>> todosUsuarios(){
		List<Usuario> usuarios = repository.findAll();

		if(usuarios.isEmpty()){
			return ResponseEntity.status(204).build();
		} else {
			return ResponseEntity.status(200).body(usuarios);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
		Optional<Usuario> opt = repository.findById(id);

		if (opt.isPresent()){
			return ResponseEntity.status(200).body(opt.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID inexistente na base de dados");
		}
	}

	@PutMapping
	public ResponseEntity<Usuario> atualizar (@Valid @RequestBody Usuario alterado){
		return service.atualizarUsuario(alterado)
		.map(resp -> ResponseEntity.ok(resp))
		.orElse(ResponseEntity.notFound().build());
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	public ResponseEntity deletar(@PathVariable Long id){
		Optional<Usuario> optional = repository.findById(id);
		
		if(optional.isPresent()){
			repository.deleteById(id);
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
