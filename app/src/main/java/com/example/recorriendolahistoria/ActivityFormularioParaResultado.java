package com.example.recorriendolahistoria;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ActivityFormularioParaResultado extends AppCompatActivity {

    String Promedio, Correctas, Incorrectas, PuntosAcum;

    TextView tvProm, tvCorr, tvInc, tvPuntAcu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_para_resultado);

        Bundle bundle = this.getIntent().getExtras();
        Promedio = "Promedio de Puntos: " + bundle.get("Promedio").toString();
        Correctas = "Preguntas Correctas: " + bundle.get("Correctas Acumuladas").toString();
        Incorrectas = "Preguntas Incorrectas: " + bundle.get("Incorrectas Acumuladas").toString();
        PuntosAcum = "Acumulaste " + bundle.get("Puntos acumulados").toString() + "Puntos";

        tvProm = findViewById(R.id.tvPromedio);
        tvCorr = findViewById(R.id.tvCorrectasAcumuladas);
        tvInc = findViewById(R.id.tvIncorrectasAcumuladas);
        tvPuntAcu = findViewById(R.id.tvPuntosAcumulados);

        tvProm.setText(Promedio);
        tvCorr.setText(Correctas);
        tvInc.setText(Incorrectas);
        tvPuntAcu.setText(PuntosAcum);

    }
    public void OnClick(View view){
        finish();
    }


}