package com.nelioalves.cursomc.enums;

import java.util.stream.Stream;

public enum TipoCliente {
    PESSOAFISICA(1, "Pessoa Fisica"),
    PESSOAJURIDICA(2,"Pessoa Juridica");

    private Integer cod;
    private String desc;

    private TipoCliente(Integer cod, String desc) {
        this.cod = cod;
        this.desc = desc;
    }

    public Integer getCodigo() {
        return this.cod;
    }

    public String getDescricao() {
        return this.desc;
    }

    public static TipoCliente toEnum(Integer cod) {
        return Stream.of(TipoCliente.values())
            .filter( tipo -> tipo.cod.equals(cod) )
            .findFirst()
            .orElseThrow( () -> new IllegalArgumentException("Id inválido: " + cod));
    }
}