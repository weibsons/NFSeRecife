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
public class NFSePrestador {

    private String cnpj;
    private String inscricaoMunicipal;
    private String itemListaServico;
    private String codigoTributacao;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public String getItemListaServico() {
        return itemListaServico;
    }

    public void setItemListaServico(String itemListaServico) {
        this.itemListaServico = itemListaServico;
    }

    public String getCodigoTributacao() {
        return codigoTributacao;
    }

    public void setCodigoTributacao(String codigoTributacao) {
        this.codigoTributacao = codigoTributacao;
    }

}
