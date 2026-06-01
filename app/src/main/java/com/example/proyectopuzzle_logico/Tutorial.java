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

public class Tutorial extends AppCompatActivity {

    private boolean primeraCarga = true;

    private TableroView tablero;

    private boolean move = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tutorial);

        FrameLayout contenedor =
                findViewById(R.id.contenedorTablero);

        tablero = new TableroView(this);

        contenedor.addView(tablero);

        Spinner spinnerCompuertas =
                findViewById(R.id.spinner);

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

                        if (seleccion.equals("Seleccionar")) {
                            return;
                        }

                        tablero.agregarCompuerta(seleccion);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

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
        if(move){
            btn.setBackgroundResource(R.drawable.ic_unlock);
        }else{
            btn.setBackgroundResource(R.drawable.ic_lock);
        }
        tablero.setPermitirMovimiento(move);
    }
}