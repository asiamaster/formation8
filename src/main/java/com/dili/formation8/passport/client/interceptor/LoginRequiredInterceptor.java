package com.dili.formation8.passport.client.interceptor;

import com.dili.formation8.passport.client.url.UrlBuilder;
import com.dili.formation8.passport.client.url.UrlBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public class LoginRequiredInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRequiredInterceptor.class);

    protected UrlBuilders urlBuilders;

    @Value("${project.domain.localPath}")
    private String projectDomainLocalPath;
    @Value("${passport.login.address}")
    private String passportLoginAddress;
    protected String homeModule = "homeModule";
    protected String loginUrl = "loginUrl";

    @PostConstruct
    public void  init(){
        Map<String, UrlBuilder> urlBuildersMap = new HashMap<>();
        try {
            urlBuildersMap.put(homeModule, new UrlBuilder(projectDomainLocalPath));
            urlBuildersMap.put(loginUrl, new UrlBuilder(passportLoginAddress));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        urlBuilders = new UrlBuilders(urlBuildersMap);
    }

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {
        return true;
    }

    protected String  getLoginUrl(HttpServletRequest request)
            throws MalformedURLException, UnsupportedEncodingException {
        UrlBuilder.Builder currentUrlBuilder = this.urlBuilders.get(this.homeModule).forPath(request.getRequestURI());
        currentUrlBuilder.put(request.getParameterMap());
        UrlBuilder.Builder loginUrlBuilder = this.urlBuilders
                .get(this.loginUrl).forPath(null);
        loginUrlBuilder.put("ReturnUrl", currentUrlBuilder.build());
        return loginUrlBuilder.build();
    }

    public void setUrlBuilders(UrlBuilders urlBuilders) {
        this.urlBuilders = urlBuilders;
    }

    public void setHomeModule(String homeModule) {
        this.homeModule = homeModule;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }
}
