package com.generation.blogpessoal.controller;


import java.util.List;
import java.util.Optional;

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

import com.generation.blogpessoal.model.Tema;
import com.generation.blogpessoal.repository.TemaRepository;

@RestController
@RequestMapping("/tema")  //nomedosite.com/tema
@CrossOrigin("*")
public class TemaController {
	
	@Autowired
	private TemaRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> GetAll(){
		return ResponseEntity.ok(repository.findAll()); // Isso:
		
		// E a mesma coisa disso:
		// List<Postagem> postagens = repository.findAll();
		// return ResponseEntity.ok(postagens);
	}
	
	@GetMapping("/{id}")  // Caminho da requisição e variavel
	public ResponseEntity<Tema> GetById(@PathVariable Long id){ 
		Optional<Tema> caixinhaDeTema = repository.findById(id);
		
		if (caixinhaDeTema.isEmpty()) {
			return ResponseEntity.notFound().build();		
		} else {
			Tema temaEncontrado = caixinhaDeTema.get();
			return ResponseEntity.ok(temaEncontrado);
		}
				
		/*
		return repository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build()); 
		*/
	}

	
	@GetMapping("/tema/{descricao}")
	public ResponseEntity<List<Tema>> GetByDescricao(@PathVariable String descricao){
		return ResponseEntity.ok(repository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@PostMapping
	public ResponseEntity<Tema> Post(@RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(tema));
	}
	
	@PutMapping
	public ResponseEntity<Tema> Put(@RequestBody Tema tema){
		return ResponseEntity.status(HttpStatus.OK).body(repository.save(tema));
	}
	
	@DeleteMapping("/{id}")
	public void Delete(@PathVariable Long id) {
		repository.deleteById(id);
	}

}

