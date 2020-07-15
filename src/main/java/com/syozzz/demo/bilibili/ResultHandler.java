package com.syozzz.demo.bilibili;

import cn.hutool.core.io.file.FileWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Slf4j
public class ResultHandler {

    private List<Map<String, Object>> data;

    public ResultHandler(List<Map<String, Object>> data) {
        this.data = data;
    }

    public void execute() {
        log.info(data.toString());
        FileWriter writer = new FileWriter("output.txt");
        PrintWriter pw = writer.getPrintWriter(false);
        for (Map<String, Object> map : data) {
            pw.println("- img: " + map.get("cover").toString());
            pw.println("  title: " + map.get("title").toString());
            pw.println("  status: 已追完");
            pw.println("  progress: 100");
            Map<String, Object> pub = (Map<String, Object>) map.get("publish");
            pw.println("  time: " + pub.get("release_date_show").toString());
            pw.println("  desc: " + map.get("evaluate").toString().replaceAll("\\n", "").replaceAll("\\r", ""));
        }
        pw.flush();
        pw.close();
    }
}
