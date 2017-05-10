package com.dili.formation8.utils;

import com.dili.formation8.service.TimeLimitService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;


@Component
public class CookieManager {
   
    @Resource
    private CookieCipherTools cookieCipherTools;
    
    @Value("${common-cookie-key}")
    private String COOKIE_ENCRYPT_KEY; 
    
    public static final String DILI_AUTH_COOKIE_NAME       = "_dl_";
    
    public static final String COOKIE_KEY_LOGIN_TIME       = "alt";
    
    public static final String COOKIE_KEY_USERNAME         = "pin";
    
    public static final String COOKIE_KEY_RETURN_URL         = "returnURL";
    
    public static int LOGIN_TIME_COOKIE_EXPIRY    = 60 * 10;
    
    public static int LOGIN_USERNAME_COOKIE_EXPIRY = 60 * 60 * 24 * 30;
    
    public static int LOGIN_RETURN_URL_EXPIRY = 60 * 3;
    
    public static int REMEMBER_ME_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 30;

    @Resource
    private TimeLimitService timeLimitService;
    
    @Autowired
    private CookieManager cookieManager;


    /**
     * 获得cookie中登录次数
     * @return
     */
    public int getLoginTimesByIp(HttpServletRequest request){
        String ip = PassportUtils.getRemoteIP(request);
        return timeLimitService.getUserLoginFailTime(ip);
    }
    
    /**
     * 获得cookie中登录次数
     * @return
     */
    public int getLoginTimes(HttpServletRequest request){
        int loginTimes = -1;
        
        String loginTimesCookie = getWebCookieValue(request, COOKIE_KEY_LOGIN_TIME);// get value from cookie
        if (loginTimesCookie != null){
            String rawStringDec = new String(Base64.decodeBase64(loginTimesCookie));
            loginTimes = Integer.parseInt(cookieCipherTools.decrypt(rawStringDec, COOKIE_ENCRYPT_KEY));
        }
        
        return loginTimes;
    }

    public void setLoginTimes(HttpServletRequest request, HttpServletResponse response, int loginTimes) {
        String rawStringEnc = cookieCipherTools.encrypt(String.valueOf((loginTimes)), COOKIE_ENCRYPT_KEY);
        newCookie(response, COOKIE_KEY_LOGIN_TIME, new String(Base64.encodeBase64(rawStringEnc.getBytes())), LOGIN_TIME_COOKIE_EXPIRY, Formation8Constants.PASSPORT_DOMAINS, true);
    }
    
    public void addLoginTimes(HttpServletRequest request, HttpServletResponse response) {
        /**
         * 增加cookie中登录次数
         * @return
         */
        int loginTimes = 1;
        
        String loginTimesCookie = getWebCookieValue(request, COOKIE_KEY_LOGIN_TIME);// get value from cookie
        
        if (loginTimesCookie != null){
            String rawStringDec = new String(Base64.decodeBase64(loginTimesCookie));
            loginTimes = Integer.parseInt(cookieCipherTools.decrypt(rawStringDec, COOKIE_ENCRYPT_KEY));
        }
        
        String rawStringEnc = cookieCipherTools.encrypt(String.valueOf((loginTimes + 1)), COOKIE_ENCRYPT_KEY);
        newCookie(response, COOKIE_KEY_LOGIN_TIME, new String(Base64.encodeBase64(rawStringEnc.getBytes())), LOGIN_TIME_COOKIE_EXPIRY,Formation8Constants.PASSPORT_DOMAINS, true);
        
        timeLimitService.incrementUserLoginFailTime(PassportUtils.getRemoteIP(request));
    }
    
