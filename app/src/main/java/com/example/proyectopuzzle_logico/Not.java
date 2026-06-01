package com.example.proyectopuzzle_logico;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Not extends Compuerta {

    private boolean entradaA = false;

    public Not(float x, float y, Paint paint) {
        super(x, y, paint);
        actualizarHitbox();
    }

    @Override
    protected void actualizarHitbox() {
        hitbox.set(x - 40, y, x + 170, y + alto);
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

        Path t = new Path();
        t.moveTo(x, y);
        t.lineTo(x, y + alto);
        t.lineTo(x + ancho, y + alto / 2f);
        t.close();

        canvas.drawPath(t, paint);

        float r = 15;

        canvas.drawCircle(
                x + ancho + r,
                y + alto / 2f,
                r,
                paint
        );

        canvas.drawLine(x - 40, y + alto / 2f, x, y + alto / 2f, paint);

        canvas.drawLine(
                x + ancho + r * 2,
                y + alto / 2f,
                x + ancho + r * 2 + 40,
                y + alto / 2f,
                paint
        );

        canvas.restore();
    }
}
