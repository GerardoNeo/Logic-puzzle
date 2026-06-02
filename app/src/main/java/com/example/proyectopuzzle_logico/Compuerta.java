package com.example.proyectopuzzle_logico;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

public abstract class Compuerta {

    protected float x, y;
    protected Paint paint;
    protected RectF hitbox;

    protected static int contador = 1;
    protected String nombre;

    protected int ancho = 100;
    protected int alto = 100;

    protected float rotacion = 0;

    protected ArrayList<Conexion> salidas = new ArrayList<>();

    public Compuerta(float x, float y, Paint paint) {
        this.x = x;
        this.y = y;
        this.paint = paint;

        hitbox = new RectF();

        nombre = getClass().getSimpleName() + contador++;

        actualizarHitbox();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Conexion> getSalidas() {
        return salidas;
    }

    public void agregarConexion(Compuerta destino, int entrada) {
        salidas.add(new Conexion(this, destino, entrada));
    }

    public void actualizar() {
        boolean salida = calcularSalida();

        for (Conexion c : salidas) {
            c.getDestino().recibirEntrada(
                    c.getEntradaDestino(),
                    salida
            );
        }
    }

    public void rotar() {
        rotacion += 90;
        if (rotacion >= 360) rotacion = 0;
    }

    public boolean contiene(float tx, float ty) {
        return hitbox.contains(tx, ty);
    }

    public void mover(float nx, float ny) {
        x = nx;
        y = ny;
        actualizarHitbox();
    }

    public float getX() { return x; }
    public float getY() { return y; }

    public float getSalidaX() { return x + ancho + 40; }
    public float getSalidaY() { return y + alto / 2f; }

    public float getEntradaX(int entrada) {
        return x - 40;
    }

    public float getEntradaY(int entrada) {
        return (entrada == 0)
                ? y + alto * 0.25f
                : y + alto * 0.75f;
    }

    protected abstract void actualizarHitbox();
    public abstract void dibujar(Canvas canvas);
    public abstract boolean calcularSalida();
    public abstract void recibirEntrada(int entrada, boolean valor);
    public void resetEntradas() {
        // default: no hace nada
    }
}