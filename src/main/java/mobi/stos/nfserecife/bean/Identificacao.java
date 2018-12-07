/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author Weibson
 */
@XmlType(propOrder = {"numero", "serie", "tipo"})
public class Identificacao implements Serializable {

    private int numero;
    private String serie;
    private int tipo;

    @XmlElement(name = "Serie")
    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    @XmlElement(name = "Numero")
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @XmlElement(name = "Tipo")
    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

}
