package com.jo.websocket.enums;

public enum YesNum {

    YES(1, "是"),
    NO(2, "否");

    private final Integer key;
    private final String value;

    YesNum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public static YesNum get(Integer val) {
        for (YesNum s : YesNum.values()) {
            if (s.key.equals(val)) {
                return s;
            }
        }
        return null;
    }

    public boolean yes (Integer key) {
        return YES.key.equals(key);
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
