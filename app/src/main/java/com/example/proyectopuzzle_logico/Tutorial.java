package com.example.proyectopuzzle_logico;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Tutorial extends AppCompatActivity {

    private boolean primeraCarga = true;

    private TableroView tablero;

    private boolean move = true;

    private boolean prueba = false;

    private Spinner spinnerEntradaA;
    private Spinner spinnerEntradaB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tutorial);

        spinnerEntradaA = findViewById(R.id.spinner2);
        spinnerEntradaB = findViewById(R.id.spinner3);

        FrameLayout contenedor = findViewById(R.id.contenedorTablero);

        tablero = new TableroView(this);
        contenedor.addView(tablero);

        Spinner spinnerCompuertas = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.compuertas,
                        android.R.layout.simple_spinner_item
                );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerCompuertas.setAdapter(adapter);

        spinnerCompuertas.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {

                        if (primeraCarga) {
                            primeraCarga = false;
                            return;
                        }

                        String seleccion =
                                parent.getItemAtPosition(position).toString();

                        if (seleccion.equals("Seleccionar")) return;

                        tablero.agregarCompuerta(seleccion);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                }
        );

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );
    }

    public void mover(View view) {

        Button btn = findViewById(R.id.button4);

        move = !move;

        if (move) {
            btn.setBackgroundResource(R.drawable.ic_unlock);
        } else {
            btn.setBackgroundResource(R.drawable.ic_lock);
        }

        tablero.setPermitirMovimiento(move);
    }

    public void borrar(View view) {
        tablero.eliminarSeleccionada();
    }

    public void rotar(View view) {
        tablero.rotarSeleccionada();
    }

    public void actualizarSpinners() {

        Compuerta seleccionada = tablero.getCompuertaSeleccionada();
        if (seleccionada == null) return;

        ArrayList<String> salidas = new ArrayList<>();
        salidas.add("Ninguno");

        ArrayList<String> entradas = new ArrayList<>();
        entradas.add("Ninguno");

        for (Compuerta c : tablero.getCompuertas()) {

            if (c == seleccionada) continue;

            salidas.add(c.getNombre() + " - Output");

            entradas.add(c.getNombre() + " - Input A");

            if (!(c instanceof Not)) {
                entradas.add(c.getNombre() + " - Input B");
            }
        }

        ArrayAdapter<String> adapterSalidas =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        salidas
                );

        adapterSalidas.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinnerEntradaA.setAdapter(adapterSalidas);

        if (seleccionada instanceof Not) {

            ArrayList<String> noAplica = new ArrayList<>();
            noAplica.add("No aplica");

            ArrayAdapter<String> adapterNoAplica =
                    new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_spinner_item,
                            noAplica
                    );

            spinnerEntradaB.setAdapter(adapterNoAplica);

        } else {
            spinnerEntradaB.setAdapter(adapterSalidas);
        }
    }

    public void crearConexion(View view) {

        Compuerta destino = tablero.getCompuertaSeleccionada();
        if (destino == null) return;

        String entradaA = spinnerEntradaA.getSelectedItem() != null
                ? spinnerEntradaA.getSelectedItem().toString()
                : "";

        String entradaB = spinnerEntradaB.getSelectedItem() != null
                ? spinnerEntradaB.getSelectedItem().toString()
                : "";

        // Input A
        if (!entradaA.isEmpty() && !entradaA.equals("Ninguno")) {

            String nombreOrigen = entradaA.split(" - ")[0];
            Compuerta origen = tablero.buscarCompuerta(nombreOrigen);

            if (origen != null) {
                origen.agregarConexion(destino, 0);
            }
        }

        // Input B
        if (!entradaB.isEmpty()
                && !entradaB.equals("No aplica")
                && !entradaB.equals("Ninguno")) {

            String nombreOrigen = entradaB.split(" - ")[0];
            Compuerta origen = tablero.buscarCompuerta(nombreOrigen);

            if (origen != null) {
                origen.agregarConexion(destino, 1);
            }
        }

        tablero.invalidate();
    }

    public void probar(View view) {

        prueba = !prueba;

        // 1. activar fuentes
        for (Compuerta c : tablero.getCompuertas()) {
            if (c instanceof Fuente) {
                ((Fuente) c).setActiva(prueba);
            }
        }

        // 2. resetear entradas ANTES de calcular
        for (Compuerta c : tablero.getCompuertas()) {
            c.resetEntradas();
        }

        // 3. propagación (varias iteraciones)
        for (int i = 0; i < 5; i++) {
            for (Compuerta c : tablero.getCompuertas()) {
                c.actualizar();
            }
        }

        tablero.invalidate();
    }
}