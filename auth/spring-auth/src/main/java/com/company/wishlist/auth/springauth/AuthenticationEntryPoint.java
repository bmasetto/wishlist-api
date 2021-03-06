package com.company.wishlist.auth.springauth;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authEx) throws IOException {
        response.addHeader("WWW-Authenticate", "Basic realm=" + getRealmName());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        var writer = response.getWriter();
        writer.println("{}");
    }

    @Override
    public void afterPropertiesSet() {
        setRealmName("wish-list");
        super.afterPropertiesSet();
    }
}
