package com.example.proyectopuzzle_logico;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Or extends Compuerta {

    private boolean entradaA = false;
    private boolean entradaB = false;

    public Or(float x, float y, Paint paint) {
        super(x, y, paint);
    }

    @Override
    protected void actualizarHitbox() {
        hitbox.set(x - 40, y, x + ancho + 40, y + alto);
    }

    @Override
    public void recibirEntrada(int entrada, boolean valor) {
        if (entrada == 0) entradaA = valor;
        else if (entrada == 1) entradaB = valor;
    }

    @Override
    public boolean calcularSalida() {
        return entradaA || entradaB;
    }

    @Override
    public void dibujar(Canvas canvas) {

        canvas.save();
        canvas.rotate(rotacion, x + ancho / 2f, y + alto / 2f);

        Paint p = paint;

        canvas.drawLine(x - 40, y + alto * 0.25f, x + 5, y + alto * 0.25f, p);
        canvas.drawLine(x - 40, y + alto * 0.75f, x + 5, y + alto * 0.75f, p);

        Path path = new Path();
        path.moveTo(x, y);
        path.quadTo(x + ancho * 0.25f, y + alto / 2f, x, y + alto);
        path.quadTo(x + ancho * 0.5f, y + alto, x + ancho, y + alto / 2f);
        path.quadTo(x + ancho * 0.5f, y, x, y);
        path.close();

        canvas.drawPath(path, p);

        canvas.drawLine(
                x + ancho,
                y + alto / 2f,
                x + ancho + 40,
                y + alto / 2f,
                p
        );

        canvas.restore();
    }
    @Override
    public void resetEntradas() {
        entradaA = false;
        entradaB = false;
    }
}