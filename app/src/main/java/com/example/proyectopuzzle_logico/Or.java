package com.example.proyectopuzzle_logico;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Or extends Compuerta {

    private boolean entradaA = false;
    private boolean entradaB = false;

    public Or(float x, float y, Paint paint) {
        super(x, y, paint);
        actualizarHitbox();
    }

    @Override
    protected void actualizarHitbox() {
        hitbox.set(x - 40, y, x + ancho + 40, y + alto);
    }

    @Override
    public void recibirEntrada(int entrada, boolean valor) {
        if (entrada == 0) entradaA = valor;
        if (entrada == 1) entradaB = valor;
    }

    @Override
    public boolean calcularSalida() {
        return entradaA || entradaB;
    }

    @Override
    public void dibujar(Canvas canvas) {

        canvas.save();
        // Rotamos el lienzo tomando como eje el centro exacto de la compuerta
        canvas.rotate(rotacion, x + ancho / 2f, y + alto / 2f);

        // 1. Entradas (Se dibujan dentro para que roten con la compuerta)
        canvas.drawLine(
                x - 40,
                y + alto * 0.25f,
                x + 5, // Entra un poco en la curva para evitar huecos visuales
                y + alto * 0.25f,
                paint
        );

        canvas.drawLine(
                x - 40,
                y + alto * 0.75f,
                x + 5,
                y + alto * 0.75f,
                paint
        );

        // 2. Cuerpo de la compuerta OR
        Path p = new Path();
        p.moveTo(x, y);

        // Curva trasera (cóncava)
        p.quadTo(x + ancho * 0.25f, y + alto / 2f, x, y + alto);
        // Curva inferior hacia la punta
        p.quadTo(x + ancho * 0.5f, y + alto, x + ancho, y + alto / 2f);
        // Curva superior de regreso al origen
        p.quadTo(x + ancho * 0.5f, y, x, y);
        p.close();

        canvas.drawPath(p, paint);

        // 3. Salida (También dentro del bloque de rotación)
        canvas.drawLine(
                x + ancho,
                y + alto / 2f,
                x + ancho + 40,
                y + alto / 2f,
                paint
        );

        canvas.restore();
    }
}