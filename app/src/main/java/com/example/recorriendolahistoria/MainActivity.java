package com.example.recorriendolahistoria;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
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

    @Override
    protected void onRestart() {
        CrearRecycler();
        super.onRestart();
    }

    private void CrearRecycler() {
        conn = new ConexionSQLiteHelper(getApplicationContext(),"preguntas", null,1);
        listPreguntas = new ArrayList<>();
        recyclerViewPreguntas = findViewById(R.id.RecyclerBtnPreg);
        recyclerViewPreguntas.setLayoutManager(new LinearLayoutManager(this));

        ConsultarListaPreguntas();


    }
    Preguntas pregCurso = new Preguntas(null,null,null,null,
            null,null,null,null,null);

    private void ConsultarListaPreguntas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Preguntas preguntas = null;
        Cursor cursor = db.rawQuery("Select * from "+Utilidades.TABLA_PREGUNTAS,null);
        while (cursor.moveToNext()){

            SharedPreferences preferences = getSharedPreferences("Codigos", Context.MODE_PRIVATE);
            int bolValen = preferences.getInt("Valen", 0);
            int bolEuge = preferences.getInt("Euge", 0);
            int bolBrune = preferences.getInt("Brune", 0);
            switch (cursor.getInt(7)){
                case 1:
                    if (bolEuge == 1){
                        preguntas = new Preguntas(null,null,null,
                                null,null,null,null,null,null);
                        preguntas.setId(cursor.getInt(0));
                        preguntas.setGuia(cursor.getInt(7));
                        preguntas.setPuntos(cursor.getInt(8));

                        listPreguntas.add(preguntas);
                    }
                    break;
                case 2:
                    if (bolBrune == 1){
                        preguntas = new Preguntas(null,null,null,
                                null,null,null,null,null,null);
                        preguntas.setId(cursor.getInt(0));
                        preguntas.setGuia(cursor.getInt(7));
                        preguntas.setPuntos(cursor.getInt(8));

                        listPreguntas.add(preguntas);
                    }
                    break;
                case 3:
                    if (bolValen == 1){
                        preguntas = new Preguntas(null,null,null,
                                null,null,null,null,null,null);
                        preguntas.setId(cursor.getInt(0));
                        preguntas.setGuia(cursor.getInt(7));
                        preguntas.setPuntos(cursor.getInt(8));

                        listPreguntas.add(preguntas);
                    }
                    break;
            }

          /*  preguntas = new Preguntas(null,null,null,
                    null,null,null,null,null,null);
            preguntas.setId(cursor.getInt(0));
            preguntas.setGuia(cursor.getInt(7));
            preguntas.setPuntos(cursor.getInt(8));

            listPreguntas.add(preguntas);*/

            AdaptadorRecyclerPreguntas adapter = new AdaptadorRecyclerPreguntas(listPreguntas);

            adapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String enviarID = listPreguntas.get(recyclerViewPreguntas.getChildAdapterPosition(v)).getId().toString();
                    Preguntas pregCurso = new Preguntas(null,null,null,null,
                            null,null,null,null,null);
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
                    pregCurso.setGuia(cursor.getInt(7));
                    pregCurso.setPuntos(cursor.getInt(8));


                    SharedPreferences preferences = getSharedPreferences("Codigos", Context.MODE_PRIVATE);
                    int bolValen = preferences.getInt("Valen", 0);
                    int bolEuge = preferences.getInt("Euge", 0);
                    int bolBrune = preferences.getInt("Brune", 0);
                    switch (pregCurso.getGuia()){
                        case 1:
                            if (bolEuge == 1){
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
                                        Toast.makeText(getApplicationContext(), "Oportunidad Utilizada", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            if (bolEuge == 0){
                                Toast.makeText(getApplicationContext(), "Es Necesario el codigo de Guia ", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 2:
                            if (bolBrune == 1){

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
                                        Toast.makeText(getApplicationContext(), "Oportunidad Utilizada", Toast.LENGTH_SHORT).show();
                                    }

                            }
                            if (bolBrune == 0){
                                Toast.makeText(getApplicationContext(), "Es Necesario el codigo de Guia ", Toast.LENGTH_LONG).show();
                            }
                            break;
                        case 3:
                            if (bolValen == 1){
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
                                        Toast.makeText(getApplicationContext(), "Oportunidad Utilizada", Toast.LENGTH_SHORT).show();
                                    }

                            }
                            if (bolValen == 0){
                                Toast.makeText(getApplicationContext(), "Es Necesario el codigo de Guia ", Toast.LENGTH_LONG).show();
                            }
                            break;
                    }



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

        Cargar(1, "¿Cuáles son los tres puntos de desarrollo que impulsaron a la Ciudad de Villa Carlos Paz?",
                "El automovilismo, la noche y los teatros", "El automovilismo, las rutas y los hoteles",
                "La noche, los hoteles y el lago San Roque ", "El automovilismo, el lago y los teatros", 2, 1,0);

        Cargar(2, "¿Quien creo la \"Semana de la Velocidad\"?", "Alcides Raies", "Jorge Descotte.",
                "Oscar Alfredo Galvez", "Martin Cristi", 2, 1,0);

        Cargar(3," ¿En 1950 se corre la primera carrera en Villa Carlos Paz?","Falso","Verdadero","Se corre en el año 1958"
                ,null,1,1,0);

        Cargar(4, "¿Quien era Martin Cristi?", "Navegante", "Piloto", "Coleccionista",
                "Periodista deportivo", 2, 1,0);

        Cargar(5, "Dentro del bar Héroes, ¿De quien es el motor que se encuentra dentro?", "Pechito Lopez", "Colo Rosso",
                "Valentino Rossi", "Agustín Cannapino", 2, 1,0);



        Cargar(6,"El observatorio Astronómico de Córdoba es el más grande de Sudamérica","Falso","Verdadero",
                "El más grande se encuentra al norte de Chile",
                null,1,2,0);

        Cargar(7, "Los autos que hicieron la carrera del observatorio eran...", "Fiat 1", "Fiat 600", "Fiat 125",
                "Fiat 128", 2, 2,0);

        Cargar(8, "¿Qué mineral es el que se encuentra en la imagen?", "Feldespato", "Mica", "Berilio",
                "Cuarzo", 3, 2,0);

        Cargar(9, "¿El mini golf comenzó como un deporte únicamente femenino?", "Verdadero", "Falso", null,
                null, 1, 2,0);

        Cargar(10, "¿Qué representan las siglas CONAE?", "Ccomisión Nacional de Actividades Espaciales",
                "Complejo Nacional Espacial", "Centro Nacional Espacial", "Comisión Nacional Espacial", 2, 2,0);

        Cargar(11, "¿Cuál es el origen del golf?", "Romano", "Escocés", "Escocés", "Alemán", 2, 2,0);

        Cargar(12, "El primer circuito de \"rally\" semipermanente se hizo en...", "Pro Racing", "Circuito Costanera",
                "Parque Temático", "Centro de Villa Carlos Paz", 2, 2,0);



        Cargar(13, "Sólo hay categoría masculina en el Rugby del Carlos Paz Rugby Club?", "Falso", "Verdadero",
                "Hay una categoría femenina de rugby: adultos", null, 1, 3,0);

        Cargar(14, "¿Por qué se canceló el deporte del Pato?", "Porque era un juego muy violento",
                "Porque había poco interés del público",
                "Muerte de un gaucho famoso", "lucha de proteccionistas por el maltrato a los animales (Pato - Caballo)",
                2, 3,0);

        Cargar(15, "Se le dice copiloto al acompañante del piloto en el Rally", "Falso", "Verdadero",
                "El término copiloto, se entiende pero el término correcto es navegante", null, 1, 3,0);

        Cargar(16, "¿Cuál es el PRINCIPAL BENEFICIO del Rally en Córdoba?", "Genera un gran incremento en la economía local",
                "Gran entrenamiento para pilotos argentinos", "Le da fama y prestigio mundial a córdoba",
                "Fomenta la industria automotriz nacional", 2, 3,0);

        Cargar(17, "Está planificado que el Estadio Arena tenga un albergue para 100 deportistas", "Verdadero", "Falso",
                null, null, 1, 3,0);

        Cargar(18, "¿De qué trata el estilo libre del bicycle motocross?", "Realizar acrobacias en el aire",
                "Recorrer cornisas y edificaciones altas", "Deporte recreativo, para pasear",
                "Recorrer montañas y hacer senderos peligrosos", 2, 3,0);

        Cargar(19, "El Hotel Portal del Lago es el primer hotel de 5 estrellas de Villa Carlos Paz?", "Falso", "Verdadero",
                "Fue el primer hotel de 4 estrellas de la villa", null, 1, 3,0);

        Cargar(20," Los años lo han cambiado... ¿Qué puente de la famosa Ciudad de los puentes es?","Puente Negro",
                "Puente Las Mojarras","Puente Carretero","Puente Carena",3,3,0);

        Cargar(21,"El Shakedown es un tramo clasificatorio del Rally","Falso","Verdadero","Es un tramo de prueba técnica",
                null,1,3,0);

        Cargar(22,"Estancia Vieja fue de los primeros pueblos de la zona en poblarse, de allí su nombre","Falso","Verdadero",
                "La comuna de Estancia Vieja tiene recien 46 años. Su nombre es por el casco de la estancia, con una fachada muy antigua.",
                null,1,3,0);

        Cargar(23,"La principal característica del alfajor cordobés es que está hecho con fruta","Verdadero","Falso",
                null,null,1,3,0);


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
    int puntAcumulado = 0, correctasAcumuladas = 0, incorrectasAcumuladas = 0;
    float promedio = 0;

    public void OnClickCargarResultado(View view) {
        int puntDB = 1;
        boolean band = false;

        SQLiteDatabase db = conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + Utilidades.TABLA_PREGUNTAS, null);
        puntAcumulado = 0;
        while (cursor.moveToNext()) {
            puntDB = cursor.getInt(8);
            puntAcumulado = puntAcumulado + puntDB;
            if (puntDB == 1) incorrectasAcumuladas++;
            if (puntDB > 1) correctasAcumuladas++;
            promedio = (float) puntAcumulado / (incorrectasAcumuladas + correctasAcumuladas);

            if (puntDB == 0) {
                band = true;
            }
        }
        if (!band){
            //Toast.makeText(getApplicationContext(), "Puede pasar al resultado", Toast.LENGTH_SHORT).show();
            IrAlResultado();
        }

        if (band) {
            Toast.makeText(getApplicationContext(), "Faltan Preguntas de Responder", Toast.LENGTH_LONG).show();
        }
    }

    private void IrAlResultado() {
        Intent intent;
        intent = new Intent(MainActivity.this, ActivityFormularioParaResultado.class);

        Bundle bundle = new Bundle();
        bundle.putInt("Puntos acumulados", puntAcumulado);
        bundle.putInt("Incorrectas Acumuladas", incorrectasAcumuladas);
        bundle.putInt("Correctas Acumuladas", correctasAcumuladas);
        bundle.putFloat("Promedio",promedio);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    public void IrCodigo(View view){
        Intent intent = new Intent(MainActivity.this,ActivityCartaPresentacion_IngresoCodigo.class);
        startActivity(intent);
        finish();
    }
}























