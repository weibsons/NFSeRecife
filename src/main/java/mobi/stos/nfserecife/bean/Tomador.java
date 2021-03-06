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
@XmlType(propOrder = {"identificacaoTomador", "razaoSocial", "endereco", "contato"})
public class Tomador implements Serializable {

    private IdentificacaoTomador identificacaoTomador;
    private String razaoSocial;
    private Endereco endereco;
    private Contato contato;

    @XmlElement(name = "IdentificacaoTomador")
    public IdentificacaoTomador getIdentificacaoTomador() {
        return identificacaoTomador;
    }

    public void setIdentificacaoTomador(IdentificacaoTomador identificacaoTomador) {
        this.identificacaoTomador = identificacaoTomador;
    }

    @XmlElement(name = "RazaoSocial")
    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    @XmlElement(name = "Endereco")
    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    @XmlElement(name = "Contato")
    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

}
