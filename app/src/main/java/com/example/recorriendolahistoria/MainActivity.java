package com.example.recorriendolahistoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enviarID = listPreguntas.get(recyclerViewPreguntas.getChildAdapterPosition(v)).getId().toString();
                    Preguntas pregCurso = new Preguntas(null,null,null,null,null,null,null,null,null);
                    SQLiteDatabase db = conn.getReadableDatabase();
                    Cursor cursor = db.rawQuery
                            ("Select * from "+Utilidades.TABLA_PREGUNTAS +
                                    " where " + Utilidades.CAMPO_ID + "=? ", new String[]{enviarID});
                    cursor.moveToFirst();
                    pregCurso.setId(cursor.getInt(0));
                    pregCurso.setPregunta(cursor.getString(1));
                    pregCurso.setRespCorrecta(cursor.getString(2));
                    pregCurso.setRespIncorrecta1(cursor.getString(3));
                    pregCurso.setRespIncorrecta2(cursor.getString(4));
                    pregCurso.setRespIncorrecta3(cursor.getString(5));
                    pregCurso.setTipoPregunta(cursor.getInt(6));
                    pregCurso.setPuntos(cursor.getInt(8));



                    if (pregCurso.getPuntos() == 0){
                        Intent intent;
                        
                        switch (pregCurso.getTipoPregunta()){
                            case 1:
                                intent = new Intent(MainActivity.this,ActivityPreguntaVerdaderoFalso.class);
                                break;
                            case 2:
                                intent = new Intent(MainActivity.this,ActivityPreguntaOpcionesTexto.class);
                                break;
                            case 3:
                                intent = new Intent(MainActivity.this,ActivityPreguntasOpcionesImagenes.class);
                                break;

                            default:
                                throw new IllegalStateException("Unexpected value: " + pregCurso.getTipoPregunta());
                        }
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("PreguntaEnCurso", pregCurso);
                        intent.putExtras(bundle);
                        startActivity(intent);


                    }else{
                        Toast.makeText(getApplicationContext(), "Oportunidad Completa", Toast.LENGTH_SHORT).show();
                    }
                    /*
                    Toast.makeText(getApplicationContext(),
                            "Seleccionado" + listPreguntas.get(recyclerViewPreguntas.getChildAdapterPosition(v)).getId(),
                            Toast.LENGTH_SHORT).show();
                            */
                }
            });

            recyclerViewPreguntas.setAdapter(adapter);

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

        Cargar(1, "Pregunta de prueba1?", "Si señor", "no1", null, null, 1, 1,0);
        Cargar(2, "Pregunta de prueba2?", "Si señor2", "no12", "no22", "no32", 2, 2,150);
        Cargar(3,"Preg pueba 3","Correcta","no1","no2","no3",3,3,5);
        Cargar(4, "Pregunta de prueba1?", "Si señor", "no1", null, null, 1, 1,1500);
        Cargar(5, "Pregunta de prueba2?", "Si señor2", "no12", "no22", "no32", 2, 2,500000);
        Cargar(6,"Preg pueba 3","Correcta","no1","no2","no3",3,3,0);

        GuardarPref();
    }

    private void Cargar(int id, String preg, String respC, String respI1, String respI2, String respI3, int tipo, int guia, int puntos) {

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "preguntas", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        /*
        String insert = "INSERT INTO " + Utilidades.TABLA_PREGUNTAS" ( " + Utilidades.CAMPO_ID "," + Utilidades.CAMPO_PREGUNTA;
        db.execSQL(insert);
        */


        ContentValues values = new ContentValues();
        values.put(Utilidades.CAMPO_ID, id);
        values.put(Utilidades.CAMPO_PREGUNTA, preg);
        values.put(Utilidades.CAMPO_RESP_CORRECTA, respC);
        values.put(Utilidades.CAMPO_RESP_INCORRECTA1, respI1);
        values.put(Utilidades.CAMPO_RESP_INCORRECTA2, respI2);
        values.put(Utilidades.CAMPO_RESP_INCORRECTA3, respI3);
        values.put(Utilidades.CAMPO_TIPO_PREGUNTA, tipo);
        values.put(Utilidades.CAMPO_GUIA, guia);
        values.put(Utilidades.CAMPO_PUNTOS,puntos);

        Long idResultante = db.insert(Utilidades.TABLA_PREGUNTAS, Utilidades.CAMPO_ID, values);


        db.close();
    }
}