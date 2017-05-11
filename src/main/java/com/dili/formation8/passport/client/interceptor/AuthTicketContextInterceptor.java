package com.dili.formation8.passport.client.interceptor;

import com.dili.formation8.passport.AuthTicket;
import com.dili.formation8.passport.AuthenticationUtil;
import com.dili.formation8.passport.client.cookie.CookieUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@PropertySource("classpath:passport.properties")
public class AuthTicketContextInterceptor extends HandlerInterceptorAdapter {
    @Value("${passport.login.cookie.name:_f8_}")
    protected String authCookieName = "_f8_";
    @Autowired
    protected CookieUtils cookieUtils;
    @Value("${passport.login.auth.key:E812B0CC2607104AB9942EE22666DE23}")
    protected String authenticationKey;
    private Logger log = LoggerFactory.getLogger(AuthTicketContextInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        parseAuthTicket(request);
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    private AuthTicket parseAuthTicket(HttpServletRequest request) {
        if (this.authCookieName == null) {
            log.debug("dotnetAuthCookieName is null!");
        }
        String cookieValue = this.cookieUtils.getCookieValue(request, this.authCookieName);
        if (StringUtils.isNotBlank(cookieValue)) {
            AuthTicket ticket = null;
            try {
                ticket = AuthenticationUtil.getFormsAuthenticationTicket(cookieValue, this.authenticationKey);
                ticket.setCookieValue(cookieValue);
                log.debug(ticket.toString());
            } catch (Exception e) {
                log.error("decrypt dotnet cookie error!", e);
            }
            if ((ticket != null) && (ticket.get_uid() > 0 && !ticket.isExpired())) {
                AuthTicket.setTicket(ticket);
                log.debug("Set Ticket to AuthTicket");
                return ticket;
            }
            log.debug("tick error or ticket expired!");
        } else if (AuthTicket.getTicket() != null) {
            AuthTicket.remove();
        } else {
            log.debug("authCookieName [" + this.authCookieName + "] is null!");
        }
        return null;
    }
    
    public void setAuthCookieName(String authCookieName) {
        this.authCookieName = authCookieName;
    }
    
    public void setCookieUtils(CookieUtils cookieUtils) {
        this.cookieUtils = cookieUtils;
    }
    
    public void setAuthenticationKey(String authenticationKey) {
        this.authenticationKey = authenticationKey;
    }
    
}
