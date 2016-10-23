package com.raykgeneer.evilgeniuses.blueartstudio;

public class TatuadoresCardView {

    String Identificador;
    String Valor;

    public TatuadoresCardView(String Identificador, String Valor) {
        this.Identificador = Identificador;
        this.Valor = Valor;
    }

    public String getIdentificador() {
        return Identificador;
    }

    public void setIdentificador(String identificador) {
        Identificador = identificador;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }
}
