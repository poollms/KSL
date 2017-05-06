package com.ksl.projects.Main.Qr.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ksl.projects.Main.Common.view.QRUtil;
import com.ksl.projects.Main.Qr.vo.QrVo;
import com.ksl.projects.Main.Qr.vo.TestVo;

/**
 * Handles requests for the application home page.
 */
@Controller(value = "/main")
public class QrController {
	
	private static final Logger logger = LoggerFactory.getLogger(QrController.class);
	
	@Autowired
	SqlSessionTemplate sqlSession;
	
	@Autowired
	SqlSessionTemplate sqlSession2;
	
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/main.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		//String formattedDate = dateFormat.format(date);
		//model.addAttribute("userId",((QrVo)sqlSession.selectOne("mapper.qr.getQr")).getUserId());
		
		//return "home";
		return "/qrcode/create_qr";
	}
	
	@RequestMapping(value = "/access.do", method = RequestMethod.GET)
	public String testAccess(Model model) {
		
		//String formattedDate = dateFormat.format(date);
		//model.addAttribute("userId",((QrVo)sqlSession.selectOne("mapper.qr.getQr")).getUserId());
		
		//return "home";
		return "/accessClient/access_server";
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	@RequestMapping(value = "/ios.do", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, String> getIos(HttpServletRequest req) {
		
		String loginKey = req.getParameter("loginKey");
        Map<String, String> result = new HashMap<String, String>();
        result.put("data", loginKey+"accessServer");

		return result;
	}
	
	
	@RequestMapping(value = "/main2.do", method = RequestMethod.GET)
	public String test(Locale locale, Model model) {
		    TestVo test = sqlSession2.selectOne("mapper.qr.getTbTest");		
		    model.addAttribute("test",test);
		    
		return "/qrcode/create_qr";
	}
	
	
	//Get Submit
	@RequestMapping(value="/getSubmit.do")
	public String getSubmit(HttpServletRequest request, Model model) {
	    try {
	    	System.out.println("Into the requestType :");
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	        //GET 방식으로 parameter를 전달
	       // String addr = "http://data.ekape.or.kr/openapi-data/service/user/grade/confirm/issueNo";
	        String param = "animalNo=160053500174&ServiceKey=";
	        String serviceKey = "17ed49dbfb4c31ea51d3bf999e5062c1889c89faf37f6a421468f01cc85bafe9";
	      //  addr = addr + param + serviceKey;
	        
	        String addr = "http://175.125.91.94/oasis/service/rest/convergence/conver6?numOfRows=2";
	        
	        HttpGet httpGet = new HttpGet(addr);
	        System.out.println("addr :"+addr);
	        CloseableHttpResponse response = httpclient.execute(httpGet);
	        System.out.println("addr2 :"+addr);
	        logger.debug("addr:"+addr);
	        try {

	            //System.out.println(response.getStatusLine());
	            //API서버로부터 받은 JSON 문자열 데이터

	           // System.out.println(EntityUtils.toString(response.getEntity()));
	            
	            HttpEntity entity = response.getEntity();
	            EntityUtils.consume(entity);
	            
	            model.addAttribute("responseXml",exampleApi());

		         
	        } finally {

	            response.close();
	        }   

	    } catch (Exception e) {

	        e.printStackTrace();

	    }
	    
	  //  exampleApi();
	    
	    return "/qrcode/create_qr";

	}

	
	
	/**
	 * Qr코드의 값을 받아서 qr코드로 변환 
	 * ResponseBody를 사용하면 파라미터와 리턴값 모두 자동으로 json으로 변하ㅗㄴ
	 * @param param : ajax로 온 parameter값
	 */
	@ResponseBody
	@RequestMapping(value = "createCode.do", method = RequestMethod.POST)
	public HashMap<String, Object> createCode(HttpServletRequest request,@RequestParam HashMap<String, Object> param) {
	    System.out.println(param);
	    System.out.println("content is "+(String)param.get("content"));
	    
	
	    
	    
	    HashMap<String, Object> hashmap = new HashMap<String, Object>();
		String fileName = makeQrcode((String)param.get("content"),request);
	    hashmap.put("qrUrl", fileName);
	    hashmap.put("name", "성정훈");
	    
	    System.out.println("qrUrl is "+fileName);
	    QrVo qrVo = new QrVo();
	    qrVo.setUniKey((String)param.get("content"));
	    qrVo.setUserId("kyh"); //임시값
	    qrVo.setQrUrl(fileName);
	    sqlSession.insert("mapper.qr.insertQr",qrVo);

	    return hashmap;

	}
	
//	@RequestMapping(value= "/ajax.seo", method=RequestMethod.GET)
//	public ModelAndView AjaxView( @RequestParam("id") String id)  {  
//	    ModelAndView mav= new ModelAndView();
//
//	    SocialPerson person = dao.getPerson(id);
//	    mav.addObject("person",person);
//	    mav.setViewName("jsonView");
//	    return mav;
//	}
	

	/**
	 * QR코드 유틸
	 * @param url : QR에 작성할 URL이다 
	 */
	public String makeQrcode(String url,HttpServletRequest request){
		int width = 100; 
		int height = 100; 
		
	    String root_path = request.getSession().getServletContext().getRealPath("/");  
	    String attach_path = "resources/upload/";

	    
		String filePath = "C:"+File.separator+"qr"+File.separator; 
		String fileName = "qr_"+ System.currentTimeMillis()+".png"; 
		QRUtil.makeQR(url, width, height, filePath, fileName);
		
		QRUtil.makeQR(url, width, height, root_path+attach_path, fileName);
		
		
		System.err.println(root_path+attach_path+fileName);
		//return filePath + fileName;
		return attach_path+fileName;
	}
	
	public StringBuilder exampleApi(){
		//  StringBuilder urlBuilder = new StringBuilder("http://www.emuseum.go.kr/openapi/relic/detail"); /*URL*/
			StringBuffer urlBuilder = new StringBuffer("http://175.125.91.94/oasis/service/rest/convergence/conver7");
		try{
//			urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=서비스키"); /*Service Key*/
//	        urlBuilder.append("&" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("testkey", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
//	        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
//	        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
//	        urlBuilder.append("&" + URLEncoder.encode("id","UTF-8") + "=" + URLEncoder.encode("PS0100100100100240600000", "UTF-8")); /*단일 소장품의 요약 정보 이용 시 사용*/
	        URL url = new URL(urlBuilder.toString());
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/json");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
	            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
	        } else {
	            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
	        }
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = rd.readLine()) != null) {
	            sb.append(line);
	        }
	        rd.close();
	        conn.disconnect();
	        System.out.println(sb.toString());
	        
	        return sb;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
}
