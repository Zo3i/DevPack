package com.jo.websocket.socket;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;


@Component
@ServerEndpoint(value="/websocket/{name}")
public class Socket {

    private Logger log = LoggerFactory.getLogger(Socket.class);

    /**
     *  与某个客户端的连接对话，需要通过它来给客户端发送消息
     */
    private Session session;

    /**
     * 标识当前连接客户端的用户名
     */
    private String name;

    /**
     *  用于存所有的连接服务的客户端，这个对象存储是安全的
     */
    private static ConcurrentHashMap<String, Socket> webSocketSet = new ConcurrentHashMap<>();


    @OnOpen
    public void OnOpen(Session session, @PathParam(value = "name") String name){
        this.session = session;
        this.name = name;
        // name是用来表示唯一客户端，如果需要指定发送，需要指定发送通过name来区分
        webSocketSet.put(name, this);
        log.info("[WebSocket] 连接成功，当前连接人数为：={}",webSocketSet.size());
    }


    @OnClose
    public void OnClose(){
//        webSocketSet.remove(this.name);
//        log.info("[WebSocket] 退出成功，当前连接人数为：={}",webSocketSet.size());
    }

    @OnMessage
    public void OnMessage(String message){
        System.out.println("收到消息：" + message);
        try {
            webSocketSet.get(name).session.getBasicRemote().sendText(">>> pong");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //连接错误时执行
    @OnError
    public void onError(Session session, Throwable error){
        log.debug("用户id为：{}的连接发送错误",this.name);
        error.printStackTrace();
    }

    /**
     * 群发
     * @param message
     */
    public void GroupSending(String message){
        for (String name : webSocketSet.keySet()){
            try {
                webSocketSet.get(name).session.getBasicRemote().sendText(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取卡信息
     */
    public void mainCardInfo (String clientName, String key, String method){
        try {
            JSONObject json = new JSONObject();
            json.put("method", method);
            json.put("key", key);
            json.put("clientName", clientName);

            log.info("json: >>> {}", json);
            webSocketSet.get(clientName).session.getBasicRemote().sendText(json.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