    public String getWebCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equalsIgnoreCase(cookieName)){
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    public void newCommonCookie(HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge) {
        newCookie(response, cookieName, cookieValue, cookieMaxAge, Formation8Constants.cookieDomain, false);
    }

    public void newAuthCookie(HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge,
                              boolean httpOnly) {
        newCookie(response, cookieName, cookieValue, cookieMaxAge, Formation8Constants.cookieDomain, httpOnly);
    }
    
    public void newCookie(HttpServletResponse response, String cookieName, String cookieValue, int cookieMaxAge, String CookieDomain,
                          boolean httpOnly) {
        if (!httpOnly) {
            Cookie newCookie = new Cookie(cookieName, cookieValue);
            newCookie.setMaxAge(cookieMaxAge);
            newCookie.setDomain(Formation8Constants.cookieDomain);
            newCookie.setPath("/");
            response.addCookie(newCookie);
            return;
        }
        
        // HttpOnly
        StringBuffer rawCookie = new StringBuffer(200);
        rawCookie.append(cookieName).append("=").append(cookieValue).append(";");
        rawCookie.append(" Domain=").append(CookieDomain).append(";");
        if (cookieMaxAge >= 0) {
            Calendar cal = Calendar.getInstance(Locale.US);
            cal.add(Calendar.SECOND, cookieMaxAge);
            DateFormat df = new SimpleDateFormat("EEE, d-MMM-yyyy HH:mm:ss z", Locale.US);
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            String expires = df.format(cal.getTime());
            if (cookieMaxAge == 0) { //���cookie
                expires = "Thu, 01-Jan-1970 00:00:10 GMT";
            }
            rawCookie.append(" Expires=").append(expires).append(";");
        }
        rawCookie.append(" Path=").append("/");
        if (httpOnly) {
            rawCookie.append(";");
            rawCookie.append(" HttpOnly");
        }
        newCookie(response, "_dl_remember_", cookieMaxAge + "", -1, CookieDomain, false);
        response.addHeader("Set-Cookie", rawCookie.toString());
    }

    public int getLOGIN_TIME_COOKIE_EXPIRY() {
        return LOGIN_TIME_COOKIE_EXPIRY;
    }

    public void setLOGIN_TIME_COOKIE_EXPIRY(int lOGIN_TIME_COOKIE_EXPIRY) {
        LOGIN_TIME_COOKIE_EXPIRY = lOGIN_TIME_COOKIE_EXPIRY;
    }

    public int getLOGIN_USERNAME_COOKIE_EXPIRY() {
        return LOGIN_USERNAME_COOKIE_EXPIRY;
    }

    public void setLOGIN_USERNAME_COOKIE_EXPIRY(int lOGIN_USERNAME_COOKIE_EXPIRY) {
        LOGIN_USERNAME_COOKIE_EXPIRY = lOGIN_USERNAME_COOKIE_EXPIRY;
    }

    public int getREMEMBER_ME_EXPIRE_TIME() {
        return REMEMBER_ME_EXPIRE_TIME;
    }

    public void setREMEMBER_ME_EXPIRE_TIME(int rEMEMBER_ME_EXPIRE_TIME) {
        REMEMBER_ME_EXPIRE_TIME = rEMEMBER_ME_EXPIRE_TIME;
    }

    public void setCOOKIE_ENCRYPT_KEY(String cOOKIE_ENCRYPT_KEY) {
        COOKIE_ENCRYPT_KEY = cOOKIE_ENCRYPT_KEY;
    }

    public String getDiliCookieDomain() {
        return Formation8Constants.cookieDomain;
    }


    public String getPassportCookieDomain() {
        return Formation8Constants.PASSPORT_DOMAINS;
    }

    public final void setCookieLoginTimes(HttpServletRequest request,
                                                 HttpServletResponse response, int loginTimes) {
        String rawStringEnc = cookieCipherTools.encrypt(String.valueOf((loginTimes)), COOKIE_ENCRYPT_KEY);
        newCookie(response, COOKIE_KEY_LOGIN_TIME, new String(Base64.encodeBase64(rawStringEnc.getBytes())), LOGIN_TIME_COOKIE_EXPIRY, Formation8Constants.PASSPORT_DOMAINS, true);
    }

    public final void deleteCookies(HttpServletResponse response) {
        PassportUtils.deleteCookie(response, CookieManager.DILI_AUTH_COOKIE_NAME, getDiliCookieDomain());
    }

    public void newAuthCookies(HttpServletResponse response,
            String cookieName, String cookieValue, int cookieMaxAge,
            boolean httpOnly) {
        newAuthCookie(response, cookieName, cookieValue, cookieMaxAge, httpOnly);
    }

}
