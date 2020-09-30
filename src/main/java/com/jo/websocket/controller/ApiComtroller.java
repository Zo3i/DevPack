package com.jo.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.jo.websocket.exception.BusinessException;
import com.jo.websocket.service.AsynService;
import com.jo.websocket.service.MainService;
import com.jo.websocket.socket.CommonAsyncListener;
import com.jo.websocket.socket.Socket;
import com.jo.websocket.utils.HttpClientUtil;
import com.jo.websocket.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Autowired
    private HttpClientUtil httpClientUtil;
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

    @RequestMapping("/iqiyi")
    public void getResource(HttpServletRequest request, HttpServletResponse response) {

        String res = httpClientUtil.getRes("https://cache.video.iqiyi.com/jp/dash?tvid=4596911661162000&bid=600&vid=e1333a330e9070c8997404ba7e50e6e5&src=01010031010000000000&vt=0&rs=1&uid=1186233851&ori=pcw&ps=1&tm=1599053770673&qd_v=2&k_uid=235426309a91d1fa12b5fbf83006fb01&pt=0&d=0&s=&lid=&cf=&ct=&authKey=06661d92c597d87ca011eacf99708365&k_tag=1&ost=0&ppt=0&bop=%7B%22version%22%3A%2210.0%22%2C%22dfp%22%3A%22a03408b9ba444b496c8d767480812f2377830ce5bc052bf1f49a76a31f567c08eb%22%7D&dfp=a03408b9ba444b496c8d767480812f2377830ce5bc052bf1f49a76a31f567c08eb&locale=zh_cn&prio=%7B%22ff%22%3A%22f4v%22%2C%22code%22%3A2%7D&k_ft1=706504940322820&k_ft4=36283952406528&k_ft5=1&pck=ccvGBm1YHaswJroIQnDl1K7dGBYaf4LFVDjoKNwDuqullisUm2Dc9LuZRLvUS6EVlpFL71&k_err_retries=1&ut=1&callback=__jp2&vf=34143a779488a21f33c79e7696ed9173");
        System.out.println(res);

        response.setContentType("application/json");
        try {
            response.getWriter().write(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
