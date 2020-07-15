package com.syozzz.demo.bilibili;

import cn.hutool.core.map.MapBuilder;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.Retryer;
import feign.form.FormEncoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;

import java.util.HashMap;
import java.util.Map;


public class RequestUtil {

    /**
     * 端点 builder
     * @param apiType
     * @param url
     * @param <T>
     * @return
     */
    public static <T> T build(Class<T> apiType, String url) {
        return Feign.builder()
                .encoder(new FormEncoder(new JacksonEncoder()))
                .decoder(new JacksonDecoder())
                .client(new OkHttpClient())
                .options(new Request.Options(2000, 3500))
                .retryer(new Retryer.Default(5000, 5000, 3))
                .logger(new Logger.JavaLogger().appendToFile("http.log"))
                .logLevel(Logger.Level.FULL)
                .requestInterceptor(new HeaderInterceptor())
                .target(apiType, url);
    }

    public static Map<String, Object> getRequestParams(long uid, int pn) {
        return MapBuilder.create(new HashMap<String, Object>())
                .put("vmid", uid)
                .put("pn", pn)
                .put("ps", FeignConfig.ps)
                .put("type", 1)
                .map();
    }
}
