package com.example.recorriendolahistoria;

import static com.example.recorriendolahistoria.R.id.btn_opcion3_Imag;
import static com.example.recorriendolahistoria.R.id.btn_respuesta3;

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

public class ActivityPreguntasOpcionesImagenes extends AppCompatActivity {


    TextView Pregunta_OpImg, TvTiempo_OpImg;
    Button Opcion1_OpImg;
    Button Opcion2_OpImg;
    Button Opcion3_OpImg;
    Button Opcion4_OpImg;

    String rCorrecta_OpImg, rIncorrecta1_OpImg, rIncorrecta2_OpImg, rIncorrecta3_OpImg, StrIdPreg_OpImg;
    int idPreg_OpImg;
    int puntajeActual_OpImg = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas_opciones_imagenes);

        TvTiempo_OpImg = findViewById(R.id.tv_Tiempo_Imag);
        Pregunta_OpImg = findViewById(R.id.tv_pregunta_Imag);
        Opcion1_OpImg = findViewById(R.id.btn_opcion1_Imag);
        Opcion2_OpImg = findViewById(R.id.btn_opcion2_Imag);
        Opcion3_OpImg = findViewById(R.id.btn_opcion3_Imag);
        Opcion4_OpImg = findViewById(R.id.btn_opcion4_Imag);
        StrIdPreg_OpImg = String.valueOf(idPreg_OpImg);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            Preguntas EstaPregunta = (Preguntas) bundle.get("PreguntaEnCurso");
            Pregunta_OpImg.setText(EstaPregunta.getPregunta());
            rCorrecta_OpImg = EstaPregunta.getRespCorrecta();
            rIncorrecta1_OpImg = EstaPregunta.getRespIncorrecta1();
            rIncorrecta2_OpImg = EstaPregunta.getRespIncorrecta2();
            rIncorrecta3_OpImg = EstaPregunta.getRespIncorrecta3();

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
                Opcion1_OpImg.setText(rCorrecta_OpImg);
                break;
            case 2:
                Opcion2_OpImg.setText(rCorrecta_OpImg);
                break;
            case 3:
                Opcion3_OpImg.setText(rCorrecta_OpImg);
                break;
            case 4:
                Opcion4_OpImg.setText(rCorrecta_OpImg);
                break;
        }
        switch (oInc1) {
            case 1:
                Opcion1_OpImg.setText(rIncorrecta1_OpImg);
                break;
            case 2:
                Opcion2_OpImg.setText(rIncorrecta1_OpImg);
                break;
            case 3:
                Opcion3_OpImg.setText(rIncorrecta1_OpImg);
                break;
            case 4:
                Opcion4_OpImg.setText(rIncorrecta1_OpImg);
                break;
        }
        switch (oInc2) {
            case 1:
                Opcion1_OpImg.setText(rIncorrecta2_OpImg);
                break;
            case 2:
                Opcion2_OpImg.setText(rIncorrecta2_OpImg);
                break;
            case 3:
                Opcion3_OpImg.setText(rIncorrecta2_OpImg);
                break;
            case 4:
                Opcion4_OpImg.setText(rIncorrecta2_OpImg);
                break;
        }
        switch (oInc3) {
            case 1:
                Opcion1_OpImg.setText(rIncorrecta3_OpImg);
                break;
            case 2:
                Opcion2_OpImg.setText(rIncorrecta3_OpImg);
                break;
            case 3:
                Opcion3_OpImg.setText(rIncorrecta3_OpImg);
                break;
            case 4:
                Opcion4_OpImg.setText(rIncorrecta3_OpImg);
                break;
        }

        iniciarCuentaRegresiva();
    }
    int tiempoRestante_Img;
    CountDownTimer cuenta;

    private void iniciarCuentaRegresiva() {
        int segundos = 15;
        long Lsegundos = segundos * 1000;

        cuenta = new CountDownTimer(Lsegundos, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long tiempo = millisUntilFinished / 1000;
                tiempoRestante_Img = (int) tiempo;
                String mostTiempo = String.valueOf(tiempo);
                TvTiempo_OpImg.setText("Tiempo Restante: " + mostTiempo);
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "Tiempo Finalizado", Toast.LENGTH_SHORT).show();
                tiempoRestante_Img = 1;
                puntajeActual_OpImg = 1;
                actualizarPuntos();
            }

        }.start();

    }

    @SuppressLint("ResourceType")
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_opcion1_Imag:

                if (Opcion1_OpImg.getText() == rCorrecta_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta correcta", Toast.LENGTH_SHORT).show();
                    cuenta.cancel();
                    actualizarPuntos();
                }

                if (Opcion1_OpImg.getText() == rIncorrecta1_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 1", Toast.LENGTH_SHORT).show();
                    //Opcion1.setBackgroundResource(R.color.rojo);
                    Opcion1_OpImg.setText(" - - - - - - - - - -");
                    Opcion1_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }
                if (Opcion1_OpImg.getText() == rIncorrecta2_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 2", Toast.LENGTH_SHORT).show();
                    //Opcion1.setBackgroundResource(R.color.rojo);
                    Opcion1_OpImg.setText(" - - - - - - - - - -");
                    Opcion1_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }
                if (Opcion1_OpImg.getText() == rIncorrecta3_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 3", Toast.LENGTH_SHORT).show();
                    //Opcion1.setBackgroundResource(R.color.rojo);
                    Opcion1_OpImg.setText(" - - - - - - - - - -");
                    Opcion1_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }
                break;


            case R.id.btn_opcion2_Imag:
                if (Opcion2_OpImg.getText() == rCorrecta_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta correcta", Toast.LENGTH_SHORT).show();
                    cuenta.cancel();
                    actualizarPuntos();
                }
                if (Opcion2_OpImg.getText() == rIncorrecta1_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                    //Opcion2.setBackgroundColor(getResources().getColor(Color.RED));
                    Opcion2_OpImg.setText(" - - - - - - - - - -");
                    Opcion2_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }
                if (Opcion2_OpImg.getText() == rIncorrecta2_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                    //Opcion2.setBackgroundColor(getResources().getColor(Color.RED));
                    Opcion2_OpImg.setText(" - - - - - - - - - -");
                    Opcion2_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }
                if (Opcion2_OpImg.getText() == rIncorrecta3_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
                    //Opcion2.setBackgroundColor(getResources().getColor());
                    Opcion2_OpImg.setText(" - - - - - - - - - -");
                    Opcion2_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }

                break;
            case btn_opcion3_Imag:
                if (Opcion3_OpImg.getText() == rCorrecta_OpImg) {
                    cuenta.cancel();
                    actualizarPuntos();
                    Toast.makeText(getApplicationContext(), "Respuesta correcta", Toast.LENGTH_SHORT).show();
                }
                if (Opcion3_OpImg.getText() == rIncorrecta1_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 1", Toast.LENGTH_SHORT).show();
                    //Opcion3.setBackgroundColor(RED);
                    Opcion3_OpImg.setText(" - - - - - - - - - -");
                    Opcion3_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }
                if (Opcion3_OpImg.getText() == rIncorrecta2_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 2", Toast.LENGTH_SHORT).show();
                    //  Opcion3.setBackgroundColor(RED);
                    Opcion3_OpImg.setText(" - - - - - - - - - -");
                    Opcion3_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }
                if (Opcion3_OpImg.getText() == rIncorrecta3_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 3", Toast.LENGTH_SHORT).show();
                    //  Opcion3.setBackgroundColor(RED);
                    Opcion3_OpImg.setText(" - - - - - - - - - -");
                    Opcion3_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }
                break;
            case R.id.btn_opcion4_Imag:
                if (
                        Opcion4_OpImg.getText() == rCorrecta_OpImg) {
                    cuenta.cancel();
                    actualizarPuntos();
                    Toast.makeText(getApplicationContext(), "Respuesta correcta", Toast.LENGTH_SHORT).show();
                }
                if (Opcion4_OpImg.getText() == rIncorrecta1_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 1", Toast.LENGTH_SHORT).show();
                    //  Opcion4.setBackgroundColor(RED);
                    Opcion4_OpImg.setText(" - - - - - - - - - -");
                    Opcion4_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }
                if (Opcion4_OpImg.getText() == rIncorrecta2_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 2", Toast.LENGTH_SHORT).show();
                    //    Opcion4.setBackgroundColor(RED);
                    Opcion4_OpImg.setText(" - - - - - - - - - -");
                    Opcion4_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
                }
                if (Opcion4_OpImg.getText() == rIncorrecta3_OpImg) {
                    Toast.makeText(getApplicationContext(), "Respuesta incorrecta 3", Toast.LENGTH_SHORT).show();
                    //   Opcion4.setBackgroundColor(RED);
                    Opcion4_OpImg.setText(" - - - - - - - - - -");
                    Opcion4_OpImg.setClickable(false);
                    puntajeActual_OpImg--;
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
        int puntosFinal = puntajeActual_OpImg * tiempoRestante_Img;

        if (puntajeActual_OpImg == 1) puntosFinal = 1;

        String[] parametro = {EstaPregunta.getId().toString()};
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_PUNTOS, puntosFinal);
        db.update(Utilidades.TABLA_PREGUNTAS, values, Utilidades.CAMPO_ID + "=? ", parametro);
        db.close();
        finish();
    }

}

