package com.jo.websocket.utils;

import com.alibaba.fastjson.JSONObject;
import com.jo.websocket.cookie.MyCookieSpecProvider;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.impl.cookie.RFC6265CookieSpecProvider;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HttpClientUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	private static List<String> cookieList = new ArrayList<String>();

	private static Map<String, String> cookieMap = new HashMap<>();

	PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();

	Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider>create()
			.register(CookieSpecs.DEFAULT,
					new DefaultCookieSpecProvider(publicSuffixMatcher))
			.register(CookieSpecs.STANDARD,
					new RFC6265CookieSpecProvider(publicSuffixMatcher))
			.register("easy", new MyCookieSpecProvider())
			.build();

//    HttpHost proxy = new HttpHost("192.168.88.151",8888);

	// 全局请求设置
	private RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
	//    private RequestConfig globalConfig = RequestConfig.custom().setCookieSpec("easy").build();
	// 创建cookie store的本地实例
	private CookieStore cookieStore = new BasicCookieStore();
	// 创建HttpClient上下文
	private HttpClientContext context = HttpClientContext.create();

	// 创建一个HttpClient
	private CloseableHttpClient httpClient =  null;

	private CloseableHttpResponse res = null;

	public HttpClientUtil(){
		context.setCookieStore(cookieStore);
		httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
				.setDefaultCookieStore(cookieStore).build();

	}

	public JSONObject getCode ()  {
		System.out.println("获取验证码");
		JSONObject ret = new JSONObject();
		String getCodeUrl = "https://pin.aliyun.com/get_img?sessionid=k0pWIcNKV19h64BGdmusOEcy87/DNh8MsfDhyOaPFI7hM=&identity=login.dingtalk.com&type=default";
		System.out.println(getCodeUrl);
		String location = "C:/1.png";
		HttpGet get = new HttpGet(getCodeUrl);
		get.addHeader(new BasicHeader("Connection", "keep-alive"));
		get.addHeader(new BasicHeader("Content-Type", "application/json, text/javascript, */*; q=0.01"));
		get.addHeader(new BasicHeader("Accept", "*/*"));
		get.addHeader(new BasicHeader("User-Agent", "PostmanRuntime/7.20.1"));
		get.addHeader(new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT x.y; Win64; x64; rv:10.0) Gecko/20100101 Firefox/10.0"));
		try {
			res = httpClient.execute(get, context);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 本地保存
		try {
//			saveImage(res.getEntity().getContent(), location);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String code = null;
		try {
			code = ocrImg(location);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ret.put("code", code);
		return ret;
	}


	public String ocrImg(String location) throws Exception{
		ITesseract instance = new Tesseract();
		//如果未将tessdata放在根目录下需要指定绝对路径
		instance.setDatapath("tessdata");
		//如果需要识别英文之外的语种，需要指定识别语种，并且需要将对应的语言包放进项目中
		instance.setLanguage("eng");
		// 指定识别图片
		File imgDir = new File(location);
		String ocrResult = instance.doOCR(imgDir);
		System.out.println(ocrResult);
//        FileUtils.deleteDirectory(srcDir);
		return ocrResult;
	}


	public static void saveImage(InputStream is, String location) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(location));
			int inByte;
			while((inByte = is.read()) != -1)
				fos.write(inByte);
			is.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 去除干扰线
			ImgUtil.zoomImage("C:/image.jpg", "C:/image.jpg", 750, 300);
            ImgUtil.cleanLinesInImage(new File(location), "C:/");
		}

	}

	public static void main(String[] args) {
		HttpClientUtil util = new HttpClientUtil();
		JSONObject code = util.getCode();
		System.out.println(code.toJSONString());
	}

}
