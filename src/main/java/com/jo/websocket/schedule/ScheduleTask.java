package com.jo.websocket.schedule;

import com.jo.websocket.socket.Socket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class ScheduleTask {

    private static Logger logger = LoggerFactory.getLogger(ScheduleTask.class);

    @Autowired
    private Socket socket;
    private int timeRecode = 0;
    
    
    /**
     * 每5秒检查在线状态
     */
    @Scheduled(fixedRate = 5 * 1000)
    public void ping() throws Exception {
        System.out.println("发送消息：>>> ping");
        socket.GroupSending(">>> ping");
    }


}