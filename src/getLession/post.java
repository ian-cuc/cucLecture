package getLession;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;  
 
import javax.xml.xpath.XPathExpressionException;  

import org.w3c.dom.Document;  
  
import org.xml.sax.SAXException;  

import com.sina.sae.storage.SaeStorage;

import java.io.BufferedReader;    

import java.io.InputStreamReader;    

import java.net.HttpURLConnection;
import java.net.URL;    

import java.util.regex.Matcher;
import java.util.regex.Pattern;

  
public  class post
{    
  
  
   public  String viewPage(String Cookie) throws IOException  
   {  
       StringBuilder sbR = new StringBuilder();  
       
       URL url1 = new URL("http://jw.cuc.edu.cn/academic/student/currcourse/currcourse.jsdo?groupId=&moduleId=2000");  
       HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();  
       
       connection1.setRequestProperty("Cookie", Cookie);  
      
       BufferedReader br1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));  
       
       String line1= br1.readLine();  
       while (line1 != null) {  
           sbR.append(line1); 
           sbR.append("\n");
           line1 = br1.readLine();  
       }  
       return sbR.toString();    
   }     
   public static String getCookie(String a) throws IOException{
	     String rge1="jsessionid=\\w*\\.\\w{3}";
	     Pattern p=Pattern.compile(rge1,Pattern.CASE_INSENSITIVE);
	     Matcher mm=p.matcher(a);
	    if( mm.find()){
	     String cookie=mm.group();
	     return cookie;
	    }
	    return "wrong";
		
	}
   
  public  String getLessons(String username ,String password) throws IOException, XPathExpressionException, ParserConfigurationException, SAXException {   
  post post2=new post();
  webPost webpost=new webPost();
  //获取课表原网页的代码
  String LessonCode=post2.getHtmlCode(username, password);
  if(LessonCode.equals("username or password is wrong"))
  {
      return LessonCode;
  }
  
  
//  
//  
  String domian="htmlcode";
  SaeStorage storage=new SaeStorage();
  storage.write(domian, username+".html", LessonCode);
  String lessonUrl="http://cuc1010-htmlcode.stor.sinaapp.com/"+username+".html";
//  "http://cuc1010-htmlcode.stor.sinaapp.com/"+
//  String lessonUrl=" tmpDir/SaeStoageDir/"+"cuc1010"+"/"+domian+"/"+username+".html";

  
  
//  CreateNewFile createFile = new CreateNewFile();
//  String lessonUrl="E:\\htmlCode\\"+username+".html";
//  createFile.createNewFile(lessonUrl, LessonCode);
//  
  
  Document doc = NekoHtmlAndXPath.getDocument(lessonUrl);
  String weekDay=getWeekDay();
  String lesson=webpost.getgetlesson(doc,weekDay,username);
  return lesson;
  
   }    

  public static void main(String[] arg) throws XPathExpressionException, IOException, ParserConfigurationException, SAXException{
	  post post1=new post();
	  String a=post1.getHtmlCode("201111113022","shadow2wind");
//	  String a=post1.getHtmlCode("201206113009","201206113009");
	  System.out.println(a);
  }
  
  public  String getHtmlCode(String  username0 ,String password) throws IOException {    
      
      String surl = "http://jw.cuc.edu.cn/academic/j_acegi_security_check"; 
      HttpURLConnection httpurlconnection = null;
      
      URL url = new URL(surl);  
      httpurlconnection = (HttpURLConnection)url.openConnection();
      httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
      httpurlconnection.setRequestProperty("Host", "jw.cuc.edu.cn");
      String strPost = "j_username="+username0+"&j_password="+password+"&groupId=";
      httpurlconnection = (HttpURLConnection) url.openConnection();
      httpurlconnection.setFollowRedirects(true);
      httpurlconnection.setInstanceFollowRedirects(true);
      httpurlconnection.setDoOutput(true); 
      httpurlconnection.setInstanceFollowRedirects(false); 
      httpurlconnection.setUseCaches(false); 
      httpurlconnection.setRequestMethod("POST");
      httpurlconnection.addRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
      httpurlconnection.setRequestProperty("Referer","http://jw.cuc.edu.cn/academic/common/security/login.jsp?login_error=1");
      httpurlconnection.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
      httpurlconnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      httpurlconnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
      httpurlconnection.setRequestProperty("Accept","gzip,deflate,sdch");
      httpurlconnection.setRequestProperty("Accept","gzip,deflate,sdch");
      httpurlconnection.setRequestProperty("Host", "jw.cuc.edu.cn");
      httpurlconnection.setRequestProperty("Content-Length", strPost.length()+ "");
      httpurlconnection.setRequestProperty("Connection", "Keep-Alive");
      httpurlconnection.setRequestProperty("Cache-Control", "no-cache");
      httpurlconnection.getOutputStream().write(strPost.getBytes());
      httpurlconnection.getOutputStream().flush();
      httpurlconnection.getOutputStream().close();
      httpurlconnection.connect();
      int code = httpurlconnection.getResponseCode();
    
      String cookie1 = httpurlconnection.getHeaderField("Set-Cookie");
      cookie1 = getCookie(cookie1);
      StringBuilder sbR = new StringBuilder();  
      URL url1 = new URL("http://jw.cuc.edu.cn/academic/student/currcourse/currcourse.jsdo?groupId=&moduleId=2000");  
      HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();        
       
      connection1.setRequestProperty("Cookie", cookie1);   

//      BufferedReader br1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));   
//      String line1= br1.readLine();  
//      while (line1 != null) {  
//          sbR.append(line1); 
//          sbR.append("\n");
//          line1 = br1.readLine();  
//      }  
//      String htmlCode=sbR.toString();
      
      
      byte[] buffer=new byte[1024*45];
      int len=-1;
      ByteArrayOutputStream outSteam = new ByteArrayOutputStream(); 
      while( (len = connection1.getInputStream().read(buffer)) != -1 ){ 

    	  outSteam.write(buffer, 0, len);  
    	  } 
      outSteam.close(); 
      byte[] b=outSteam.toByteArray();
      String htmlCode=new String(b,"GBK");
      
      
      connection1.getInputStream().close();
      httpurlconnection.disconnect();
      connection1.disconnect();
      if(code!=302&code!=200)
      {
    	  return "username or password is wrong";
      }
      return htmlCode;
//      return doc;
  }    
  
  
  public static String getWeekDay()
	{
		int weekDayNum = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_WEEK);
		String WeekDay=null;
		switch(weekDayNum)
		{
		case 1: WeekDay="周日";
		break;	
		case 2:WeekDay="周一";
		break;
		case 3:WeekDay="周二";
		break;
		case 4:WeekDay="周三";
		break;
		case 5:WeekDay="周四";
		break;
		case 6:WeekDay="周五";
		break;
		case 7:WeekDay="周六";
		break;
		}
		return WeekDay;
		
	}

  
}    
