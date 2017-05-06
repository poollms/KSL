package com.ksl.projects.Main.getApi.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	@Autowired
	SqlSessionTemplate sqlSession2;
	

	//Get Submit
	@RequestMapping(value="/getMuseumList.do")
	@ResponseBody
	public Map<String, String> getSubmit(HttpServletRequest request, Model model) {
		//http://openapi.seoul.go.kr:8088/(¿Œ¡ı≈∞)/xml/SebcMuseumInfoKor2/1/5/
		String museUrl = "http://openapi.seoul.go.kr:8088/";
		String serviceKey = "6a645a464d726c643130304f67676f62";
		String strFix = "/json/SebcMuseumInfoKor2/1/10/";
		System.out.println(museUrl+serviceKey+strFix);
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put("museData", getApi(museUrl+serviceKey+strFix));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return map;

	}

	public String getApi(String apiUrl) throws IOException{
		InputStreamReader reader = null;
		BufferedReader rd = null;
	        try {
	            URL url = new URL(apiUrl);
	            rd = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
	            StringBuffer buffer = new StringBuffer();
//	            int i;
//	            byte[] b = new byte[4096];
//	            while( (i = reader.read(b)) != -1){
//	              buffer.append(new String(b, 0, i));
		        StringBuilder sb = new StringBuilder();
		        String line;
		        while ((line = rd.readLine()) != null) {
		            sb.append(line);
		        }
		        
	            return sb.toString();
	        } finally {
	            if (reader != null)
	                reader.close();
	            if (rd != null)
	            	rd.close();
	        }

	}
	
	
}
