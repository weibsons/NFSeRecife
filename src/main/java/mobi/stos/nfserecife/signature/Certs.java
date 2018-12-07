/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.signature;

import mobi.stos.nfserecife.NFSe;

/**
 *
 * @author Weibson
 */
public class Certs {

    private static Certs instance;

    public final String trustStore;
    public final String pfx;
    public final String sign;
    public final String password;

    public static Certs instance() {
        if (instance == null) {
            instance = new Certs();
        }
        return instance;
    }

    public Certs() {
        String javaHomePath = System.getProperty("java.home");
        String keystore = javaHomePath + "/lib/security/cacerts";
        
        this.pfx = NFSe.class.getClassLoader().getResource("certs/04986320.pfx").getPath();
        this.sign = NFSe.class.getClassLoader().getResource("certs/homolog_cert.pfx").getPath();
        this.trustStore = NFSe.class.getClassLoader().getResource("certs/cacerts").getPath();
        this.password = "04986320";
    }

}
