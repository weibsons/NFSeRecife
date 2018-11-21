/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.bean;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Weibson
 */
public class CpfCnpj {

    private String cnpj;
    private String cpf;

    public CpfCnpj() {
    }

    public CpfCnpj(String cnpj, String cpf) {
        this.cnpj = cnpj;
        this.cpf = cpf;
    }

    @XmlElement(name = "Cnpj")
    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    @XmlElement(name = "Cpf")
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
