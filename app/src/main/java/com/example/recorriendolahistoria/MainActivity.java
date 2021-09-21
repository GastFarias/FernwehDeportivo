package com.example.recorriendolahistoria;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.recorriendolahistoria.utilidades.Utilidades;

public class MainActivity extends AppCompatActivity {

    public static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "preguntas", null, 1);

        CargarBd();

    }



    public boolean cargada = false;



    public void CargarBd(){
        if (!cargada){
            Cargar(1,"Pregunta de prueba1?","Si señor","no1", "no2","no3",1,11);
            Cargar(2,"Pregunta de prueba2?","Si señor2","no12", "no22","no32",2,22);

            cargada = true;
        }
    }

    private void Cargar(int id, String preg, String respC, String respI1, String respI2, String respI3, int tipo, int guia) {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "preguntas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        //String insert = "INSERT INTO " + Utilidades.TABLA_PREGUNTAS" ( " + Utilidades.CAMPO_ID "," + Utilidades.CAMPO_PREGUNTA;

        //db.execSQL(insert);


        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID,id);
        values.put(Utilidades.CAMPO_PREGUNTA,preg);
        values.put(Utilidades.CAMPO_RESP_CORRECTA,respC);
        values.put(Utilidades.CAMPO_RESP_INCORRECTA1,respI1);
        values.put(Utilidades.CAMPO_RESP_INCORRECTA2,respI2);
        values.put(Utilidades.CAMPO_RESP_INCORRECTA3,respI3);
        values.put(Utilidades.CAMPO_TIPO_PREGUNTA,tipo);
        values.put(Utilidades.CAMPO_GUIA,guia);

        Long idResultante = db.insert(Utilidades.TABLA_PREGUNTAS,Utilidades.CAMPO_ID,values);


        db.close();
    }
}