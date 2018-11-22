/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Weibson
 */
public class NFeBuildAllCacerts {

    private static final String JSSECACERTS = System.getProperty("java.home") + 
            File.separatorChar + "lib" + 
            File.separatorChar + "security" +
            File.separatorChar + "cacerts";
    private static final int TIMEOUT_WS = 30;

    public static void main(String[] args) {
        try {
            char[] passphrase = "changeit".toCharArray();
            File file = new File(JSSECACERTS);
            
            info("| Loading KeyStore " + file + "...");
            KeyStore ks;
            try (InputStream in = new FileInputStream(file)) {
                ks = KeyStore.getInstance(KeyStore.getDefaultType());
                ks.load(in, passphrase);
            }

            get("nfse.recife.pe.gov.br", 443, ks);
            get("nfe.sefaz.pe.gov.br", 443, ks);

            File cafile = new File(JSSECACERTS);
            try (OutputStream out = new FileOutputStream(cafile)) {
                ks.store(out, passphrase);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void get(String host, int port, KeyStore ks) throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(ks);
        X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];
        SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
        context.init(null, new TrustManager[]{tm}, null);
        SSLSocketFactory factory = context.getSocketFactory();

        info("| Opening connection to " + host + ":" + port + "...");
        SSLSocket socket = (SSLSocket) factory.createSocket(host, port);
        socket.setSoTimeout(TIMEOUT_WS * 1000);
        try {
            info("| Starting SSL handshake...");
            socket.startHandshake();
            socket.close();
            info("| No errors, certificate is already trusted");
        } catch (SSLHandshakeException e) {
            /**
             * PKIX path building failed:
             * sun.security.provider.certpath.SunCertPathBuilderException:
             * unable to find valid certification path to requested target Não
             * tratado, pois sempre ocorre essa exceção quando o cacerts nao
             * esta gerado.
             */
        } catch (SSLException e) {
            error("| " + e.toString());
        }

        X509Certificate[] chain = tm.chain;
        if (chain == null) {
            info("| Could not obtain server certificate chain");
        }

        info("| Server sent " + chain.length + " certificate(s):");
        MessageDigest sha1 = MessageDigest.getInstance("SHA1");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        for (int i = 0; i < chain.length; i++) {
            X509Certificate cert = chain[i];
            sha1.update(cert.getEncoded());
            md5.update(cert.getEncoded());

            String alias = host + "-" + (i);
            ks.setCertificateEntry(alias, cert);
            info("| Added certificate to keystore '" + JSSECACERTS + "' using alias '" + alias + "'");
        }
    }

    private static class SavingTrustManager implements X509TrustManager {

        private final X509TrustManager tm;
        private X509Certificate[] chain;

        SavingTrustManager(X509TrustManager tm) {
            this.tm = tm;
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            throw new UnsupportedOperationException();
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {
            this.chain = chain;
            tm.checkServerTrusted(chain, authType);
        }
    }

    private static void info(String log) {
        System.out.println("INFO: " + log);
    }

    private static void error(String log) {
        System.out.println("ERROR: " + log);
    }

}
