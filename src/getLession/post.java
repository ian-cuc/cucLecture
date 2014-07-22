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
       //闂佺懓鐏氶幐鍝ユ閹鳖槃L闁哄鏅濋崑鐐垫暜閿燂拷
       URL url1 = new URL("http://jw.cuc.edu.cn/academic/student/currcourse/currcourse.jsdo?groupId=&moduleId=2000");  
       HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();  
       //缂傚倷鐒﹂悷锕�耿閸ヮ剙绀夐柍銉ㄦ珪閻濄倝姊洪陇鍏屾繛鍛劥閵囨劙寮撮悙鏉戯拷闂佹眹鍔岄崹绫祇kie  
       connection1.setRequestProperty("Cookie", Cookie);  
       //闁荤姴娲╅褑銇愰崶銊︿氦闁哄倹瀵х粈锟芥煟閵娿儱顏柕鍡楊檨濡啴路瀹ュ嫮绠氶梺璇″弾閸ㄤ即宕虹�鎭�  
       BufferedReader br1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));  
       //闂佸憡鐟﹂悧鐐垫崲閹达箑鐐婇柣鎰仛閻ｅ崬顪㈤妶鍥ㄦ毈婵炵》鎷穊r1闁哄鍋熼幉姒朢  
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
  //闂佸吋鍎抽崲鑼鏉堚晜瀚氶柟瑙勫姂閿熻棄顪㈤妶鍥ㄦ毈婵為棿鍗抽幆鍐礆閻氱尘l婵炲濯寸徊鍧楁偉濠婂拑鎷锋俊銈呭枤閸庡﹤鈽夐幘璇″殝缂佹唻鎷�  
  public  String getHtmlCode(String  username0 ,String password) throws IOException {    
      //闁哄鏅濋崑鐐垫暜閹绢喖鎹堕柡澶嬪缁诧拷
      String surl = "http://jw.cuc.edu.cn/academic/j_acegi_security_check"; 
      HttpURLConnection httpurlconnection = null;
      /**   
       * 婵☆偓绲鹃悧鏇㈠储濞戞碍鍟哄ù锝呮啞鐎氱澔RL婵炴垶鎸搁鍥р枔閹鳖槃LConnection闁诲海鏁搁、濠囨儊娴犲违闁跨喓绁礡LConnection闂佸憡鐟崹顖涚閹烘埈鍤楅柛顐ｇ箥閸熷洭鏌￠崟顒佸暗婵炲牊鍨剁粋鎺戭吋缁傤洃閻庣數澧楅〃婊堝春瀹�嫬违闁稿本绋撳Σ宄扳攽閳ュ啿浜扮紒鎲嬫嫹// Using   
       *  java.net.URL and //java.net.URLConnection   
       */   
      URL url = new URL(surl);  
      httpurlconnection = (HttpURLConnection)url.openConnection();
      httpurlconnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
      httpurlconnection.setRequestProperty("Host", "jw.cuc.edu.cn");
      String strPost = "j_username="+username0+"&j_password="+password+"&groupId=";
      httpurlconnection = (HttpURLConnection) url.openConnection();
      httpurlconnection.setFollowRedirects(true);
      httpurlconnection.setInstanceFollowRedirects(true);
      httpurlconnection.setDoOutput(true); // 闂傚倸娴勯幏鐑解�閸涙潙瑙﹂柟瀵稿仦缁犳盯鏌涢弬鐚存嫹婵炲懏甯″畷妯衡枎閹邦厽顔嶉梺鍦閹凤拷      httpurlconnection.setDoInput(true); // 
      httpurlconnection.setInstanceFollowRedirects(false); 
      httpurlconnection.setUseCaches(false); // 闂佸吋鍎抽崲鑼闁秴瀚夌�銉ユ噹椤偤鏌涢敐鍐ㄥ婵炴挸鐖煎顒勫箥椤旂晫鏆犳繛锝呮礌閸撴繃瀵奸敓锟�     httpurlconnection.setAllowUserInteraction(false);
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