package com.example.proyectopuzzle_logico;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Fuente extends Compuerta {

    private boolean activa = false;

    public Fuente(float x, float y, Paint paint) {
        super(x, y, paint);
    }

    @Override
    protected void actualizarHitbox() {
        hitbox.set(x, y, x + ancho, y + alto);
    }

    @Override
    public void recibirEntrada(int entrada, boolean valor) {}

    @Override
    public boolean calcularSalida() {
        return activa;
    }

    public void setActiva(boolean estado) {
        this.activa = estado;
    }

    @Override
    public void actualizar() {
        boolean salida = calcularSalida();

        for (Conexion c : salidas) {
            c.getDestino().recibirEntrada(
                    c.getEntradaDestino(),
                    salida
            );
        }
    }

    @Override
    public void resetEntradas() {}

    @Override
    public void dibujar(Canvas canvas) {

        float cx = x + ancho / 2f;
        float cy = y + alto / 2f;

        Paint fill = new Paint();
        fill.setStyle(Paint.Style.FILL);
        fill.setAntiAlias(true);

        Paint border = new Paint();
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(4);
        border.setColor(Color.WHITE);
        border.setAntiAlias(true);

        Paint text = new Paint();
        text.setColor(Color.WHITE);
        text.setTextSize(26);
        text.setAntiAlias(true);

        // 🔌 cuerpo
        fill.setColor(activa ? Color.GREEN : Color.DKGRAY);

        canvas.drawRoundRect(
                x,
                y,
                x + ancho,
                y + alto,
                20,
                20,
                fill
        );

        canvas.drawRoundRect(
                x,
                y,
                x + ancho,
                y + alto,
                20,
                20,
                border
        );

        // ⚡ símbolo
        if (activa) {
            Paint bolt = new Paint();
            bolt.setColor(Color.YELLOW);
            bolt.setTextSize(40);
            bolt.setAntiAlias(true);

            canvas.drawText("⚡", cx - 12, cy + 12, bolt);
        }

        // 🧠 etiqueta
        canvas.drawText("VCC", x + 15, y + 30, text);

        // 🔌 PATITA DE SALIDA (IMPORTANTE)
        Paint pin = new Paint();
        pin.setColor(Color.WHITE);
        pin.setStrokeWidth(6);
        pin.setAntiAlias(true);

        float pinX1 = x + ancho;
        float pinY = cy;
        float pinX2 = x + ancho + 40;

        canvas.drawLine(pinX1, pinY, pinX2, pinY, pin);

        // opcional: punto de conexión más visible
        Paint dot = new Paint();
        dot.setColor(Color.WHITE);
        dot.setAntiAlias(true);

        canvas.drawCircle(pinX2, pinY, 6, dot);
    }
}