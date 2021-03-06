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

import java.text.DecimalFormat;
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

        Cargar(1, "??Cu??les son los tres puntos de desarrollo que impulsaron a la Ciudad de Villa Carlos Paz?",
                "El automovilismo, la noche y los teatros", "El automovilismo, las rutas y los hoteles",
                "La noche, los hoteles y el lago San Roque ", "El automovilismo, el lago y los teatros", 2, 1,0);

        Cargar(2, "??Qu??en creo la \"Semana de la Velocidad\"?", "Alcides Raies", "Jorge Descotte.",
                "Oscar Alfredo Galvez", "Martin Cristi", 2, 1,0);

        Cargar(3,"En 1950 se corre la primera carrera en Villa Carlos Paz","Falso","Verdadero","Se corre en el a??o 1958"
                ,null,1,1,0);

        Cargar(4, "??Qu??en era Mart??n Christie?", "Navegante", "Piloto", "Coleccionista",
                "Periodista deportivo", 2, 1,0);

        Cargar(5, "??De qu??en es el motor que se encuentra dentro del bar H??roes? ", "Pechito Lopez", "Colo Rosso",
                "Valentino Rossi", "Agust??n Cannapino", 2, 1,0);



        Cargar(6,"El observatorio Astron??mico de C??rdoba es el m??s grande de Sudam??rica","Falso","Verdadero",
                "El m??s grande se encuentra al norte de Chile",
                null,1,2,0);

        Cargar(7, "Los autos que realizaron la \" Carrera del Observatorio \" eran...", "Fiat 1", "Fiat 600", "Fiat 125",
                "Fiat 128", 2, 2,0);

        Cargar(8, "??Qu?? mineral es el que se encuentra en la imagen?", "Feldespato", "Mica", "Berilio",
                "Cuarzo", 3, 2,0);

        Cargar(9, "El mini golf comenz?? como un deporte ??nicamente femenino", "Verdadero", "Falso", null,
                null, 1, 2,0);

        Cargar(10, "??Qu?? representan las siglas CONAE?", "Comisi??n Nacional de Actividades Espaciales",
                "Complejo Nacional Espacial", "Centro Nacional Espacial", "Comisi??n Nacional Espacial", 2, 2,0);

        Cargar(11, "??Cu??l es el origen del golf?",
                "Romano", "Franc??s", "Escoc??s", "Alem??n", 2, 2,0);

        Cargar(12, "El primer circuito de \"Rally\" semipermanente se hizo en...", "Pro Racing",
                "Circuito Costanera","Parque Tem??tico", "Centro de Villa Carlos Paz", 2, 2,0);



        Cargar(13, "S??lo hay categor??a masculina en el Rugby del \"Carlos Paz Rugby Club\" ", "Falso", "Verdadero",
                "Hay una categor??a femenina de rugby: adultos", null, 1, 3,0);

        Cargar(14, "??Por qu?? se cancel?? el deporte del Pato?", "Porque era un juego muy violento",
                "Porque hab??a poco inter??s del p??blico",
                "Muerte de un gaucho famoso", "Lucha de proteccionistas por el maltrato a los animales (Pato - Caballo)",
                2, 3,0);

        Cargar(15, "Se le dice copiloto al acompa??ante del piloto en el Rally", "Falso", "Verdadero",
                "El t??rmino copiloto, se entiende pero el t??rmino correcto es navegante", null, 1, 3,0);

        Cargar(16, "??Cu??l es el PRINCIPAL BENEFICIO del Rally en C??rdoba?", "Genera un gran incremento en la econom??a local",
                "Gran entrenamiento para pilotos argentinos", "Le da fama y prestigio mundial a c??rdoba",
                "Fomenta la industria automotriz nacional", 2, 3,0);

        Cargar(17, "Est?? planificado que el Estadio Arena tenga un albergue para 100 deportistas", "Verdadero", "Falso",
                null, null, 1, 3,0);

        Cargar(18, "??De qu?? trata el Estilo Libre del Bicycle Motocross (BMX)?", "Realizar acrobacias en el aire",
                "Recorrer cornisas y edificaciones altas", "Deporte recreativo, para pasear",
                "Recorrer monta??as y hacer senderos peligrosos", 2, 3,0);

        Cargar(19, "El Hotel Portal del Lago es el primer hotel de 5 estrellas de Villa Carlos Paz?", "Falso", "Verdadero",
                "Fue el primer hotel de 4 estrellas de la villa", null, 1, 3,0);

        Cargar(20," Los a??os lo han cambiado... ??Qu?? puente de la famosa \"Ciudad de los puentes\" es?","Puente Negro",
                "Puente Las Mojarras","Puente Carretero","Puente Carena",3,3,0);

        Cargar(21,"El Shakedown es un tramo clasificatorio del Rally","Falso","Verdadero",
                "Es un tramo de prueba t??cnica", null,1,3,0);

        Cargar(22,"Estancia Vieja fue de los primeros pueblos de la zona en poblarse, de all?? su nombre","Falso",
                "Verdadero",
                "Su nombre es por el casco de la estancia, con una fachada muy antigua.",
                null,1,3,0);

        Cargar(23,"La principal caracter??stica del alfajor cordob??s es que est?? hecho con fruta","Verdadero","Falso",
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























