package com.nelioalves.cursomc.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.validation.ClienteUpdate;

import org.hibernate.validator.constraints.Length;

@ClienteUpdate
public class ClienteDTO implements Serializable {
	
	
	/**
	 *
	 */
	private static final long serialVersionUID = 2659343092442695735L;

	private Integer id;

	@NotEmpty(message = "Preenchimento obrigatorio")
	@Length(min = 5, max = 120, message = "Deve estar em 5 e 120 caracteres")
	private String nome;

	@NotEmpty(message = "Preenchimento obrigatorio")
	@Email(message = "Email invalido")
	private String email;

	@NotEmpty(message="Preenchimento obrigatório")
	private String senha;

	public ClienteDTO() {
	}

	public ClienteDTO(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof ClienteDTO)) {
			return false;
		}
		ClienteDTO clienteDTO = (ClienteDTO) o;
		return Objects.equals(id, clienteDTO.id) && Objects.equals(nome, clienteDTO.nome) && Objects.equals(email, clienteDTO.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome, email);
	}


	

}
