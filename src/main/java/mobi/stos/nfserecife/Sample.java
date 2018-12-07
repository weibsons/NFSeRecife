/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife;

import java.text.DecimalFormat;

/**
 *
 * @author Weibson
 */
public class Sample {
    
    public static void main(String[] args) {
        
        
        System.out.println(0.65 / 100);
        System.out.println(new DecimalFormat("0.00").format(113.65 / 100).replace(",", "."));
        
    }
    
}
