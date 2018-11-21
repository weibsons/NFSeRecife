/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.transmitir;

import com.sun.net.ssl.internal.ssl.Provider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
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
import org.apache.commons.io.FileUtils;

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
        System.setProperty("javax.net.ssl.keyStore", Certs.instance().pfx);
        System.setProperty("javax.net.ssl.keyStorePassword", Certs.instance().password);

        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        System.setProperty("javax.net.ssl.trustStore", Certs.instance().trustStore);
        System.setProperty("javax.net.ssl.trueStorePassword", "changeit");
    }

    private KeyStore doKeyStorePFX(String token) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException {
        Provider provider = new Provider();
        Security.addProvider(provider);
        KeyStore ks2 = KeyStore.getInstance("pkcs12");
        char pin[] = Certs.instance().password.toCharArray();
        ks2.load(new FileInputStream(token), pin);
        return ks2;
    }

    private KeyStore doTrustKeyStore() throws Exception {
        String trustStoreFile = Certs.instance().trustStore;
        KeyStore trustStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyInput = new FileInputStream(trustStoreFile)) {
            String trustStorePassword = "changeit";
            trustStore.load(keyInput, trustStorePassword.toCharArray());
        }
        return trustStore;
    }

    public void enviar() throws Exception {

//        KeyStore keyStore = doKeyStorePFX(Certs.instance().pfx);
//        KeyStore trustStore = doTrustKeyStore();
//
//        X509SSLContextFactory sslContextFactory = new X509SSLContextFactory(keyStore, Certs.instance().password, trustStore);
//        SSLContext sslClientContext = sslContextFactory.buildSSLContext();
//
//        SslContextedSecureProtocolSocketFactory secureProtocolSocketFactory = new SslContextedSecureProtocolSocketFactory(sslClientContext);
//        Protocol.registerProtocol("https", new Protocol("https", (ProtocolSocketFactory) secureProtocolSocketFactory, 443));
        this.systemSSL();

        this.nfeXml = FileUtils.readFileToString(new File("C:\\temp\\xml_assinado.xml"), "utf-8");

        String soapEndpointUrl = "https://nfse.recife.pe.gov.br/WSNacional/nfse_v01.asmx";
        String soapAction = "http://nfse.recife.pe.gov.br/GerarNfse";

        callSoapWebService(soapEndpointUrl, soapAction);
    }

    private void createSoapEnvelope(SOAPMessage soapMessage) throws SOAPException {
        SOAPPart soapPart = soapMessage.getSOAPPart();

        String myNamespace = "nfse";
        String myNamespaceURI = "http://nfse.recife.pe.gov.br/";

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);

        /*
        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:nfse="http://nfse.recife.pe.gov.br/">
            <soapenv:Header/>
            <soapenv:Body>
               <nfse:GerarNfseRequest>
                  <nfse:inputXML>?</nfse:inputXML>
               </nfse:GerarNfseRequest>
            </soapenv:Body>
         </soapenv:Envelope>
         */
        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement soapBodyElem = soapBody.addChildElement("GerarNfseRequest", myNamespace);
        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("inputXML", myNamespace);
        soapBodyElem1.addTextNode(this.nfeXml);
    }

    private void callSoapWebService(String soapEndpointUrl, String soapAction) {
        try {
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(soapAction), soapEndpointUrl);

            System.out.println("Response SOAP Message:");
            soapResponse.writeTo(System.out);
            System.out.println();

            soapConnection.close();
        } catch (Exception e) {
            System.err.println("\nError occurred while sending SOAP Request to Server!\nMake sure you have the correct endpoint URL and SOAPAction!\n");
            e.printStackTrace();
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
