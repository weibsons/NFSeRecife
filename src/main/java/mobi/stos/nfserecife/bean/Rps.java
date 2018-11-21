/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Weibson
 */
public class Rps implements Serializable {

    private Identificacao identificacao;
    private Date dataEmissao = new Date();
    private int naturezaOperacao;
    private int optanteSimplesNacional;
    private int incentivadorCultural;
    private int status;
    private Servico servico;
    private Prestador prestador;
    private Tomador tomador;

    @XmlAttribute
    public String getXmlns() {
        return "http://www.abrasf.org.br/ABRASF/arquivos/nfse.xsd";
    }

    @XmlAttribute(name = "Id")
    public String getId() {
        return "Rps_5";
    }

    @XmlElement(name = "IdentificacaoRps")
    public Identificacao getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(Identificacao identificacao) {
        this.identificacao = identificacao;
    }

    @XmlElement(name = "DataEmissao", type = String.class)
    public String getDataEmissao() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(dataEmissao);
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    @XmlElement(name = "NaturezaOperacao")
    public int getNaturezaOperacao() {
        return naturezaOperacao;
    }

    public void setNaturezaOperacao(int naturezaOperacao) {
        this.naturezaOperacao = naturezaOperacao;
    }

    @XmlElement(name = "OptanteSimplesNacional")
    public int getOptanteSimplesNacional() {
        return optanteSimplesNacional;
    }

    public void setOptanteSimplesNacional(int optanteSimplesNacional) {
        this.optanteSimplesNacional = optanteSimplesNacional;
    }

    @XmlElement(name = "IncentivadorCultural")
    public int getIncentivadorCultural() {
        return incentivadorCultural;
    }

    public void setIncentivadorCultural(int incentivadorCultural) {
        this.incentivadorCultural = incentivadorCultural;
    }

    @XmlElement(name = "Status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @XmlElement(name = "Servico")
    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    @XmlElement(name = "Prestador")
    public Prestador getPrestador() {
        return prestador;
    }

    public void setPrestador(Prestador prestador) {
        this.prestador = prestador;
    }

    @XmlElement(name = "Tomador")
    public Tomador getTomador() {
        return tomador;
    }

    public void setTomador(Tomador tomador) {
        this.tomador = tomador;
    }

}
