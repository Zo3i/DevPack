package com.jo.websocket.service;

import org.springframework.stereotype.Service;

import javax.servlet.AsyncContext;
import java.util.HashMap;
import java.util.Map;

@Service
public class AsynService {

    private static Map<String, AsyncContext> ASYNMAP = new HashMap<>();

    public static void addAsyn(String id, AsyncContext asyn) {
        ASYNMAP.put(id, asyn);
    }

    public static void rmAsyn(String id) {
        ASYNMAP.remove(id);
    }

    public static AsyncContext getAsyn(String id) {
        return ASYNMAP.get(id);
    }

    public Map<String, AsyncContext> getASYNMAP() {
        return ASYNMAP;
    }

    public void setASYNMAP(Map<String, AsyncContext> ASYNMAP) {
        this.ASYNMAP = ASYNMAP;
    }
}
