package com.example.proyectopuzzle_logico;

import android.graphics.Canvas;
import android.graphics.Paint;

public class And extends Compuerta {

    private boolean entradaA = false;
    private boolean entradaB = false;

    public And(float x, float y, Paint paint) {
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
        return entradaA && entradaB;
    }

    @Override
    public void dibujar(Canvas canvas) {

        canvas.save();
        canvas.rotate(rotacion, x + ancho / 2f, y + alto / 2f);

        canvas.drawLine(x - 40, y + alto * 0.25f, x, y + alto * 0.25f, paint);
        canvas.drawLine(x - 40, y + alto * 0.75f, x, y + alto * 0.75f, paint);

        canvas.drawLine(x, y, x, y + alto, paint);
        canvas.drawLine(x, y, x + ancho / 2f, y, paint);
        canvas.drawLine(x, y + alto, x + ancho / 2f, y + alto, paint);

        android.graphics.RectF arco =
                new android.graphics.RectF(x, y, x + ancho, y + alto);

        canvas.drawArc(arco, -90, 180, false, paint);

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