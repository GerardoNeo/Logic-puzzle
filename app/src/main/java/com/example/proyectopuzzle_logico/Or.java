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
        canvas.rotate(rotacion, x + ancho / 2f, y + alto / 2f);

        Path p = new Path();

        p.moveTo(x, y);
        p.quadTo(x + ancho * 0.25f, y + alto / 2f, x, y + alto);
        p.quadTo(x + ancho * 0.5f, y + alto, x + ancho, y + alto / 2f);
        p.quadTo(x + ancho * 0.5f, y, x, y);
        p.close();

        canvas.drawPath(p, paint);

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