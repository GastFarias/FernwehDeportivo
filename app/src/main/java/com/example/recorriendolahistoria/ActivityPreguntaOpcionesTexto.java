package com.example.recorriendolahistoria;



import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recorriendolahistoria.entidades.Preguntas;
import com.example.recorriendolahistoria.utilidades.Utilidades;


public class ActivityPreguntaOpcionesTexto extends AppCompatActivity {

    TextView Pregunta, TvTiempo;
    Button Opcion1;
    Button Opcion2;
    Button Opcion3;
    Button Opcion4;

    String rCorrecta, rIncorrecta1, rIncorrecta2, rIncorrecta3, StrIdPreg;
    int idPreg;
    int puntajeActual = 4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_opciones_texto);

        TvTiempo = findViewById(R.id.tv_Tiempo);
        Pregunta = findViewById(R.id.tv_pregunta);
        Opcion1 = findViewById(R.id.btn_respuesta1);
        Opcion2 = findViewById(R.id.btn_respuesta2);
        Opcion3 = findViewById(R.id.btn_respuesta3);
        Opcion4 = findViewById(R.id.btn_respuesta4);
        StrIdPreg = String.valueOf(idPreg);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            Preguntas EstaPregunta = (Preguntas) bundle.get("PreguntaEnCurso");
            Pregunta.setText(EstaPregunta.getPregunta());
            rCorrecta = EstaPregunta.getRespCorrecta();
            rIncorrecta1 = EstaPregunta.getRespIncorrecta1();
            rIncorrecta2 = EstaPregunta.getRespIncorrecta2();
            rIncorrecta3 = EstaPregunta.getRespIncorrecta3();

        }
        int oCor, oInc1 = 0, oInc2 = 0, oInc3 = 0;
        oCor = (int) (Math.random() * 4) + 1;
        while (oInc1 == 0 || oInc1 == oCor) {
            oInc1 = (int) (Math.random() * 4) + 1;
        }
        while (oInc2 == 0 || oInc2 == oCor || oInc2 == oInc1) {
            oInc2 = (int) (Math.random() * 4) + 1;
        }
        while (oInc3 == 0 || oInc3 == oCor || oInc3 == oInc1 || oInc3 == oInc2) {
            oInc3 = (int) (Math.random() * 4) + 1;
        }

        switch (oCor) {
            case 1:
                Opcion1.setText(rCorrecta);
                break;
            case 2:
                Opcion2.setText(rCorrecta);
                break;
            case 3:
                Opcion3.setText(rCorrecta);
                break;
            case 4:
                Opcion4.setText(rCorrecta);
                break;
        }
        switch (oInc1) {
            case 1:
                Opcion1.setText(rIncorrecta1);
                break;
            case 2:
                Opcion2.setText(rIncorrecta1);
                break;
            case 3:
                Opcion3.setText(rIncorrecta1);
                break;
            case 4:
                Opcion4.setText(rIncorrecta1);
                break;
        }
        switch (oInc2) {
            case 1:
                Opcion1.setText(rIncorrecta2);
                break;
            case 2:
                Opcion2.setText(rIncorrecta2);
                break;
            case 3:
                Opcion3.setText(rIncorrecta2);
                break;
            case 4:
                Opcion4.setText(rIncorrecta2);
                break;
        }
        switch (oInc3) {
            case 1:
                Opcion1.setText(rIncorrecta3);
                break;
            case 2:
                Opcion2.setText(rIncorrecta3);
                break;
            case 3:
                Opcion3.setText(rIncorrecta3);
                break;
            case 4:
                Opcion4.setText(rIncorrecta3);
                break;
        }

        iniciarCuentaRegresiva();
    }

    int tiempoRestante;
    CountDownTimer cuenta;

    private void iniciarCuentaRegresiva() {
        int segundos = 15;
        long Lsegundos = segundos * 1000;

        cuenta = new CountDownTimer(Lsegundos, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long tiempo = millisUntilFinished / 1000;
                tiempoRestante = (int) tiempo;
                String mostTiempo = String.valueOf(tiempo);
                TvTiempo.setText("Tiempo Restante: " + mostTiempo);

            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Tiempo Finalizado", Toast.LENGTH_SHORT).show();
                tiempoRestante = 1;
                puntajeActual = 1;
                actualizarPuntos();
            }


        }.start();

    }

    @SuppressLint("ResourceType")
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_respuesta1:

                if (Opcion1.getText() == rCorrecta) {
                    Toast.makeText(getApplicationContext(), "Respuesta correcta", Toast.LENGTH_SHORT).show();
                    cuenta.cancel();
                    actualizarPuntos();
                }
                if (Opcion1.getText() == rIncorrecta1) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 1", Toast.LENGTH_SHORT).show();
                    //Opcion1.setBackgroundResource(R.color.rojo);
                    Opcion1.setText(" - - - - - - - - - -");
                    Opcion1.setClickable(false);
                    puntajeActual--;
                }
                if (Opcion1.getText() == rIncorrecta2) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 2", Toast.LENGTH_SHORT).show();
                    //Opcion1.setBackgroundResource(R.color.rojo);
                    Opcion1.setText(" - - - - - - - - - -");
                    Opcion1.setClickable(false);
                    puntajeActual--;
                }
                if (Opcion1.getText() == rIncorrecta3) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 3", Toast.LENGTH_SHORT).show();
                    //Opcion1.setBackgroundResource(R.color.rojo);
                    Opcion1.setText(" - - - - - - - - - -");
                    Opcion1.setClickable(false);
                    puntajeActual--;
                }
                break;


            case R.id.btn_respuesta2:
                if (Opcion2.getText() == rCorrecta) {
                    Toast.makeText(getApplicationContext(), "Respuesta correcta", Toast.LENGTH_SHORT).show();
                    cuenta.cancel();
                    actualizarPuntos();
                }
                if (Opcion2.getText() == rIncorrecta1) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 1", Toast.LENGTH_SHORT).show();
                    //Opcion2.setBackgroundColor(getResources().getColor(Color.RED));
                    Opcion2.setText(" - - - - - - - - - -");
                    Opcion2.setClickable(false);
                    puntajeActual--;
                }
                if (Opcion2.getText() == rIncorrecta2) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 2", Toast.LENGTH_SHORT).show();
                    //Opcion2.setBackgroundColor(getResources().getColor(Color.RED));
                    Opcion2.setText(" - - - - - - - - - -");
                    Opcion2.setClickable(false);
                    puntajeActual--;
                }
                if (Opcion2.getText() == rIncorrecta3) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 3", Toast.LENGTH_SHORT).show();
                    //Opcion2.setBackgroundColor(getResources().getColor());
                    Opcion2.setText(" - - - - - - - - - -");
                    Opcion2.setClickable(false);
                    puntajeActual--;
                }

                break;
            case R.id.btn_respuesta3:
                if (Opcion3.getText() == rCorrecta) {
                    cuenta.cancel();
                    actualizarPuntos();
                    Toast.makeText(getApplicationContext(), "Respuesta correcta", Toast.LENGTH_SHORT).show();
                }
                if (Opcion3.getText() == rIncorrecta1) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 1", Toast.LENGTH_SHORT).show();
                    //Opcion3.setBackgroundColor(RED);
                    Opcion3.setText(" - - - - - - - - - -");
                    Opcion3.setClickable(false);
                    puntajeActual--;
                }
                if (Opcion3.getText() == rIncorrecta2) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 2", Toast.LENGTH_SHORT).show();
                    //  Opcion3.setBackgroundColor(RED);
                    Opcion3.setText(" - - - - - - - - - -");
                    Opcion3.setClickable(false);
                    puntajeActual--;
                }
                if (Opcion3.getText() == rIncorrecta3) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 3", Toast.LENGTH_SHORT).show();
                    //  Opcion3.setBackgroundColor(RED);
                    Opcion3.setText(" - - - - - - - - - -");
                    Opcion3.setClickable(false);
                    puntajeActual--;
                }
                break;
            case R.id.btn_respuesta4:
                if (Opcion4.getText() == rCorrecta) {
                    cuenta.cancel();
                    actualizarPuntos();
                    Toast.makeText(getApplicationContext(), "Respuesta correcta", Toast.LENGTH_SHORT).show();
                }
                if (Opcion4.getText() == rIncorrecta1) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 1", Toast.LENGTH_SHORT).show();
                    //  Opcion4.setBackgroundColor(RED);
                    Opcion4.setText(" - - - - - - - - - -");
                    Opcion4.setClickable(false);
                    puntajeActual--;
                }
                if (Opcion4.getText() == rIncorrecta2) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 2", Toast.LENGTH_SHORT).show();
                    //    Opcion4.setBackgroundColor(RED);
                    Opcion4.setText(" - - - - - - - - - -");
                    Opcion4.setClickable(false);
                    puntajeActual--;
                }
                if (Opcion4.getText() == rIncorrecta3) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 3", Toast.LENGTH_SHORT).show();
                    //   Opcion4.setBackgroundColor(RED);
                    Opcion4.setText(" - - - - - - - - - -");
                    Opcion4.setClickable(false);
                    puntajeActual--;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    private void actualizarPuntos() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "preguntas", null, 1);
        Bundle bundle = this.getIntent().getExtras();
        Preguntas EstaPregunta = (Preguntas) bundle.get("PreguntaEnCurso");
        int puntosFinal = puntajeActual * tiempoRestante;

        if (puntajeActual == 1) puntosFinal = 1;

        String[] parametro = {EstaPregunta.getId().toString()};
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_PUNTOS, puntosFinal);
        db.update(Utilidades.TABLA_PREGUNTAS, values, Utilidades.CAMPO_ID + "=? ", parametro);
        db.close();
        finish();


    }

}