package com.dili.formation8.passport.client.url;

import java.util.Collections;
import java.util.Map;

public class UrlBuilders {

    private final Map<String, UrlBuilder> urlBuilders;

    public UrlBuilders(Map<String, UrlBuilder> urlBuilders) {
        this.urlBuilders = Collections.unmodifiableMap(urlBuilders);
    }

    public UrlBuilder get(String key) throws NullPointerException {
        UrlBuilder urlBuilder = (UrlBuilder) this.urlBuilders.get(key);
        if (urlBuilder == null) {
            throw new NullPointerException("No such predefined url builder: "
                    + key);
        }
        return (UrlBuilder) this.urlBuilders.get(key);
    }
}
