/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.rps;

import javax.xml.bind.annotation.XmlElement;
import mobi.stos.nfserecife.bean.Rps;

/**
 *
 * @author Weibson
 */
public class RootRps {

    private Rps rps;

    public RootRps() {
    }

    public RootRps(Rps rps) {
        this.rps = rps;
    }

    @XmlElement(name = "InfRps")
    public Rps getRps() {
        return rps;
    }

    public void setRps(Rps rps) {
        this.rps = rps;
    }
    
}
