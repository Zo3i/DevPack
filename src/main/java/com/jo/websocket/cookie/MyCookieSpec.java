package com.jo.websocket.cookie;

import org.apache.http.impl.cookie.DefaultCookieSpec;

public class MyCookieSpec extends DefaultCookieSpec {

//    @Override
//    public List<Cookie> parse(Header header, CookieOrigin cookieOrigin) throws MalformedCookieException {
//        String value = header.getValue();
//
////        if (value.contains("JSESSIONID")) {
////            header = new BasicHeader(header.getName(), value);
////            return super.parse(header, cookieOrigin);
////        }
//
//        // 手动修改cookie
////        value = value.replaceAll("[pP]ath.*;", "path=/;");
////        value = value.replaceAll("domain.*;", "domain: www.sinopecsales.com;");
////        if (value.contains("security_session_verify")) {
////            value = value.replaceAll("expires.*;", "");
////            value += ";path=/";
//////            System.out.println("===============" + value + "===============");
////        }
//
//        header = new BasicHeader(header.getName(), value);
//        return super.parse(header, cookieOrigin);
//    }

//    @Override
//    public void validate(Cookie arg0, CookieOrigin arg1) throws MalformedCookieException {
//        //allow all cookies
//    }

}
