package com.example.recorriendolahistoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityCartaPresentacion_IngresoCodigo extends AppCompatActivity {

    TextView CodigoIngresado;
    Button btnIngresar;
    String CodigoValen = "3004", CodigoEuge = "1512", CodigoBrunella = "2407";
    int bolValen = 0, bolEuge = 0, bolBrune = 0;
    TextView tv_carta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta_presentacion_ingreso_codigo);

        CodigoIngresado = findViewById(R.id.et_Codigo);
        btnIngresar = findViewById(R.id.btnSiguiente);
        tv_carta = findViewById(R.id.tvCartaPrecentacion);

        SharedPreferences preferences = getSharedPreferences("Codigos", Context.MODE_PRIVATE);
        bolValen = preferences.getInt("Valen", 0);
        bolEuge = preferences.getInt("Euge", 0);
        bolBrune = preferences.getInt("Brune", 0);

        if (bolValen == 1 || bolEuge == 1 || bolBrune == 1){
            btnIngresar.setText("Ingresar");
        }
        tv_carta.setText("Hola a todos y bienvenidos a nuestra aplicación!" +
                " Somos el grupo de Fernweh deportivo que pensamos en esta divertida forma para ponernos a entrenar los cerebros. " +
                "\n\n   Para ingresar, mañana les daremos los códigos especiales de guías \n\n   ¡Muchas Gracias por elegirnos !");

    }
    public void OnClick(View view){
        if (CodigoIngresado.getText().toString().equals(CodigoValen)){
            SharedPreferences preferences = getSharedPreferences("Codigos", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putInt("Valen", 1);
            edit.commit();
            Toast.makeText(getApplicationContext(), "Codigo - Valentina Gullo -", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActivityCartaPresentacion_IngresoCodigo.this, MainActivity.class);
            startActivity(intent);
        }

        if (CodigoIngresado.getText().toString().equals(CodigoEuge)){
            SharedPreferences preferences = getSharedPreferences("Codigos", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putInt("Euge", 1);
            edit.commit();
            Toast.makeText(getApplicationContext(), "Codigo - Eugenia Navarro -", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActivityCartaPresentacion_IngresoCodigo.this, MainActivity.class);
            startActivity(intent);
        }

        if (CodigoIngresado.getText().toString().equals(CodigoBrunella)){
            SharedPreferences preferences = getSharedPreferences("Codigos", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = preferences.edit();
            edit.putInt("Brune", 1);
            edit.commit();
            Toast.makeText(getApplicationContext(), "Codigo - Brunella Bossi -", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ActivityCartaPresentacion_IngresoCodigo.this, MainActivity.class);
            startActivity(intent);
        }
        else{
            if (bolValen == 1 || bolEuge == 1 || bolBrune == 1){
                Intent intent = new Intent(ActivityCartaPresentacion_IngresoCodigo.this, MainActivity.class);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "Por Favor Ingrese un Codigo de Guia ", Toast.LENGTH_SHORT).show();
            }
            
        }

        CodigoIngresado.setText("");
    }


}