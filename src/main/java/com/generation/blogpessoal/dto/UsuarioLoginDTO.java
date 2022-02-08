package com.generation.blogpessoal.dto;

import javax.validation.constraints.NotBlank;

public class UsuarioLoginDTO {

	@NotBlank
	private String usuario;
	
	@NotBlank
	private String senha;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
