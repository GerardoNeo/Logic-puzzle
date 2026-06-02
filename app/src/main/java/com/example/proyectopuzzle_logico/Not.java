package com.example.proyectopuzzle_logico;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Not extends Compuerta {

    private boolean entradaA = false;

    public Not(float x, float y, Paint paint) {
        super(x, y, paint);
    }

    @Override
    protected void actualizarHitbox() {
        hitbox.set(x - 40, y, x + ancho + 70, y + alto);
    }

    @Override
    public void recibirEntrada(int entrada, boolean valor) {
        entradaA = valor;
    }

    @Override
    public boolean calcularSalida() {
        return !entradaA;
    }

    @Override
    public void dibujar(Canvas canvas) {

        canvas.save();
        canvas.rotate(rotacion, x + ancho / 2f, y + alto / 2f);

        Paint p = paint;

        // 🔺 Triángulo del NOT
        Path t = new Path();
        t.moveTo(x, y);
        t.lineTo(x, y + alto);
        t.lineTo(x + ancho, y + alto / 2f);
        t.close();

        canvas.drawPath(t, p);

        // ⚪ Burbuja de negación
        float r = 15;

        float bubbleX = x + ancho + r;
        float centerY = y + alto / 2f;

        canvas.drawCircle(bubbleX, centerY, r, p);

        // 🔌 ENTRADA (izquierda)
        canvas.drawLine(
                x - 40,
                centerY,
                x,
                centerY,
                p
        );

        // 🔌 SALIDA (desde la burbuja, no desde el triángulo)
        canvas.drawLine(
                bubbleX + r,
                centerY,
                bubbleX + r + 40,
                centerY,
                p
        );

        canvas.restore();
    }

    @Override
    public float getEntradaX(int entrada) {
        return x - 40;
    }

    @Override
    public float getEntradaY(int entrada) {
        return y + alto / 2f;
    }

    @Override
    public float getSalidaX() {
        // salida real: después de la burbuja
        return x + ancho + 60;
    }

    @Override
    public float getSalidaY() {
        return y + alto / 2f;
    }

    @Override
    public void resetEntradas() {
        entradaA = false;
    }
}