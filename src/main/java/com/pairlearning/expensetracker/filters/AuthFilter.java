package com.pairlearning.expensetracker.filters;

import com.pairlearning.expensetracker.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String authHeader = httpRequest.getHeader("Authorization");
        if(authHeader != null) {
            String[] authHeaderArr = authHeader.split("Bearer ");
            if(authHeaderArr.length > 1 && authHeaderArr[1] != null) {
                String token = authHeaderArr[1];
                try {
                    //target is to fetch the claims present in the payload of the token by providing api key to the parser
                    //pass token to parseClaimsJws and get the body
                    //finally the claims contain all user details (userId, email, firstName and lastName)
                    Claims claims = Jwts.parser().setSigningKey(Constants.API_SECRET_KEY)
                            .parseClaimsJws(token).getBody();
                    //modify our request object and add the userId attribute using the request's setAttribute
                    //this way, throughout the request processing, we'll have the userId of the current user attached to the request object
                    //once the request pass through the filter, we can access userId from anywhere in our code
                    httpRequest.setAttribute("userId", Integer.parseInt(claims.get("userId").toString()));
                }catch (Exception e) {
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "invalid/expired token");
                    return;
                }
            } else {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be Bearer [token]");
                return;
            }
        } else {
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(), "Authorization token must be provided");
            return;
        }
        //doFilter will continue the processing
        filterChain.doFilter(servletRequest, servletResponse);
    }
}