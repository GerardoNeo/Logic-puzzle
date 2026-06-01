package com.example.proyectopuzzle_logico;

public class Conexion {

    public Compuerta origen;
    public Compuerta destino;
    public int entradaDestino; // 0 = A, 1 = B

    public Conexion(Compuerta origen, Compuerta destino, int entradaDestino) {
        this.origen = origen;
        this.destino = destino;
        this.entradaDestino = entradaDestino;
    }
}