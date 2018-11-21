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
public enum SimNao {

    SIM(1),
    NAO(2);
    private final int value;

    private SimNao(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
