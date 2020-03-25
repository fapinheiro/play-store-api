package com.nelioalves.cursomc.enums;

import java.util.stream.Stream;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");

	private Integer codigo;
	private String descricao;

	private Perfil(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao () {
		return descricao;
	}

	public static Perfil toEnum(Integer codigo) {

        return Stream.of(Perfil.values())
            .filter( perfil -> perfil.codigo.equals(codigo) )
            .findFirst()
            .orElseThrow( () -> new IllegalArgumentException("Id inválido: " + codigo));

	}

}