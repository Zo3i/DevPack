package com.jo.websocket.utils;

import java.security.MessageDigest;

public class Sha1Utils {

    /**
     * @Comment SHA1实现
     * @Author Ron
     * @Date 2017年9月13日 下午3:30:36
     * @return
     */
    public static String shaEncode(String inStr) throws Exception {
        MessageDigest sha = null;
        try {
            sha = MessageDigest.getInstance("SHA");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }

        byte[] byteArray = inStr.getBytes("UTF-8");
        byte[] md5Bytes = sha.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static void main(String args[]) throws Exception {
//        String str = new String("[{\"zuCard\":\"1000113500060191222\",\"fuCard\":\"1000113500060193923\",\"jine\":\"0.01\",\"jifen\":\"0\",\"yonghu\":\"林声\",\"keyCode\":\"7e38eb8edfdcec2bdc0e6ce8001367ad122f95d0\"},{\"zuCard\":\"1000113500060191222\",\"fuCard\":\"1000113500060194008\",\"jine\":\"0.01\",\"jifen\":\"0\",\"yonghu\":\"林声\",\"keyCode\":\"a1b7a8a0365a49ad31657c0ceb82087509dac212\"}]");
        String str = "[{\"zuCard\":\"\",\"fuCard\":\"1000113500005038020\",\"jine\":0.01,\"jifen\":0}]";
        System.out.println("原始：" + str);
        System.out.println("SHA后：" + shaEncode(str));

        String code = "expires= , 16-12ÔÂ-19 11:03:48 GMT;";

        System.out.println(code.replaceAll("expires=.*;", "expires=null;"));


    }
}
