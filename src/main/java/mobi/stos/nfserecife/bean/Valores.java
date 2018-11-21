/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.bean;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Weibson
 */
public class Valores implements Serializable {

    private double valorServico;
    private double deducoes;
    private double pis;
    private double cofins;
    private double inss;
    private double ir;
    private double csll;
    private double issRetido;
    private double iss;
    private double aliquota;

    @XmlElement(name = "ValorServicos")
    public double getValorServico() {
        return valorServico;
    }

    public void setValorServico(double valorServico) {
        this.valorServico = valorServico;
    }

    @XmlElement(name = "ValorDeducoes")
    public double getDeducoes() {
        return deducoes;
    }

    public void setDeducoes(double deducoes) {
        this.deducoes = deducoes;
    }

    @XmlElement(name = "ValorPis")
    public double getPis() {
        return pis;
    }

    public void setPis(double pis) {
        this.pis = pis;
    }

    @XmlElement(name = "ValorCofins")
    public double getCofins() {
        return cofins;
    }

    public void setCofins(double cofins) {
        this.cofins = cofins;
    }

    @XmlElement(name = "ValorInss")
    public double getInss() {
        return inss;
    }

    public void setInss(double inss) {
        this.inss = inss;
    }

    @XmlElement(name = "ValorIr")
    public double getIr() {
        return ir;
    }

    public void setIr(double ir) {
        this.ir = ir;
    }

    @XmlElement(name = "ValorCsll")
    public double getCsll() {
        return csll;
    }

    public void setCsll(double csll) {
        this.csll = csll;
    }

    @XmlElement(name = "IssRetido")
    public double getIssRetido() {
        return issRetido;
    }

    public void setIssRetido(double issRetido) {
        this.issRetido = issRetido;
    }

    @XmlElement(name = "ValorIss")
    public double getIss() {
        return iss;
    }

    public void setIss(double iss) {
        this.iss = iss;
    }

    @XmlElement(name = "Aliquota")
    public double getAliquota() {
        return aliquota;
    }

    public void setAliquota(double aliquota) {
        this.aliquota = aliquota;
    }

}
