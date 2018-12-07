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
@XmlType(propOrder = {"valorServico", "deducoes", "pis", "cofins", "inss", "ir", "csll", "issRetido", "iss", "aliquota"})
public class Valores implements Serializable {

    private String valorServico;
    private String deducoes;
    private String pis;
    private String cofins;
    private String inss;
    private String ir;
    private String csll;
    private int issRetido;
    private String iss;
    private String aliquota;

    @XmlElement(name = "ValorServicos")
    public String getValorServico() {
        return valorServico;
    }

    public void setValorServico(String valorServico) {
        this.valorServico = valorServico;
    }

    @XmlElement(name = "ValorDeducoes")
    public String getDeducoes() {
        return deducoes;
    }

    public void setDeducoes(String deducoes) {
        this.deducoes = deducoes;
    }

    @XmlElement(name = "ValorPis")
    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    @XmlElement(name = "ValorCofins")
    public String getCofins() {
        return cofins;
    }

    public void setCofins(String cofins) {
        this.cofins = cofins;
    }

    @XmlElement(name = "ValorInss")
    public String getInss() {
        return inss;
    }

    public void setInss(String inss) {
        this.inss = inss;
    }

    @XmlElement(name = "ValorIr")
    public String getIr() {
        return ir;
    }

    public void setIr(String ir) {
        this.ir = ir;
    }

    @XmlElement(name = "ValorCsll")
    public String getCsll() {
        return csll;
    }

    public void setCsll(String csll) {
        this.csll = csll;
    }

    @XmlElement(name = "IssRetido")
    public int getIssRetido() {
        return issRetido;
    }

    public void setIssRetido(int issRetido) {
        this.issRetido = issRetido;
    }

    @XmlElement(name = "ValorIss")
    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    @XmlElement(name = "Aliquota")
    public String getAliquota() {
        return aliquota;
    }

    public void setAliquota(String aliquota) {
        this.aliquota = aliquota;
    }

}
