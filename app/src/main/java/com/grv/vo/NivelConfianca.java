package com.grv.vo;

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

    public static NivelConfianca fromString(String nome) {
        try {
            return NivelConfianca.valueOf(nome.toUpperCase());
        } catch (IllegalArgumentException e) {
            return SEM_DADOS;
        }
    }

    public static NivelConfianca fromNivel(String nivel) {
        try {
            int nivelNum = Integer.parseInt(nivel);
            for (NivelConfianca n : values()) {
                if (n.nivel == nivelNum) {
                    return n;
                }
            }
        } catch (Exception ignored) {
        }
        return SEM_DADOS;
    }
}
