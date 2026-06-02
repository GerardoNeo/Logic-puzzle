package com.example.proyectopuzzle_logico;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class TableroView extends View {

    private Paint paint;
    private Paint paintGrid;
    private Paint paintSeleccion;

    private ArrayList<Compuerta> compuertas;

    private boolean arrastrando = false;
    private boolean permitirMovimiento = true;

    private float offsetX;
    private float offsetY;

    private Compuerta compuertaSeleccionada;

    private static final int TAM_CELDA = 50;

    public ArrayList<Compuerta> getCompuertas() {
        return compuertas;
    }

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

        paintSeleccion = new Paint();
        paintSeleccion.setColor(Color.YELLOW);
        paintSeleccion.setStyle(Paint.Style.STROKE);
        paintSeleccion.setStrokeWidth(4);

        compuertas = new ArrayList<>();
    }

    public void setPermitirMovimiento(boolean permitir) {
        this.permitirMovimiento = permitir;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        dibujarCuadricula(canvas);

        dibujarConexiones(canvas);

        for (Compuerta compuerta : compuertas) {

            compuerta.dibujar(canvas);

            if (compuerta == compuertaSeleccionada) {

                canvas.drawRect(
                        compuerta.getX() - 15,
                        compuerta.getY() - 15,
                        compuerta.getX() + 115,
                        compuerta.getY() + 115,
                        paintSeleccion
                );
            }
        }
    }

    private void dibujarCuadricula(Canvas canvas) {

        for (int x = 0; x <= getWidth(); x += TAM_CELDA) {
            canvas.drawLine(
                    x,
                    0,
                    x,
                    getHeight(),
                    paintGrid
            );
        }

        for (int y = 0; y <= getHeight(); y += TAM_CELDA) {
            canvas.drawLine(
                    0,
                    y,
                    getWidth(),
                    y,
                    paintGrid
            );
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

            case "LED":
                compuertas.add(new Led(x, y, paint));
                break;

            case "FUENTE":
                compuertas.add(new Fuente(x, y, paint));
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
                invalidate();

                for (int i = compuertas.size() - 1; i >= 0; i--) {

                    Compuerta compuerta = compuertas.get(i);

                    if (compuerta.contiene(touchX, touchY)) {

                        compuertaSeleccionada = compuerta;

                        // Actualizar spinners del Activity
                        if (getContext() instanceof Tutorial) {
                            ((Tutorial) getContext()).actualizarSpinners();
                        }

                        if (!permitirMovimiento) {

                            invalidate();
                            return true;
                        }

                        offsetX = touchX - compuerta.getX();
                        offsetY = touchY - compuerta.getY();

                        arrastrando = true;

                        invalidate();

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

                if (arrastrando && compuertaSeleccionada != null) {

                    float nuevoX =
                            Math.round(
                                    compuertaSeleccionada.getX() / TAM_CELDA
                            ) * TAM_CELDA;

                    float nuevoY =
                            Math.round(
                                    compuertaSeleccionada.getY() / TAM_CELDA
                            ) * TAM_CELDA;

                    compuertaSeleccionada.mover(
                            nuevoX,
                            nuevoY
                    );

                    invalidate();
                }

                arrastrando = false;

                break;
        }

        return true;
    }

    public void eliminarSeleccionada() {

        if (compuertaSeleccionada != null) {

            for (Compuerta c : compuertas) {
                c.getSalidas().removeIf(conexion ->
                        conexion.getDestino() == compuertaSeleccionada ||
                                conexion.getOrigen() == compuertaSeleccionada
                );
            }

            compuertas.remove(compuertaSeleccionada);
            compuertaSeleccionada = null;

            invalidate();
        }
    }

    public void rotarSeleccionada() {

        if (compuertaSeleccionada != null) {

            compuertaSeleccionada.rotar();

            invalidate();
        }
    }

    public Compuerta buscarCompuerta(String nombre) {

        for (Compuerta c : compuertas) {

            if (c.getNombre().equals(nombre)) {
                return c;
            }
        }

        return null;
    }

    public Compuerta getCompuertaSeleccionada() {
        return compuertaSeleccionada;
    }

    private void dibujarConexiones(Canvas canvas) {

        Paint p = new Paint();
        p.setColor(Color.GREEN);
        p.setStrokeWidth(6);
        p.setAntiAlias(true);

        for (Compuerta origen : compuertas) {

            for (Conexion conexion : origen.getSalidas()) {

                Compuerta destino = conexion.getDestino();

                float x1 = origen.getSalidaX();
                float y1 = origen.getSalidaY();

                float x2 = destino.getEntradaX(conexion.getEntradaDestino());
                float y2 = destino.getEntradaY(conexion.getEntradaDestino());

                canvas.drawLine(x1, y1, x2, y2, p);
            }
        }
    }
}