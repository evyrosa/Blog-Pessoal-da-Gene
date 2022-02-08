package com.generation.blogpessoal.dto;

public class UsuarioCredenciaisDTO {

	private Long id;
	private String usuario;
	private String token;
	
	public UsuarioCredenciaisDTO(Long id, String usuario, String token) {
		this.id = id;
		this.usuario = usuario;
		this.token = token;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
}
