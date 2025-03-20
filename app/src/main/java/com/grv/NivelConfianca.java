package com.grv;

public enum NivelConfianca {
    SEM_DADOS(-1, "Sem dados"),
    BAIXO(0, "Baixo"),
    MEDIO(1, "Médio"),
    ALTO(2, "Alto");

    private final int nivel;
    private final String desc;

    NivelConfianca(int nivel, String desc) {
        this.nivel = nivel;
        this.desc = desc;
    }

    public int getNivel() {
        return nivel;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
