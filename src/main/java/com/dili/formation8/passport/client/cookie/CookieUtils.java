package com.dili.formation8.passport.client.cookie;

import com.dili.formation8.passport.CookieCipherTools;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
//@EnableConfigurationProperties
//@ConfigurationProperties(prefix = "cookie",locations = "classpath:passport.properties")
@PropertySource("classpath:passport.properties" )
public class CookieUtils {

  private Map<String, CipherCookie> cookieMap = new HashMap<>();

  @Autowired
  private CookieCipherTools cookieCipherTools;

  @Value("${cookie.encrypt}")
  private String encrypt;
  @Value("${cookie.expiry}")
  private String expiry;
  @Value("${cookie.name}")
  private String name;
  @Value("${cookie.key}")
  private String key;
  @Value("${cookie.path}")
  private String path;
  @Value("${cookie.domain}")
  private String domain;

  @PostConstruct
  public void  init(){
    CipherCookie cipherCookie = new CipherCookie();
    cipherCookie.setCookieCipherTools(cookieCipherTools);
    cipherCookie.setEncrypt(Boolean.parseBoolean(encrypt));
    cipherCookie.setExpiry(Integer.parseInt(expiry));
    cipherCookie.setName(name);
    cipherCookie.setKey(key);
    cipherCookie.setPath(path);
    cipherCookie.setDomain(domain);
    cookieMap.put("_BrowseCookie", cipherCookie);
  }

    public String getCookieValue(HttpServletRequest servletRequest, String name){
      Cookie[] cookies = servletRequest.getCookies();
      if ((cookies != null) && (cookies.length > 0)) {
        for (Cookie cookie : cookies) {
          String cookieName = cookie.getName();
          if (cookieName.equals(name)) {
            if ((this.cookieMap != null) && (this.cookieMap.containsKey(name))) {
              CipherCookie cipherCookie = (CipherCookie)this.cookieMap.get(name);
              return cipherCookie.getValue(cookie.getValue());
            }
            return cookie.getValue();
          }
        }
      }
      return null;
    }
    
    public void deleteCookie(HttpServletResponse servletResponse, String name){
      Cookie cookie = null;
      if ((this.cookieMap != null) && (this.cookieMap.containsKey(name))) {
        CipherCookie cipherCookie = (CipherCookie)this.cookieMap.get(name);
        cookie = cipherCookie.newCookie(null);
      } else {
        cookie = new Cookie(name, null);
      }
      cookie.setMaxAge(0);
      servletResponse.addCookie(cookie);
    }
    
    public void setCookie(HttpServletResponse servletResponse, String name, String value){
      if ((this.cookieMap != null) && (this.cookieMap.containsKey(name))) {
        CipherCookie cipherCookie = (CipherCookie)this.cookieMap.get(name);
        Cookie cookie = cipherCookie.newCookie(value);
        servletResponse.addCookie(cookie);
      } else {
        throw new RuntimeException("Cookie " + name + " is undefined!");
      }
    }
    
    public void setCipherCookieCookie(List<CipherCookie> cipherCookieList){
      if (cipherCookieList != null) {
        HashMap<String, CipherCookie> cipherCookieHashMap = new HashMap<String, CipherCookie>(cipherCookieList.size());
        for (CipherCookie cipherCookie : cipherCookieList) {
          cipherCookieHashMap.put(cipherCookie.getName(), cipherCookie);
        }
        this.cookieMap = cipherCookieHashMap;
      }
    }
    
    public void invalidate(HttpServletRequest request, HttpServletResponse response){
      if ((this.cookieMap != null) && (this.cookieMap.size() > 0)) {
        for (Map.Entry<String, CipherCookie> entry : this.cookieMap.entrySet()) {
          String key = (String)entry.getKey();
          CipherCookie cipherCookie = (CipherCookie)entry.getValue();
          if ((cipherCookie.getExpiry() < 1) &&
            (StringUtils.isNotEmpty(getCookieValue(request, key)))) {
            deleteCookie(response, key);
          }
        }
      }
    }
}
