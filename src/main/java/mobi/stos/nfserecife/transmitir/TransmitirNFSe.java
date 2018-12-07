/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.transmitir;

import java.security.Security;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import mobi.stos.nfserecife.signature.Certs;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Weibson
 */
public class TransmitirNFSe {

    private String nfeXml;

    private void systemSSL() {
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        System.setProperty("sun.security.ssl.allowUnsafeRenegotiation ", "true");

        System.clearProperty("javax.net.ssl.keyStore");
        System.clearProperty("javax.net.ssl.keyStorePassword");
        System.clearProperty("javax.net.ssl.trustStore");

        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");
        System.setProperty("javax.net.ssl.keyStore", Certs.instance().sign);
        System.setProperty("javax.net.ssl.keyStorePassword", Certs.instance().password);

        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStore", Certs.instance().trustStore);
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
    }

    public String enviar(String xml) throws Exception {
        this.systemSSL();

        this.nfeXml = xml;

        String soapEndpointUrl = "https://nfse.recife.pe.gov.br/WSNacional/nfse_v01.asmx";
        String soapAction = "http://nfse.recife.pe.gov.br/GerarNfse";

        return callSoapWebService(soapEndpointUrl, soapAction);
    }

    private void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "nfse";
        String myNamespaceURI = "http://nfse.recife.pe.gov.br/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GerarNfseRequest", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("inputXML", myNamespace);
        soapBodyElem1.addTextNode(this.nfeXml);
    }

    private String callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            SOAPBody soapBody = soapResponse.getSOAPBody();
            NodeList nodes = soapBody.getElementsByTagName("outputXML");

            String outputXML;
            Node node = nodes.item(0);
            outputXML = node != null ? node.getTextContent() : "";
            soapConnection.close();

            return outputXML;
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
            return null;
        }
    }

    private SOAPMessage createSOAPRequest(String soapAction) throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();

        createSoapEnvelope(soapMessage);

        MimeHeaders headers = soapMessage.getMimeHeaders();
        headers.addHeader("SOAPAction", soapAction);
        soapMessage.saveChanges();

        System.out.println("Request SOAP Message:");
        soapMessage.writeTo(System.out);
        System.out.println("\n");

        return soapMessage;
    }

}
