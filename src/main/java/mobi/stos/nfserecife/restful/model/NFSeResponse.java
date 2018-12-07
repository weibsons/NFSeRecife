/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mobi.stos.nfserecife.restful.model;

/**
 *
 * @author Weibson
 */
public class NFSeResponse {

    private String request;
    private String response;

    public NFSeResponse() {
    }

    public NFSeResponse(String request, String response) {
        this.request = request;
        this.response = response;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

}
