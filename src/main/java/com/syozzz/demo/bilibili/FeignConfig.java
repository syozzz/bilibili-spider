package com.syozzz.demo.bilibili;


import cn.hutool.core.lang.Assert;
import cn.hutool.core.thread.GlobalThreadPool;
import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;

@Slf4j
public class FeignConfig {

    //请求域名
    private static final String URL = "http://api.bilibili.com";
    //请求用户 UID
    private static final long UID = 828754;
    //所有的番剧
    private static List<Map<String, Object>> bangumi = new ArrayList<>();
    //每页请求数量
    public static final int ps = 20;

    public static void main(String[] args) {
        //创建请求端点
        RequestService service = RequestUtil.build(RequestService.class, URL);
        //获取请求结果
        Map<String, Object> result = service.list(RequestUtil.getRequestParams(UID, 1));
        int total = handleRequestResult(result);;
        //如果还要下一页 继续追加
        try {
            appendNextPage(total, service);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
        } finally {
            GlobalThreadPool.shutdown(true);
        }
        log.info("执行完毕，共计结果{}条", bangumi.size());
        new ResultHandler(bangumi).execute();
    }

    private static int handleRequestResult(Map<String, Object> result) {
        Map<String, Object> data = (Map<String, Object>) result.get("data");
        List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("list");
        bangumi.addAll(list);
        int total = (int) data.get("total");
        return total;
    }

    private static void appendNextPage(int total, RequestService requestService) throws InterruptedException, ExecutionException {
        if (total < ps) {
            return;
        }
        float newTotal = total - ps;
        //计算还有几页
        int nums = (int) Math.ceil(newTotal / ps);
        CompletionService completionService = ThreadUtil.newCompletionService();

        //添加任务
        for (int i = 1; i <= nums; i++) {
            final int pn = i + 1;
            completionService.submit(() -> {
                return requestService.list(RequestUtil.getRequestParams(UID, pn));
            });
        }

        //获取执行结果
        for (int i = 1; i <= nums; i++) {
            Map<String, Object> res = (Map<String, Object>) completionService.take().get();
            handleRequestResult(res);
        }

        //比对结果
        Assert.isTrue(bangumi.size() == total, "执行结果与总数不一致");
    }

}
