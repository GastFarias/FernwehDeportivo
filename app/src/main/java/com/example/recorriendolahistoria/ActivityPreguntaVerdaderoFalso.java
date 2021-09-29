package com.example.recorriendolahistoria;

import androidx.appcompat.app.AppCompatActivity;

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

public class ActivityPreguntaVerdaderoFalso extends AppCompatActivity {

    TextView pregunta, tv_Tiempo;
    Button btnVerdadero, btnFalso;
    String rCorrectaVF, rIncorrectaVF;
    int puntajeActual = 4 ;
    String justificativo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_verdadero_falso);

        pregunta = findViewById(R.id.tvPreg);
        btnVerdadero = findViewById(R.id.btnTrue);
        btnFalso = findViewById(R.id.btn_false);
        tv_Tiempo = findViewById(R.id.tv_TiempoVF);


        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            Preguntas EstaPregunta = (Preguntas) bundle.get("PreguntaEnCurso");
            pregunta.setText(EstaPregunta.getPregunta());
            rCorrectaVF = EstaPregunta.getRespCorrecta();
            rIncorrectaVF = EstaPregunta.getRespIncorrecta1();
            justificativo = EstaPregunta.getRespIncorrecta2();
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
                tv_Tiempo.setText("Tiempo Restante: " + mostTiempo);

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

    public void OnClickTrue(View view){
        if (btnVerdadero.getText().toString().equals(rCorrectaVF)){
            Toast.makeText(getApplicationContext(), "Respuesta Correcta", Toast.LENGTH_SHORT).show();
            cuenta.cancel();
            actualizarPuntos();
        }
        if (btnVerdadero.getText().toString().equals(rIncorrectaVF)) {
            Toast.makeText(getApplicationContext(), justificativo, Toast.LENGTH_LONG).show();
            cuenta.cancel();
            puntajeActual = 1;
            actualizarPuntos();
        }
    }
    public void OnClickFalse(View view) {
        if (btnFalso.getText().toString().equals(rCorrectaVF)) {
            Toast.makeText(getApplicationContext(), "Respuesta Correcta", Toast.LENGTH_SHORT).show();
            cuenta.cancel();
            actualizarPuntos();
        }
        if (btnFalso.getText().toString().equals(rIncorrectaVF)) {
            Toast.makeText(getApplicationContext(), "Respuesta Incorrecta", Toast.LENGTH_LONG).show();
            cuenta.cancel();
            puntajeActual = 1;
            actualizarPuntos();
        }
    }
}
