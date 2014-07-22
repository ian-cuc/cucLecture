package webChat.java;
import getLession.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.catalina.startup.SetContextPropertiesRule;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class txtRetu
 */
@WebServlet("/txtRetu")
public class txtRetu extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static  HttpServletRequest final_request;
    private static  HttpServletResponse final_response;
    private static String TOKEN="yinyien";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public txtRetu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		final_request=request;
		final_response=response;
		valid();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

	public void valid( ){  
        String echostr=final_request.getParameter("echostr");  
        if(null==echostr||echostr.isEmpty()){  
            try {
				responseMsg();
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
        }else{  
            if(this.checkSignature()){  
                this.print(echostr);  
            }else{  
                this.print("error");                                                                                                                                                                                                                                                                                                                                           
            }  
        }  
    }  
    //自动回复内容  
    public void responseMsg() throws XPathExpressionException, IOException, ParserConfigurationException, SAXException{  
        String postStr=null;  
        try{  
        	//获取了输入流的参数；这是接收到的xml数据
            postStr=this.readStreamParameter(final_request.getInputStream());  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
        //System.out.println(postStr);  
        if (null!=postStr&&!postStr.isEmpty()){  
            Document document=null;  
            try{  
                document = DocumentHelper.parseText(postStr);  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
            if(null==document){  
                this.print("");  
                return;  
            }  
            Element root=document.getRootElement();  
            //get user name;
            String fromUsername = root.elementText("FromUserName");  
            //get my account;
            String toUsername = root.elementText("ToUserName");  
            //获取文本消息内容
            String keyword = root.elementTextTrim("Content");  
//            String keyword=root.elementText("Content"); 
            String time = new Date().getTime()+"";  
//            String textTpl = "<xml>"+  
//                        "<ToUserName><![CDATA[%1$s]]></ToUserName>"+  
//                        "<FromUserName><![CDATA[%2$s]]></FromUserName>"+  
//                        "<CreateTime>%3$s</CreateTime>"+  
//                        "<MsgType><![CDATA[%4$s]]></MsgType>"+  
//                        "<Content><![CDATA[%5$s]]></Content>"+  
//                        "<FuncFlag>0</FuncFlag>"+  
//                        "</xml>";               
              if(keyword.equalsIgnoreCase("aaaa"))
              {
            	  post post01=new post();
            	  String content01=null;
            	  
				content01=post01.getLessons("201111113022","shadow2wind");
				
         	  String textTpl = "<xml>"+  
                          "<ToUserName><![CDATA["+fromUsername+"]]></ToUserName>"+  
                          "<FromUserName><![CDATA["+toUsername+"]]></FromUserName>"+  
                          "<CreateTime>"+time+"</CreateTime>"+  
                          "<MsgType><![CDATA[text]]></MsgType>"+  
                          "<Content><![CDATA["+content01+"]]></Content>"+  
                          "<FuncFlag>0</FuncFlag>"+  
                          "</xml>";               
            	  this.print(textTpl);  
              }
//            if(keyword.equals("aaaa"))  
//            {  
//                String msgType = "text";  
//                String contentStr = "Welcome to wechat world!";  
//                String resultStr = String.format(textTpl, fromUsername, toUsername, time, msgType, contentStr);  
//                this.print(resultStr);  
//            }else{  
//                this.print("Input something...");  
//            }  
//  
//        }
        else {  
            this.print("aaaa");  
        }  
        }
        
        
    }  
    //微信接口验证  
    public boolean checkSignature(){  
        String signature = final_request.getParameter("signature");  
        String timestamp = final_request.getParameter("timestamp");  
        String nonce = final_request.getParameter("nonce");  
        String token=TOKEN;  
        String[] tmpArr={token,timestamp,nonce};  
        Arrays.sort(tmpArr);  
        String tmpStr=this.ArrayToString(tmpArr);  
        tmpStr=this.SHA1Encode(tmpStr);  
        if(tmpStr.equalsIgnoreCase(signature)){  
            return true;  
        }else{  
            return false;  
        }  
    }  
    //向请求端发送返回数据  
    public void print(String content){  
        try{  
            final_response.getWriter().print(content);  
            final_response.getWriter().flush();  
            final_response.getWriter().close();  
        }catch(Exception e){  
              
        }  
    }  
    //数组转字符串  
    public String ArrayToString(String [] arr){  
        StringBuffer bf = new StringBuffer();  
        for(int i = 0; i < arr.length; i++){  
         bf.append(arr[i]);  
        }  
        return bf.toString();  
    }  
    //sha1加密  
    public String SHA1Encode(String sourceString) {  
        String resultString = null;  
        try {  
           resultString = new String(sourceString);  
           MessageDigest md = MessageDigest.getInstance("SHA-1");  
           resultString = byte2hexString(md.digest(resultString.getBytes()));  
        } catch (Exception ex) {  
        }  
        return resultString;  
    }  
    public final String byte2hexString(byte[] bytes) {  
        StringBuffer buf = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            if (((int) bytes[i] & 0xff) < 0x10) {  
                buf.append("0");  
            }  
            buf.append(Long.toString((int) bytes[i] & 0xff, 16));  
        }  
        return buf.toString().toUpperCase();  
    }  
    //从输入流读取post参数  
    public String readStreamParameter(ServletInputStream in){  
        StringBuilder buffer = new StringBuilder();  
        BufferedReader reader=null;  
        try{  
            reader = new BufferedReader(new InputStreamReader(in));  
            String line=null;  
            while((line = reader.readLine())!=null){  
                buffer.append(line);  
            }  
        }catch(Exception e){  
            e.printStackTrace();  
        }finally{  
            if(null!=reader){  
                try {  
                    reader.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        return buffer.toString();  
    }  
  
}
