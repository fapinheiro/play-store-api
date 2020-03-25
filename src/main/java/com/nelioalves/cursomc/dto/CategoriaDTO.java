package com.nelioalves.cursomc.dto;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;

import com.nelioalves.cursomc.domain.Categoria;

import org.hibernate.validator.constraints.Length;


public class CategoriaDTO implements Serializable {
	
	
	/**
	 *
	 */
	private static final long serialVersionUID = 2902432479116556558L;

	
	private Integer id;

	@NotEmpty(message = "Preenchimento obrigatorio")
	@Length(min = 8, max = 45, message = "Deve estar em 8 e 45 caracteres")
	private String nome;

	public CategoriaDTO() {
	}

	public CategoriaDTO(Categoria cat) {
		this.id = cat.getId();
		this.nome = cat.getNome();
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


	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof CategoriaDTO)) {
			return false;
		}
		CategoriaDTO categoriaDTO = (CategoriaDTO) o;
		return Objects.equals(id, categoriaDTO.id) && Objects.equals(nome, categoriaDTO.nome);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nome);
	}

}
