package com.example.proyectopuzzle_logico;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class TableroView extends View {

    private Paint paint;
    private Paint paintGrid;

    private ArrayList<Compuerta> compuertas;

    private boolean arrastrando = false;
    private boolean permitirMovimiento = true;

    private float offsetX;
    private float offsetY;

    private Compuerta compuertaSeleccionada;

    private static final int TAM_CELDA = 50;

    public TableroView(Context context) {
        super(context);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);

        paintGrid = new Paint();
        paintGrid.setColor(Color.DKGRAY);
        paintGrid.setStrokeWidth(1);

        compuertas = new ArrayList<>();
    }

    public void setPermitirMovimiento(boolean permitir) {
        this.permitirMovimiento = permitir;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        dibujarCuadricula(canvas);

        for (Compuerta compuerta : compuertas) {
            compuerta.dibujar(canvas);
        }
    }

    private void dibujarCuadricula(Canvas canvas) {

        for (int x = 0; x <= getWidth(); x += TAM_CELDA) {
            canvas.drawLine(x, 0, x, getHeight(), paintGrid);
        }

        for (int y = 0; y <= getHeight(); y += TAM_CELDA) {
            canvas.drawLine(0, y, getWidth(), y, paintGrid);
        }
    }

    public void agregarCompuerta(String tipo) {

        float x = getWidth() / 2f - 50;
        float y = getHeight() / 2f - 50;

        switch (tipo) {

            case "AND":
                compuertas.add(new And(x, y, paint));
                break;

            case "OR":
                compuertas.add(new Or(x, y, paint));
                break;

            case "NOT":
                compuertas.add(new Not(x, y, paint));
                break;
        }

        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:

                compuertaSeleccionada = null;

                for (int i = compuertas.size() - 1; i >= 0; i--) {

                    Compuerta compuerta = compuertas.get(i);

                    if (compuerta.contiene(touchX, touchY)) {

                        // 🔒 MODO BLOQUEADO → MENÚ
                        if (!permitirMovimiento) {
                            //mostrarMenu(compuerta);
                            return true;
                        }

                        // 🔓 MODO EDICIÓN → ARRASTRAR
                        compuertaSeleccionada = compuerta;

                        offsetX = touchX - compuerta.getX();
                        offsetY = touchY - compuerta.getY();

                        arrastrando = true;
                        break;
                    }
                }

                break;

            case MotionEvent.ACTION_MOVE:

                if (arrastrando && compuertaSeleccionada != null) {

                    compuertaSeleccionada.mover(
                            touchX - offsetX,
                            touchY - offsetY
                    );

                    invalidate();
                }

                break;

            case MotionEvent.ACTION_UP:

                if (compuertaSeleccionada != null) {

                    float nuevoX =
                            Math.round(compuertaSeleccionada.getX() / TAM_CELDA) * TAM_CELDA;

                    float nuevoY =
                            Math.round(compuertaSeleccionada.getY() / TAM_CELDA) * TAM_CELDA;

                    compuertaSeleccionada.mover(nuevoX, nuevoY);

                    invalidate();
                }

                arrastrando = false;
                compuertaSeleccionada = null;

                break;
        }

        return true;
    }

    // 🔥 MENÚ DE ACCIONES
    /*private void mostrarMenu(Compuerta compuerta) {

        String[] opciones = {"Eliminar", "Rotar", "Conectar"};

        new android.app.AlertDialog.Builder(getContext())
                .setTitle("Acciones")
                .setItems(opciones, (dialog, which) -> {

                    switch (which) {

                        case 0: // ELIMINAR
                            compuertas.remove(compuerta);
                            invalidate();
                            break;

                        case 1: // ROTAR
                            compuerta.rotar();
                            invalidate();
                            break;

                        case 2: // CONECTAR
                            Toast.makeText(getContext(),
                                    "Modo conexión activado",
                                    Toast.LENGTH_SHORT).show();
                            break;
                    }
                })
                .show();
    }*/
}