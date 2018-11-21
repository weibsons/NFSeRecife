/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.rps;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Weibson
 */
@XmlRootElement(name = "GerarNfseEnvio")
public class GerarNfseEnvio implements Serializable {

    @XmlAttribute
    public String getXmlns() {
        return "http://nfse.recife.pe.gov.br/WSNacional/XSD/1/nfse_recife_v01.xsd";
    }

    private RootRps root;

    public GerarNfseEnvio() {
    }

    public GerarNfseEnvio(RootRps root) {
        this.root = root;
    }

    @XmlElement(name = "Rps")
    public RootRps getRoot() {
        return root;
    }

    public void setRoot(RootRps root) {
        this.root = root;
    }

}
