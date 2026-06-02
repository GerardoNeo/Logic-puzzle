package com.example.proyectopuzzle_logico;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Led extends Compuerta {

    private boolean entrada = false;

    public Led(float x, float y, Paint paint) {
        super(x, y, paint);
    }

    @Override
    protected void actualizarHitbox() {
        hitbox.set(x, y, x + ancho, y + alto);
    }

    @Override
    public void recibirEntrada(int entradaIndex, boolean valor) {

        // 🔥 SOLO aceptar entrada 0 (entrada única)
        if (entradaIndex == 0) {
            this.entrada = valor;
        }
    }

    @Override
    public void actualizar() {
        // LED no propaga nada
    }

    @Override
    public void dibujar(Canvas canvas) {

        float cx = x + ancho / 2f;
        float cy = y + alto / 2f;
        float r = 38;

        Paint fill = new Paint();
        fill.setStyle(Paint.Style.FILL);
        fill.setAntiAlias(true);

        Paint border = new Paint();
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(4);
        border.setColor(Color.WHITE);
        border.setAntiAlias(true);

        Paint glow = new Paint();
        glow.setStyle(Paint.Style.FILL);
        glow.setAntiAlias(true);

        // 🔌 base
        Paint base = new Paint();
        base.setStyle(Paint.Style.FILL);
        base.setColor(Color.DKGRAY);

        canvas.drawRect(cx - 12, cy + 35, cx + 12, cy + 55, base);

        // 💡 estado
        if (entrada) {
            glow.setColor(Color.argb(80, 255, 255, 0));
            canvas.drawCircle(cx, cy, r + 18, glow);

            fill.setColor(Color.YELLOW);
        } else {
            fill.setColor(Color.rgb(50, 50, 50));
        }

        canvas.drawCircle(cx, cy, r, fill);
        canvas.drawCircle(cx, cy, r, border);
    }

    @Override
    public float getEntradaX(int entradaIndex) {
        return x + 10;
    }

    @Override
    public float getEntradaY(int entradaIndex) {
        return y + alto / 2f;
    }

    @Override
    public boolean calcularSalida() {
        return entrada;
    }
}