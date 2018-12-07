package mobi.stos.nfserecife.restful;

import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mobi.stos.nfserecife.util.Util;
import org.apache.commons.codec.binary.Base64;

public class RestAuthenticationFilter implements Filter {

    public static final String AUTHENTICATION_HEADER = "Authorization";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filter) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            String authCredentials = httpServletRequest.getHeader(AUTHENTICATION_HEADER);

            boolean authenticationStatus = authenticate(request.getServletContext(), authCredentials);

            if (authenticationStatus) {
                filter.doFilter(request, response);
            } else {
                if (response instanceof HttpServletResponse) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
            }
        }
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    public boolean authenticate(ServletContext context, String authCredentials) {
        try {
            if (null == authCredentials) {
                return false;
            }

            final String encodedUserPassword = authCredentials.replaceFirst("Basic" + " ", "");
            final byte[] decodedBytes = Base64.decodeBase64(encodedUserPassword.getBytes());
            final String pwd = new String(decodedBytes, "UTF-8");

            final StringTokenizer tokenizer = new StringTokenizer(pwd, ":");
            final long cnpj = Long.parseLong(Util.onlyNumber(tokenizer.nextToken()));
            final String hash = tokenizer.nextToken();

            return cnpj == 17713221000100l && hash.equals("0944a726efc813481de483b0e5febab2");
        } catch (Exception e) {
            System.err.println("Erro ao tentar autorizar o uso do webservice");
            e.printStackTrace();
            return false;
        }
    }
}
