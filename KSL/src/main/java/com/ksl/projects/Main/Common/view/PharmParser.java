package com.ksl.projects.Main.Common.view;
import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
 
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
 
public class PharmParser {
 
    //public final static String PHARM_URL = "http://openapi.hira.or.kr/openapi/service/pharmacyInfoService/getParmacyBasisList";
    public final static String KEY = "??????????????????";
    public final static String PHARM_URL = "http://175.125.91.94/oasis/service/rest/convergence/conver7?numOfRows=100";
    
    public PharmParser() {
        try {
            apiParserSearch();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
 
    
    /**
     * 
     * @throws Exception
     */
    public void apiParserSearch() throws Exception {
        URL url = new URL(getURLParam(null));
 
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        xpp.setInput(bis, "utf-8");
        
        String tag = null;
        int event_type = xpp.getEventType();
        
        ArrayList<String> list = new ArrayList<String>();
        
        String addr = null;
        while (event_type != XmlPullParser.END_DOCUMENT) {
            if (event_type == XmlPullParser.START_TAG) {
                tag = xpp.getName();
            } else if (event_type == XmlPullParser.TEXT) {
            	if(tag.equals("creator"))
            		System.out.println(xpp.getText());
               
            } else if (event_type == XmlPullParser.END_TAG) {
                tag = xpp.getName();
                if (tag.equals("item")) {
                    list.add(addr);
                }
            }
 
            event_type = xpp.next();
        }
        printList(list);
    }
    
    /**
     * 결과 값을 출력해본다.
     * @param list
     */
    private void printList(ArrayList<String> list){
        for(String entity : list){
            System.out.println(entity);
        }
        
        
    }
    
    
    
    private String getURLParam(String search){
        //String url = PHARM_URL+"?ServiceKey="+KEY;
    	String url = PHARM_URL;
        
        return url;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        new PharmParser();
    }
}