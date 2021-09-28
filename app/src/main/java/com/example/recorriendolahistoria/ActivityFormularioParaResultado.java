package com.example.recorriendolahistoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityFormularioParaResultado extends AppCompatActivity {

    String Promedio, Correctas, Incorrectas, PuntosAcum;
    Boolean etPrendido;
    int puntosTraidos;
    int puntosExtra = 500000;
    String codigo = "0110";

    TextView tvProm, tvCorr, tvInc, tvPuntAcu, cartelPuntos;
    EditText et_IngresoCodigo;

    int guardado = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_para_resultado);

        Bundle bundle = this.getIntent().getExtras();
        Promedio = "Promedio de Puntos: " + bundle.get("Promedio").toString();
        Correctas = "Preguntas Correctas: " + bundle.get("Correctas Acumuladas").toString();
        Incorrectas = "Preguntas Incorrectas: " + bundle.get("Incorrectas Acumuladas").toString();
        puntosTraidos = (int) bundle.get("Puntos acumulados");
        PuntosAcum = "Acumulaste " + puntosTraidos + " Puntos";


        tvProm = findViewById(R.id.tvPromedio);
        tvCorr = findViewById(R.id.tvCorrectasAcumuladas);
        tvInc = findViewById(R.id.tvIncorrectasAcumuladas);
        tvPuntAcu = findViewById(R.id.tvPuntosAcumulados);

        et_IngresoCodigo = findViewById(R.id.etIngresarCodigo);
        cartelPuntos = findViewById(R.id.tvCartelDelCodigo);

        tvProm.setText(Promedio);
        tvCorr.setText(Correctas);
        tvInc.setText(Incorrectas);
        tvPuntAcu.setText(PuntosAcum);

        cartelPuntos.setAlpha(0);
        et_IngresoCodigo.setAlpha(0);
        et_IngresoCodigo.setClickable(false);
        etPrendido = false;

        CheckBonus();
        if (guardado == 1){
            int pFinal = puntosTraidos + puntosExtra;
            PuntosAcum = "Acumulaste " + pFinal + " Puntos";
            tvPuntAcu.setText(PuntosAcum);
        }
    }

    public void OnClick(View view) {
        finish();
    }

    public void OnClickEspecial(View view) {
        if (etPrendido == false) {
            et_IngresoCodigo.setAlpha(1);
            et_IngresoCodigo.setClickable(true);
            etPrendido = true;
        }
        else if (etPrendido == true) {
            String CodigoIngresado = String.valueOf(et_IngresoCodigo.getText());

            if(!CodigoIngresado.equals(codigo) && CodigoIngresado != null) {
                Toast.makeText(getApplicationContext(), "Codigo Incorrecto ", Toast.LENGTH_SHORT).show();
                et_IngresoCodigo.setAlpha(0);
                et_IngresoCodigo.setText("");
                et_IngresoCodigo.setClickable(false);
                etPrendido = false;
            }

            if (CodigoIngresado.equals(codigo)) {

                SharedPreferences preferences = getSharedPreferences("CodigoUsado", Context.MODE_PRIVATE);
                Integer integer = preferences.getInt("bolCodigo", 0);
                guardado = integer;

                if (guardado == 1) {
                    Toast.makeText(getApplicationContext(), "Codigo Utilizado", Toast.LENGTH_SHORT).show();
                    et_IngresoCodigo.setAlpha(0);
                    et_IngresoCodigo.setText("");
                    et_IngresoCodigo.setClickable(false);
                    etPrendido = false;
                }
                if (guardado == 0) {
                    Toast.makeText(getApplicationContext(), "Puntos extra: " + puntosExtra, Toast.LENGTH_SHORT).show();
                    cartelPuntos.setText("¡ ¡ ¡ ¡ EXELENTE! ! ! ! Ganaste " + puntosExtra + " puntos extra");
                    cartelPuntos.setAlpha(1);
                    int pFinal = puntosTraidos + puntosExtra;
                    PuntosAcum = "Acumulaste " + pFinal + " Puntos";
                    tvPuntAcu.setText(PuntosAcum);

                    SharedPreferences preferencias = getSharedPreferences("CodigoUsado", Context.MODE_PRIVATE);
                    guardado = 1;
                    SharedPreferences.Editor editor = preferencias.edit();
                    editor.putInt("bolCodigo", guardado);
                    editor.commit();
                }

            }
            if (CodigoIngresado == null) {
                et_IngresoCodigo.setAlpha(0);
                et_IngresoCodigo.setText("");
                et_IngresoCodigo.setClickable(false);
                etPrendido = false;
            }
           /* if(CodigoIngresado != codigo && CodigoIngresado != null) {
                Toast.makeText(getApplicationContext(), "Codigo Incorrecto", Toast.LENGTH_SHORT).show();
                et_IngresoCodigo.setAlpha(0);
                et_IngresoCodigo.setText("");
                et_IngresoCodigo.setClickable(false);
                etPrendido = false;
            }*/


        }
    }
    private void CheckBonus(){
        SharedPreferences preferences = getSharedPreferences("CodigoUsado", Context.MODE_PRIVATE);
        Integer integer = preferences.getInt("bolCodigo", 0);
        guardado = integer;
    }

}

