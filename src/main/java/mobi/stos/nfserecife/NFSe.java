/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import javax.xml.bind.JAXB;
import mobi.stos.nfserecife.bean.CpfCnpj;
import mobi.stos.nfserecife.bean.Endereco;
import mobi.stos.nfserecife.bean.Identificacao;
import mobi.stos.nfserecife.bean.IdentificacaoTomador;
import mobi.stos.nfserecife.bean.Prestador;
import mobi.stos.nfserecife.bean.Rps;
import mobi.stos.nfserecife.bean.Servico;
import mobi.stos.nfserecife.bean.Tomador;
import mobi.stos.nfserecife.bean.Valores;
import mobi.stos.nfserecife.enumm.NaturezaOperacaoEnum;
import mobi.stos.nfserecife.enumm.SimNao;
import mobi.stos.nfserecife.enumm.Status;
import mobi.stos.nfserecife.rps.GerarNfseEnvio;
import mobi.stos.nfserecife.rps.RootRps;
import mobi.stos.nfserecife.signature.AssinarXMLsCertfificadoA1;
import mobi.stos.nfserecife.signature.Certs;
import mobi.stos.nfserecife.transmitir.TransmitirNFSe;
import mobi.stos.nfserecife.util.Util;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Weibson
 */
public class NFSe {

    public static void main(String[] args) throws IOException, Exception {
        Rps rps = new Rps();

        //<editor-fold defaultstate="collapsed" desc="Identificação">
        Identificacao identificacao = new Identificacao();
        identificacao.setNumero(100);
        identificacao.setSerie("1");
        identificacao.setTipo(1);
        rps.setIdentificacao(identificacao);
        //</editor-fold>

        rps.setNaturezaOperacao(NaturezaOperacaoEnum.TRIBUTACAO_MUNICIPIO.getValue());
        rps.setOptanteSimplesNacional(SimNao.NAO.getValue());
        rps.setIncentivadorCultural(SimNao.NAO.getValue());
        rps.setStatus(Status.NORMAL.getValue());

        //<editor-fold defaultstate="collapsed" desc="Serviços">
        Servico servico = new Servico();
        servico.setItemListaServico(Util.onlyNumber("12.08")); // Feiras, exposições, congressos e congêneres.
        servico.setCodigoTributacaoMunicipio(Util.onlyNumber("8230-0/01")); // Serviços de organização de feiras, congressos, exposições e festas
        servico.setDiscriminacao(Util.unaccent("NOTA FISCAL DE TESTE"));

        //<editor-fold defaultstate="collapsed" desc="valores ...">
        DecimalFormat df = new DecimalFormat("0.00");

        double valorServico = 1;

        Valores valores = new Valores();
        valores.setIssRetido(SimNao.NAO.getValue());
        valores.setValorServico(df.format(valorServico).replace(",", "."));
        servico.setValores(valores);
        //</editor-fold>
        rps.setServico(servico);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Prestador">
        Prestador prestador = new Prestador();
        prestador.setCnpj(Util.onlyNumber("17.713.221/0001-00"));
        prestador.setInscricaoMunicipal("4905920");
        rps.setPrestador(prestador);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Tomador">
        Tomador tomador = new Tomador();
        tomador.setRazaoSocial(Util.unaccent("Weibson S Santos"));
        IdentificacaoTomador identificacaoTomador = new IdentificacaoTomador();
        identificacaoTomador.setCpfCnpj(new CpfCnpj(null, Util.onlyNumber("07375446431")));
        tomador.setIdentificacaoTomador(identificacaoTomador);

        Endereco endereco = new Endereco();
        endereco.setBairro(Util.unaccent("Madalena"));
        endereco.setCep(Util.onlyNumber("50610-180"));
        endereco.setComplemento("Apto 1102");
        endereco.setEndereco(Util.unaccent("Rua Professor José Torres Pires"));
        endereco.setNumero(Util.unaccent("47"));
        endereco.setUf(Util.unaccent("PE"));
        endereco.setCodigoMunicipio("2611606");
        tomador.setEndereco(endereco);
        rps.setTomador(tomador);
        //</editor-fold>

        GerarNfseEnvio gerarNfseEnvio = new GerarNfseEnvio();
        gerarNfseEnvio.setRoot(new RootRps(rps));

        StringWriter sw = new StringWriter();
        JAXB.marshal(gerarNfseEnvio, sw);
        String xmlString = sw.toString();
        
        AssinarXMLsCertfificadoA1 assinarXMLsCertfificadoA1 = new AssinarXMLsCertfificadoA1();
        String xmlEnviNFeAssinado = assinarXMLsCertfificadoA1.assinaEnviNFe(xmlString, Certs.instance().pfx, Certs.instance().password);
        
        FileUtils.write(new File("c:\\temp\\xml_assinado.xml"), xmlEnviNFeAssinado, Charset.defaultCharset());

        TransmitirNFSe transmitir = new TransmitirNFSe();
        //transmitir.enviar(xmlEnviNFeAssinado);

    }

}
