package com.jo.websocket.socket;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    /**
     * 字符串转json
     * eg. templateId=xxx;card=xxx;amount
     */
    public String json2Str(String method, String str) {
        JSONObject json = new JSONObject();
        json.put("methed", method);
        String[] params = str.split(";");
        if (params.length < 2) {
            System.out.println("参数有误");
        }
        for(String s : params) {
            String[] t = s.split("=");
            if (t.length < 2) {
                System.out.println("参数有误");
            }
            json.put(t[0],t[1]);
        }
        return json.toJSONString();
    }

}
