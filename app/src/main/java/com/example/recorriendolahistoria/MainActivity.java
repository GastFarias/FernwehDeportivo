package com.example.recorriendolahistoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.recorriendolahistoria.entidades.Preguntas;
import com.example.recorriendolahistoria.utilidades.Utilidades;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public int guardado;

    ArrayList<Preguntas> listPreguntas;
    ArrayList<String> listaStringPreguntas;
    RecyclerView recyclerViewPreguntas;
    ConexionSQLiteHelper conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "preguntas", null, 1);
        CheckBase();
        if (guardado == 0) {
            CargarBd();
        }

        CrearRecycler();


    }

    private void CrearRecycler() {
        conn = new ConexionSQLiteHelper(getApplicationContext(),"preguntas", null,1);
        listPreguntas = new ArrayList<>();
        recyclerViewPreguntas = findViewById(R.id.RecyclerBtnPreg);
        recyclerViewPreguntas.setLayoutManager(new LinearLayoutManager(this));

        ConsultarListaPreguntas();


    }

    private void ConsultarListaPreguntas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Preguntas preguntas = null;
        Cursor cursor = db.rawQuery("Select * from "+Utilidades.TABLA_PREGUNTAS,null);
        while (cursor.moveToNext()){
            preguntas = new Preguntas(null,null,null,
                    null,null,null,null,null,null);
            preguntas.setId(cursor.getInt(0));
            preguntas.setGuia(cursor.getInt(7));
            preguntas.setPuntos(cursor.getInt(8));

            listPreguntas.add(preguntas);

            AdaptadorRecyclerPreguntas adapter = new AdaptadorRecyclerPreguntas(listPreguntas);
            recyclerViewPreguntas.setAdapter(adapter );

        }
    }

    private void CheckBase() {
        SharedPreferences preferencias = getSharedPreferences("NGuardado", Context.MODE_PRIVATE);
        Integer integer = preferencias.getInt("GG", 0);
        guardado = integer;
    }


    private void GuardarPref() {
        SharedPreferences preferencias = getSharedPreferences("NGuardado", Context.MODE_PRIVATE);
        guardado = 1;
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putInt("GG", guardado);
        editor.commit();
    }


    public void CargarBd() {

        Cargar(1, "Pregunta de prueba1?", "Si señor", "no1", "no2", "no3", 1, 1);
        Cargar(2, "Pregunta de prueba2?", "Si señor2", "no12", "no22", "no32", 2, 2);
        Cargar(3,"Preg pueba 3","Correcta","no1","no2","no3",3,3);
        Cargar(4, "Pregunta de prueba1?", "Si señor", "no1", "no2", "no3", 1, 1);
        Cargar(5, "Pregunta de prueba2?", "Si señor2", "no12", "no22", "no32", 2, 2);
        Cargar(6,"Preg pueba 3","Correcta","no1","no2","no3",3,3);

        GuardarPref();
    }

    private void Cargar(int id, String preg, String respC, String respI1, String respI2, String respI3, int tipo, int guia) {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "preguntas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        //String insert = "INSERT INTO " + Utilidades.TABLA_PREGUNTAS" ( " + Utilidades.CAMPO_ID "," + Utilidades.CAMPO_PREGUNTA;

        //db.execSQL(insert);


        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID, id);
        values.put(Utilidades.CAMPO_PREGUNTA, preg);
        values.put(Utilidades.CAMPO_RESP_CORRECTA, respC);
        values.put(Utilidades.CAMPO_RESP_INCORRECTA1, respI1);
        values.put(Utilidades.CAMPO_RESP_INCORRECTA2, respI2);
        values.put(Utilidades.CAMPO_RESP_INCORRECTA3, respI3);
        values.put(Utilidades.CAMPO_TIPO_PREGUNTA, tipo);
        values.put(Utilidades.CAMPO_GUIA, guia);

        Long idResultante = db.insert(Utilidades.TABLA_PREGUNTAS, Utilidades.CAMPO_ID, values);


        db.close();
    }
}