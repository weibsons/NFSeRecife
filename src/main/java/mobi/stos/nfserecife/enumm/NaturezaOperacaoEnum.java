/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.enumm;

/**
 *
 * @author Weibson
 */
public enum NaturezaOperacaoEnum {

    TRIBUTACAO_MUNICIPIO(1),
    TRIBUTACAO_FORA_MUNICIPIO(2),
    ISENCAO(3),
    IMUNE(4),
    EXIBILIDADE_SUSPENSA_DECISAO_JUDICIAL(5),
    EXIBILIDADE_SUSPENSA_PROCEDIMENTO_ADMINISTRATIVO(6);
    private final int value;

    private NaturezaOperacaoEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
