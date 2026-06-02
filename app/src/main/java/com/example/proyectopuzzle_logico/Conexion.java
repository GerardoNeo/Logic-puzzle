package com.example.proyectopuzzle_logico;

public class Conexion {

    private Compuerta origen;
    private Compuerta destino;

    // 0 = Entrada A
    // 1 = Entrada B
    private int entradaDestino;

    public Conexion(
            Compuerta origen,
            Compuerta destino,
            int entradaDestino
    ) {
        this.origen = origen;
        this.destino = destino;
        this.entradaDestino = entradaDestino;
    }

    public Compuerta getOrigen() {
        return origen;
    }

    public Compuerta getDestino() {
        return destino;
    }

    public int getEntradaDestino() {
        return entradaDestino;
    }
}