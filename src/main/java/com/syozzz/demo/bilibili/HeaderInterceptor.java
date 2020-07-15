package com.syozzz.demo.bilibili;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一设置 cookie 模拟已登录
 */
@Slf4j
public class HeaderInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Cookie", setCookie(getCookieMap()));
        requestTemplate.header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
    }

    private String setCookie(Map<String, String> cookieMap) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry entry : cookieMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            sb.append(entry.getValue());
            sb.append(";");
        }
        String cookie = sb.toString();
        return cookie;
    }

    /**
     * 登录之后的 cookie 值
     * 可用浏览器登录 b 站之后，在浏览器控制台中获取
     * @return
     */
    private Map<String, String> getCookieMap() {
        Map<String, String> map = new HashMap<>();
        map.put("DedeUserID", "xxxx");
        map.put("DedeUserID__ckMd5", "xxxx");
        map.put("SESSDATA", "xxxx");
        map.put("bili_jct", "xxxx");
        return map;
    }
}
