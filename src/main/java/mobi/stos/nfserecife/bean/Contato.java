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
public class Contato {

    private String email;

    @XmlElement(name = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
