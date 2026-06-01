package com.example.proyectopuzzle_logico;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import java.util.ArrayList;

public abstract class Compuerta {

    protected float x;
    protected float y;
    protected Paint paint;
    protected RectF hitbox;

    protected int ancho = 100;
    protected int alto = 100;

    protected float rotacion = 0;

    protected ArrayList<Conexion> salidas = new ArrayList<>();

    public Compuerta(float x, float y, Paint paint) {
        this.x = x;
        this.y = y;
        this.paint = paint;
        this.hitbox = new RectF();
    }

    // 🔥 CONEXIÓN
    public void agregarConexion(Compuerta destino, int entrada) {
        salidas.add(new Conexion(this, destino, entrada));
    }

    // 🔥 PROPAGACIÓN DE SEÑAL
    public void actualizar() {

        boolean salidaActual = calcularSalida();

        for (Conexion c : salidas) {
            c.destino.recibirEntrada(c.entradaDestino, salidaActual);
        }
    }

    public void rotar() {
        rotacion += 90;
        if (rotacion >= 360) rotacion = 0;
    }

    protected abstract void actualizarHitbox();

    public abstract boolean calcularSalida();

    public abstract void dibujar(Canvas canvas);

    public abstract void recibirEntrada(int entrada, boolean valor);

    public boolean contiene(float touchX, float touchY) {
        return hitbox.contains(touchX, touchY);
    }

    public void mover(float nuevoX, float nuevoY) {
        x = nuevoX;
        y = nuevoY;
        actualizarHitbox();
    }

    public float getX() { return x; }
    public float getY() { return y; }
}