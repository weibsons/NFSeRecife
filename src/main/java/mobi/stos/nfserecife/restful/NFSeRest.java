/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.restful;

import java.io.File;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXB;
import mobi.stos.nfserecife.bean.Contato;
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
import mobi.stos.nfserecife.restful.model.NFSe;
import mobi.stos.nfserecife.restful.model.NFSeResponse;
import mobi.stos.nfserecife.rps.GerarNfseEnvio;
import mobi.stos.nfserecife.rps.RootRps;
import mobi.stos.nfserecife.signature.AssinarXMLsCertfificadoA1;
import mobi.stos.nfserecife.signature.Certs;
import mobi.stos.nfserecife.transmitir.TransmitirNFSe;
import mobi.stos.nfserecife.util.Util;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Weibson
 */
@Path("/nfse")
public class NFSeRest {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response gerar(NFSe nfse) {
        try {
            Rps rps = new Rps();

            //<editor-fold defaultstate="collapsed" desc="Identificação">
            Identificacao identificacao = new Identificacao();
            identificacao.setNumero(nfse.getNumero());
            identificacao.setSerie(nfse.getSerie());
            identificacao.setTipo(nfse.getTipo());
            rps.setIdentificacao(identificacao);
            //</editor-fold>

            rps.setNaturezaOperacao(NaturezaOperacaoEnum.TRIBUTACAO_MUNICIPIO.getValue());
            rps.setOptanteSimplesNacional(SimNao.NAO.getValue());
            rps.setIncentivadorCultural(SimNao.NAO.getValue());
            rps.setStatus(Status.NORMAL.getValue());

            //<editor-fold defaultstate="collapsed" desc="Serviços">
            Servico servico = new Servico();
            servico.setItemListaServico(Util.onlyNumber(nfse.getPrestador().getItemListaServico())); // Feiras, exposições, congressos e congêneres.
            servico.setCodigoTributacaoMunicipio(Util.onlyNumber(nfse.getPrestador().getCodigoTributacao())); // Serviços de organização de feiras, congressos, exposições e festas
            servico.setDiscriminacao(Util.unaccent(nfse.getDiscriminacao()));

            //<editor-fold defaultstate="collapsed" desc="valores ...">
            DecimalFormat df = new DecimalFormat("0.00");

            Valores valores = new Valores();
            valores.setIssRetido(SimNao.NAO.getValue());
            valores.setValorServico(df.format(nfse.getValor()).replace(",", "."));
            servico.setValores(valores);
            //</editor-fold>
            rps.setServico(servico);
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Prestador">
            Prestador prestador = new Prestador();
            prestador.setCnpj(Util.onlyNumber(nfse.getPrestador().getCnpj()));
            prestador.setInscricaoMunicipal(nfse.getPrestador().getInscricaoMunicipal());
            rps.setPrestador(prestador);
            //</editor-fold>

            //<editor-fold defaultstate="collapsed" desc="Tomador">
            Tomador tomador = new Tomador();
            tomador.setRazaoSocial(Util.unaccent(nfse.getTomador().getNome()));
            IdentificacaoTomador identificacaoTomador = new IdentificacaoTomador();
            identificacaoTomador.setCpfCnpj(new CpfCnpj(null, Util.onlyNumber(nfse.getTomador().getCpfCnpj())));
            tomador.setIdentificacaoTomador(identificacaoTomador);

            Endereco endereco = new Endereco();
            endereco.setBairro(Util.unaccent(nfse.getTomador().getBairro()));
            endereco.setCep(Util.onlyNumber(nfse.getTomador().getCep()));
            endereco.setComplemento(nfse.getTomador().getComplemento());
            endereco.setEndereco(Util.unaccent(nfse.getTomador().getLogradouro()));
            endereco.setNumero(Util.unaccent(nfse.getTomador().getNumero()));
            endereco.setUf(Util.unaccent(nfse.getTomador().getUf()));
            endereco.setCodigoMunicipio(nfse.getTomador().getCodigoMunicipio());
            tomador.setEndereco(endereco);

            if (StringUtils.isNotBlank(nfse.getTomador().getEmail())) {
                Contato contato = new Contato();
                contato.setEmail(nfse.getTomador().getEmail());
                tomador.setContato(contato);
            }

            rps.setTomador(tomador);
            //</editor-fold>

            GerarNfseEnvio gerarNfseEnvio = new GerarNfseEnvio();
            gerarNfseEnvio.setRoot(new RootRps(rps));

            StringWriter sw = new StringWriter();
            JAXB.marshal(gerarNfseEnvio, sw);
            String xmlString = sw.toString();

            AssinarXMLsCertfificadoA1 assinarXMLsCertfificadoA1 = new AssinarXMLsCertfificadoA1();
            String xmlEnviNFeAssinado = assinarXMLsCertfificadoA1.assinaEnviNFe(xmlString, Certs.instance().pfx, Certs.instance().password);

            //FileUtils.write(new File("c:\\temp\\xml_assinado.xml"), xmlEnviNFeAssinado, Charset.defaultCharset());
            TransmitirNFSe transmitir = new TransmitirNFSe();
            //String xmlResponse = "<xml><resposta>Sucesso carai!</resposta></xml>";
            String xmlResponse = transmitir.enviar(xmlEnviNFeAssinado);
            //FileUtils.write(new File("c:\\temp\\resposta.xml"), xmlResponse, Charset.defaultCharset());

            return Response.ok(new NFSeResponse(xmlEnviNFeAssinado, xmlResponse)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
    }

}
