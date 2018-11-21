/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.util;

import java.io.File;
import java.io.FileWriter;
import java.text.Normalizer;

/**
 *
 * @author Weibson
 */
public class Util {

    public static String onlyNumber(String s) {
        StringBuilder builder = new StringBuilder();
        if (s != null) {
            for (int i = 0; i < s.length(); i++) {
                if (Character.isDigit(s.charAt(i))) {
                    builder.append(s.charAt(i));
                }
            }
        }
        return builder.toString();
    }

    public static String unaccent(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public static void writeFile(String filename, String content) throws Exception {
        /*
         * Cria o Diretório caso necessário
         */
        String[] dir = filename.split("/");
        String diretorio = "";
        for (int i = 0; i < (dir.length - 1); i++) {
            diretorio = dir[i] + "/";
        }

        if (!diretorio.equals("")) {
            File w = new File(diretorio);
            w.mkdir();
        }

        /*
         * Cria o Arquivo
         */
        File x = new File(filename);
        if (x.exists()) {
            x.delete();
        }

        String text = content;
        byte[] bytes = text.getBytes("UTF8");
        String newText = new String(bytes);
        content = newText;

        try (FileWriter w = new FileWriter(filename)) {
            w.write(content);
            w.close();
        }
    }

    /**
     * M&eacute;todo para escrever um arquivo em algum local do
     * sistema/servidor. Testa se existe o arquivo, em caso de existir um
     * arquivo com o mesmo nome sobreescreve a informa&ccedil;&otilde;es pela
     * nova enviada.
     *
     * @param filename String Diret&oacute;rio + Nome do arquivo onde deseja
     * salvar
     * @param content String Conte&uacute;do que deseja salvar
     * @param charset String Definie qual ser&aacute; ser&aacute; a
     * codifica&ccedil;&atilde;o do arquivo.
     * @throws java.lang.Exception
     */
    public static void writeFile(String filename, String content, String charset) throws Exception {
        /*
         * Cria o Diretório caso necessário
         */
        String[] dir = filename.split("/");
        String diretorio = "";
        for (int i = 0; i < (dir.length - 1); i++) {
            diretorio = dir[i] + "/";
        }

        if (!diretorio.equals("")) {
            File w = new File(diretorio);
            w.mkdir();
        }

        /*
         * Cria o Arquivo
         */
        File x = new File(filename);
        if (x.exists()) {
            x.delete();
        }

        String text = content;
        byte[] bytes = text.getBytes(charset.toUpperCase());
        String newText = new String(bytes);
        content = newText;

        try (FileWriter w = new FileWriter(filename)) {
            w.write(content);
            w.close();
        }
    }

}
