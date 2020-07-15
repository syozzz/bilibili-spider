package com.syozzz.demo.bilibili;

import feign.QueryMap;
import feign.RequestLine;

import java.util.Map;

public interface RequestService {

    @RequestLine("GET /x/space/bangumi/follow/list")
    Map<String, Object> list(@QueryMap Map<String, Object> queryMap);

}
