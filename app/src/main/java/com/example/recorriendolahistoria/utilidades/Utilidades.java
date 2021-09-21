package com.example.recorriendolahistoria.utilidades;

public class Utilidades {

    //Constantes campos de tabla PREGUNTAS
    public static final String TABLA_PREGUNTAS = "preguntas";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_PREGUNTA = "Pregunta";
    public static final String CAMPO_RESP_CORRECTA = "RespCorrecta";
    public static final String CAMPO_RESP_INCORRECTA1 = "RespIncorrecta1";
    public static final String CAMPO_RESP_INCORRECTA2 = "RespIncorrecta2";
    public static final String CAMPO_RESP_INCORRECTA3 = "RespIncorrecta3";
    public static final String CAMPO_TIPO_PREGUNTA = "TipoPregunta";
    public static final String CAMPO_GUIA = "Guia";
    public static final String CAMPO_PUNTOS = "Puntos";


    public static final String CREAR_TABLA_PREGUNTAS = "CREATE TABLE " + TABLA_PREGUNTAS + " " +
            "(" + CAMPO_ID + " INTEGER, " + CAMPO_PREGUNTA + " TEXT, " + CAMPO_RESP_CORRECTA + " TEXT, " + CAMPO_RESP_INCORRECTA1 + " TEXT, "
            + CAMPO_RESP_INCORRECTA2 + " TEXT, " + CAMPO_RESP_INCORRECTA3 + " TEXT, " + CAMPO_TIPO_PREGUNTA +
            " INTEGER, " + CAMPO_GUIA + " INTEGER, " + CAMPO_PUNTOS + " INTEGER)";

}

