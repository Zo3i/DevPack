package com.jo.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.jo.websocket.exception.BusinessException;
import com.jo.websocket.service.AsynService;
import com.jo.websocket.service.MainService;
import com.jo.websocket.socket.CommonAsyncListener;
import com.jo.websocket.socket.Socket;
import com.jo.websocket.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ApiComtroller {

    @Autowired
    private Socket socket;
    @Autowired
    private AsynService asynService;
    @Autowired
    private MainService mainService;
    private Map<String,JSONObject> reqMap = new HashMap<>();


    /**
     * @param req
     */
    @RequestMapping("/testLongConnect")
    public void testLongConnect(HttpServletRequest req) {

        String key = UUID.randomUUID().toString();
        System.out.println(key);

        AsyncContext acontext = req.startAsync();
        acontext.addListener(new CommonAsyncListener(key));
        // 设置超时时间
        acontext.setTimeout(30 * 1000);
        // 记录当前请求
        asynService.addAsyn(key, acontext);
    }

    @RequestMapping("/closeLongConnect")
    private void closeLongConnect(@RequestParam("key") String key) {

        String result = "处理成功closeLongConnect！";

        // 根据key查询未结束的请求
        Map<String, AsyncContext> map = SpringContextUtils.getBean(AsynService.class).getASYNMAP();
        AsyncContext ctx = map.get(key);

        if (ctx == null) {
            throw new BusinessException("回调请求不存在！");
        }
        ctx.getResponse().setContentType("application/json;charset=UTF-8");
        try {
            ctx.getResponse().getWriter().write("返回内容key=" + key + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.complete();
    }

}
