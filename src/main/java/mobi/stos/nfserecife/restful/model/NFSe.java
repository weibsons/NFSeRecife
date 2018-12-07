/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.restful.model;

/**
 *
 * @author Weibson
 */
public class NFSe {

    private int numero;
    private String serie;
    private int tipo;
    private String discriminacao;
    private double valor;
    private NFSePrestador prestador;
    private NFSeTomador tomador;

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDiscriminacao() {
        return discriminacao;
    }

    public void setDiscriminacao(String discriminacao) {
        this.discriminacao = discriminacao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public NFSePrestador getPrestador() {
        return prestador;
    }

    public void setPrestador(NFSePrestador prestador) {
        this.prestador = prestador;
    }

    public NFSeTomador getTomador() {
        return tomador;
    }

    public void setTomador(NFSeTomador tomador) {
        this.tomador = tomador;
    }

}
