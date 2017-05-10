package com.dili.formation8.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Pattern;

public class PassportUtils {

    private static final Logger logger = LoggerFactory.getLogger(PassportUtils.class);
    private static final Pattern WebsiteDomainsP = Pattern.compile("(http://|https://)([\\w\\-_]*)([\\.]?)(" + Formation8Constants.WEBSITE_DOMAINS + ")\\.com([/]?)(.*)+", Pattern.CASE_INSENSITIVE);
    private static final Pattern PassportP = Pattern.compile("(http://|https://)passport([\\.]?)(" + Formation8Constants.WEBSITE_DOMAINS + ")([/]?)(.*)+", Pattern.CASE_INSENSITIVE);

    /**
     * 获取远程IP
     *
     * @param request
     * @return
     */
    public static String getRemoteIP(HttpServletRequest request) {
        String ip = null;
        if ((request.getHeader("x-forwarded-for") != null) && (!"unknown".equalsIgnoreCase(request.getHeader("x-forwarded-for")))) {
            ip = request.getHeader("x-forwarded-for");
        } else if ((request.getHeader("X-Real-IP") != null) && (!"unknown".equalsIgnoreCase(request.getHeader("X-Real-IP")))) {
            ip = request.getHeader("X-Real-IP");
        } else {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取返回Url
     *
     * @param request
     * @return
     */
    public static String getReturnUrl(HttpServletRequest request) {
        String returnUrl = request.getParameter("returnUrl");
        if (StringUtils.isBlank(returnUrl)) {
            returnUrl = request.getParameter("ReturnUrl");
        }
        if (StringUtils.isNotBlank(returnUrl)) {
            try {
                returnUrl = URLDecoder.decode(returnUrl, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.debug("============== passport return syntax error  ==============", e);
                returnUrl = StringUtils.EMPTY;
            }
        }
        logger.debug("============= passport login parameter return url : " + returnUrl + "===========");
        if (StringUtils.isBlank(returnUrl) || !WebsiteDomainsP.matcher(returnUrl).matches() || PassportP.matcher(returnUrl).matches()) {
            returnUrl = Formation8Constants.WWW_DOMAIN;
        }
        logger.debug("============= passport login return url : " + returnUrl + "===========");
        return returnUrl;
    }

    /**
     * 设置不缓存头
     *
     * @param response
     */
    public static void setNonCacheResponseHeaders(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setHeader("Pragma", "no-cache");
        long time = System.currentTimeMillis();
        response.setDateHeader("Last-Modified", time);
        response.setDateHeader("Date", time);
        response.setDateHeader("Expires", time);
    }

    /**
     * 删除cookie
     *
     * @param response
     * @param cookieName
     * @param cookieDomain
     */
    public static void deleteCookie(HttpServletResponse response, String cookieName, String cookieDomain) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setDomain(cookieDomain);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /**
     * 用户密码加密
     *
     * @param password
     * @return
     */
    public static String md5PwdWithSubString(String password) {
        MD5Util m = new MD5Util();
        return m.getMD5ofStr(password).substring(6, 24);
    }

}
