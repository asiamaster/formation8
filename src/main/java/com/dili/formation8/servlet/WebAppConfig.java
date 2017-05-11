package com.dili.formation8.servlet;

import com.dili.formation8.passport.client.interceptor.AuthTicketContextInterceptor;
import com.dili.formation8.passport.client.interceptor.TicketRequiredInterceptor;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * passport接入配置
 * Created by asiam on 2017/5/11 0011.
 */
@Configuration
@ConfigurationProperties(prefix="passport.login", locations = "classpath:passport.properties")
public class WebAppConfig extends WebMvcConfigurerAdapter {

    //加个name避免使用@Autowired报错。。。
//    @Autowired
    @Resource(name="authTicketContextInterceptor")
    AuthTicketContextInterceptor authTicketContextInterceptor;

    @Resource(name="ticketRequiredInterceptor")
    TicketRequiredInterceptor ticketRequiredInterceptor;

    //初始化以避免空
    private List<String> contextInterceptorPaths = new ArrayList<>();
    private List<String> contextInterceptorExcludePaths = new ArrayList<>();
    private List<String> requiredInterceptorPaths = new ArrayList<>();
    private List<String> requiredInterceptorExcludePaths = new ArrayList<>();

    /**
     * 配置拦截器
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authTicketContextInterceptor)
                .addPathPatterns(contextInterceptorPaths.toArray(new String[contextInterceptorPaths.size()]))
                .excludePathPatterns(contextInterceptorExcludePaths.toArray(new String[contextInterceptorExcludePaths.size()]));
        registry.addInterceptor(ticketRequiredInterceptor)
                .addPathPatterns(requiredInterceptorPaths.toArray(new String[requiredInterceptorPaths.size()]))
                .excludePathPatterns(requiredInterceptorExcludePaths.toArray(new String[requiredInterceptorExcludePaths.size()]));
    }

    public List<String> getContextInterceptorPaths() {
        return contextInterceptorPaths;
    }

    public void setContextInterceptorPaths(List<String> contextInterceptorPaths) {
        this.contextInterceptorPaths = contextInterceptorPaths;
    }

    public List<String> getRequiredInterceptorPaths() {
        return requiredInterceptorPaths;
    }

    public void setRequiredInterceptorPaths(List<String> requiredInterceptorPaths) {
        this.requiredInterceptorPaths = requiredInterceptorPaths;
    }


    public List<String> getContextInterceptorExcludePaths() {
        return contextInterceptorExcludePaths;
    }

    public void setContextInterceptorExcludePaths(List<String> contextInterceptorExcludePaths) {
        this.contextInterceptorExcludePaths = contextInterceptorExcludePaths;
    }

    public List<String> getRequiredInterceptorExcludePaths() {
        return requiredInterceptorExcludePaths;
    }

    public void setRequiredInterceptorExcludePaths(List<String> requiredInterceptorExcludePaths) {
        this.requiredInterceptorExcludePaths = requiredInterceptorExcludePaths;
    }

}
