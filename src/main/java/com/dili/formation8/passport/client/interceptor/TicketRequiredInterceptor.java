package com.dili.formation8.passport.client.interceptor;

import com.alibaba.fastjson.JSON;
import com.dili.formation8.passport.AuthTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
@PropertySource("classpath:passport.properties")
public class TicketRequiredInterceptor extends LoginRequiredInterceptor {
    private static final Logger log = LoggerFactory.getLogger(TicketRequiredInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        AuthTicket ticket = AuthTicket.getTicket();

        if (ticket == null) {
            log.debug("Context hasn't a ticked!");
            String loginUrl = getLoginUrl(request);
            //判断是否ajax请求
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                Map<String, Object> map = new HashMap<String, Object>(2);
                map.put("login", true);
                map.put("loginUrl", loginUrl);
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(map));
                writer.flush();
            } else
                response.sendRedirect(loginUrl);
            return false;
        }
        return true;
    }
}

