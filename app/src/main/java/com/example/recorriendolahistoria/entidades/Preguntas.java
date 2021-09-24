package com.example.recorriendolahistoria.entidades;

public class Preguntas {

    private Integer id;
    private String Pregunta;
    private String RespCorrecta;
    private String RespIncorrecta1;
    private String RespIncorrecta2;
    private String RespIncorrecta3;
    private Integer TipoPregunta;
    private Integer Guia;
    private Integer Puntos;

    public Preguntas(Integer id, String pregunta, String respCorrecta, String respIncorrecta1,
                     String respIncorrecta2, String respIncorrecta3, Integer tipoPregunta,
                     Integer guia, Integer puntos) {

        this.id = id;
        this.Pregunta = pregunta;
        this.RespCorrecta = respCorrecta;
        this.RespIncorrecta1 = respIncorrecta1;
        this.RespIncorrecta2 = respIncorrecta2;
        this.RespIncorrecta3 = respIncorrecta3;
        this.TipoPregunta = tipoPregunta;
        this.Guia = guia;
        this.Puntos = puntos;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPregunta() {
        return Pregunta;
    }

    public void setPregunta(String pregunta) {
        Pregunta = pregunta;
    }

    public String getRespCorrecta() {
        return RespCorrecta;
    }

    public void setRespCorrecta(String respCorrecta) {
        RespCorrecta = respCorrecta;
    }

    public String getRespIncorrecta1() {
        return RespIncorrecta1;
    }

    public void setRespIncorrecta1(String respIncorrecta1) {
        RespIncorrecta1 = respIncorrecta1;
    }

    public String getRespIncorrecta2() {
        return RespIncorrecta2;
    }

    public void setRespIncorrecta2(String respIncorrecta2) {
        RespIncorrecta2 = respIncorrecta2;
    }

    public String getRespIncorrecta3() {
        return RespIncorrecta3;
    }

    public void setRespIncorrecta3(String respIncorrecta3) {
        RespIncorrecta3 = respIncorrecta3;
    }

    public Integer getTipoPregunta() {
        return TipoPregunta;
    }

    public void setTipoPregunta(Integer tipoPregunta) {
        TipoPregunta = tipoPregunta;
    }

    public Integer getGuia() {
        return Guia;
    }

    public void setGuia(Integer guia) {
        Guia = guia;
    }

    public Integer getPuntos() {
        return Puntos;
    }

    public void setPuntos(Integer puntos) {
        Puntos = puntos;
    }
}
