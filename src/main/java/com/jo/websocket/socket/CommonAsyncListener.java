package com.jo.websocket.socket;

import com.jo.websocket.exception.BusinessException;
import com.jo.websocket.service.AsynService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;

/**
 * 通用异步监听
 */
public class CommonAsyncListener implements AsyncListener {


    @Autowired
    private AsynService asynService;

    private static final Logger logger = LoggerFactory.getLogger(CommonAsyncListener.class);
    private String waitKey;

    public CommonAsyncListener(String waitKey){
        this.waitKey = waitKey;
    }

    @Override
    public void onComplete(AsyncEvent event) {
        logger.info("complete 移除上下文");
        asynService.rmAsyn(waitKey);
    }

    @Override
    public void onTimeout(AsyncEvent event) {
        logger.warn("长轮询超时关闭",event.getThrowable());
        throw new BusinessException("长轮询超时关闭！");
    }

    @Override
    public void onError(AsyncEvent event) {
        logger.warn("长轮询异常关闭",event.getThrowable());
        throw new BusinessException("长轮询异常关闭！");
    }

    @Override
    public void onStartAsync(AsyncEvent event) {
        logger.warn("长轮询开始");
    }

    /**
     * 获取业务数据
     * @param event
     * @return
     */
    private String getBizData(AsyncEvent event){
        return  event.getAsyncContext().getRequest().getParameter("bizData");
    }

}