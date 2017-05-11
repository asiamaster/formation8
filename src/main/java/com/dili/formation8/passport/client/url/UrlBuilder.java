package com.dili.formation8.passport.client.url;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;

public class UrlBuilder {

    private final URL base;
    private final boolean ignoreEmpty;
    private final Charset charset;
    private final Map<String, Object> queryMap;

    public UrlBuilder(String base) throws MalformedURLException {
        this(base, "utf-8", true);
    }

    public UrlBuilder(String base, String charsetName)
            throws MalformedURLException {
        this(base, charsetName, true);
    }

    public UrlBuilder(String base, String charsetName, boolean ignoreEmpty)
            throws MalformedURLException {
        this.base = new URL(base);
        this.charset = Charset.forName(charsetName);
        this.ignoreEmpty = ignoreEmpty;
        String queryString = this.base.getQuery();
        if (!StringUtils.isEmpty(queryString)) {
            this.queryMap = new LinkedHashMap<String, Object>(parseQuery(queryString));
        } else {
            this.queryMap = Collections.emptyMap();
        }
    }

    private Map<String, Object> parseQuery(String query) {
        String[] params = query.split("&");
        Map<String, Object> map = new LinkedHashMap<String, Object>(params.length);
        for (String param : params) {
            String[] strings = param.split("=");
            String name = strings[0];
            String value = null;
            if (strings.length > 1) {
                value = strings[1];
            }
            map.put(name, value);
        }
        return map;
    }

    public Builder forPath(String path) {
        return new Builder(this.base, path, this.charset.name(),
                this.ignoreEmpty, this.queryMap);
    }

    public static class Builder {

        final URL base;
        String path;
        String charsetName;
        boolean ignoreEmpty;
        final Map<String, Object> urlParameters;

        Builder(URL base, String path, String charsetName, boolean ignoreEmpty, Map<String, Object> queryMap) {
            this.base = base;
            this.path = path;
            this.charsetName = charsetName;
            this.ignoreEmpty = ignoreEmpty;
            this.urlParameters = new LinkedHashMap<String, Object>(queryMap);
        }

        public Builder setPath(String path) {
            this.path = path;
            return this;
        }

        public Builder setCharsetName(String charsetName) {
            this.charsetName = charsetName;
            return this;
        }

        public Builder setIgnoreEmpty(boolean ignoreEmpty) {
            this.ignoreEmpty = ignoreEmpty;
            return this;
        }

        public String build() throws MalformedURLException {
            String path = prefixPath(this.base.getPath(), this.path);
            int port = this.base.getPort();
            if (this.base.getPort() == this.base.getDefaultPort()) {
                port = -1;
            }
            StringBuilder builder = new StringBuilder(
                    new URL(this.base.getProtocol(), this.base.getHost(), port,
                            path).toString());

            StringBuilder query = new StringBuilder();
            for (Map.Entry<String, Object> entry : this.urlParameters.entrySet()) {
                String key = (String) entry.getKey();
                Object value = entry.getValue();
                if (value != null) {
                    if ((value instanceof Object[])) {
                        for (Object v : (Object[]) value) {
                            appendQueryString(key, v, query);
                        }
                    } else if ((value instanceof Collection)) {
                        for (Iterator<?> localIterator2 = ((Collection<?>) value)
                                .iterator(); localIterator2.hasNext();) {
                            Object v = localIterator2.next();
                            appendQueryString(key, v, query);
                        }
                    } else
                        appendQueryString(key, value, query);
                }
            }
            if (query.length() > 0) {
                query.replace(0, 1, "?");
            }

            return builder.toString() + query.toString();
        }

        void appendQueryString(String key, Object v, StringBuilder sb) {
            if (v == null) {
                return;
            }
            String value = String.valueOf(v);
            if ((this.ignoreEmpty) && (value.trim().length() == 0)) {
                return;
            }

            sb.append("&").append(key).append("=").append(encodeUrl(value));
        }

        String encodeUrl(String value) {
            String result;
            try {
                result = URLEncoder.encode(value, StringUtils
                        .isNotBlank(this.charsetName) ? this.charsetName
                        : Charset.defaultCharset().name());
            } catch (UnsupportedEncodingException e) {
                result = value;
            }
            return result;
        }

        String prefixPath(String contextPath, String path) {
            String returnPath;
            if ((path == null) || (contextPath == null)) {
                if ((path == null) && (contextPath == null)) {
                    returnPath = "/";
                } else {
                    if (contextPath == null) {
                        returnPath = path;
                    } else
                        returnPath = contextPath;
                }
            } else {
                if ((contextPath.endsWith("/")) && (path.startsWith("/"))) {
                    returnPath = contextPath + path.substring(1);
                } else {
                    returnPath = contextPath + path;
                }
            }
            return returnPath;
        }

        public Builder add(String key, Object value) {
            Object newValue;
            if (this.urlParameters.containsKey(key)) {
                Object o = this.urlParameters.get(key);
                if (o == null) {
                    newValue = value;
                } else {
                    List<Object> container = new LinkedList<Object>();
                    append(container, o);
                    append(container, value);
                    newValue = container;
                }
            } else {
                newValue = value;
            }
            this.urlParameters.put(key, newValue);
            return this;
        }

        void append(List<Object> container, Object o) {
            if ((o instanceof Object[])) {
                for (Object e : (Object[]) o) {
                    container.add(e);
                }
            } else if ((o instanceof Collection)) {
                container.addAll((Collection<?>) o);
            } else {
                container.add(o);
            }
        }

        public Builder add(Map<String, Object> values) {
            for (Map.Entry<String, Object> entry : values.entrySet()) {
                add((String) entry.getKey(), entry.getValue());
            }
            return this;
        }

        public Builder put(String key, Object value) {
            this.urlParameters.put(key, value);
            return this;
        }

        public Builder put(Map<String, Object> values) {
            this.urlParameters.putAll(values);
            return this;
        }
    }
}
