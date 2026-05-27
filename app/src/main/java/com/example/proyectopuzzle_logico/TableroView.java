package com.example.proyectopuzzle_logico;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class TableroView extends View {

    Paint paint;

    public TableroView(Context context) {
        super(context);

        paint = new Paint();

        paint.setColor(Color.WHITE);

        paint.setStyle(Paint.Style.STROKE);

        paint.setStrokeWidth(6);

        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        dibujarNOT(canvas, 100, 100);

        dibujarNOT(canvas, 500, 100);

    }

    public void dibujarNOT(
            Canvas canvas,
            int x,
            int y
    ) {

        // LINEA ENTRADA

        canvas.drawLine(
                x,
                y + 75,
                x + 50,
                y + 75,
                paint
        );

        // TRIANGULO

        Path path = new Path();

        path.moveTo(x + 50, y);

        path.lineTo(x + 50, y + 150);

        path.lineTo(x + 200, y + 75);

        path.close();

        canvas.drawPath(path, paint);

        // CIRCULO

        canvas.drawCircle(
                x + 225,
                y + 75,
                20,
                paint
        );

        // LINEA SALIDA

        canvas.drawLine(
                x + 245,
                y + 75,
                x + 320,
                y + 75,
                paint
        );
    }
}