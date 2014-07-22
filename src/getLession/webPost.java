package getLession;
import java.io.File;
import java.io.IOException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sina.sae.storage.SaeStorage;
public class webPost
{
	
//获取今日课表
	public String getgetlesson(Document doc,String weekdayFormWebchat,String username) throws XPathExpressionException, ParserConfigurationException, SAXException, IOException{
		   StringBuilder sbR = new StringBuilder();  
		   String root="/HTML/BODY/TABLE[4]/TR[";
		   String root1="]/TD[";
		   String root2="]/TABLE/TR/TD[";

		  XPathFactory factory = XPathFactory.newInstance();  
		  XPath xpath = factory.newXPath();  
		  NodeList nodes=(NodeList)xpath.evaluate("/HTML/BODY/TABLE[4]/TR", doc,XPathConstants.NODESET);
		  int lessonNum=nodes.getLength();
//		  System.out.println(lessonNum);
		  String lessonXpath=null;
		  String lessonAttXpath=null;
		  String lessonName=null;
		  String lessonAtt=null;
		  sbR.append("<?xml   version=\"1.0\"   encoding=\"GB2312\"?>\r\n");
		  sbR.append("<lessonsum>\r\n");
		  for(int i=2;i<=lessonNum;i++)
		  {
			  
			  lessonXpath = root+i+root1+3+"]/A";
//			  System.out.println(lessonXpath);
			  lessonName = xpath.evaluate(lessonXpath, doc);
//			  System.out.println(lessonName);
			  sbR.append("<lesson>\r\n");
			  sbR.append("<lessonName>"+lessonName+"</lessonName>\r\n");
			  for(int y=1;y<=3;y++){
			  for(int j=1;j<=4;j++)
			  {
				  lessonAttXpath=root+i+root1+10+"]/TABLE/TR["+y+"]/TD["+j+"]";
				  lessonAtt=xpath.evaluate(lessonAttXpath, doc);
				  sbR.append("<lessonName"+y+j+">"+lessonAtt+"</lessonName"+y+j+">\r\n");
			  }
			
			  }
			  sbR.append("</lesson>\r\n");
		  }
		  sbR.append("</lessonsum>");
		  
		
		  String lessonXml=null;
		  lessonXml=sbR.toString();
		  System.out.println(lessonXml);
//		  CreateNewFile createFile = new CreateNewFile();
//		  createFile.createNewFile("F:\\"+username+".txt", lessonXml);
//		   return sbR.toString();
		 
		  String Last=xml(lessonXml, weekdayFormWebchat, username);
		  return Last;
		   
	}
	
	public  String xml(String lessonXml, String weekdayFormWebchat,String username) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
		
		 XPathFactory xpfactory = XPathFactory.newInstance();  
		  XPath xpath = xpfactory.newXPath();  
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		DocumentBuilder builder =factory.newDocumentBuilder();
		
		 String domian="htmlcode";
		  SaeStorage storage=new SaeStorage();
		  storage.write(domian, username+".xml", lessonXml);
		  
		  String lessonUrl="http://cuc1010-htmlcode.stor.sinaapp.com/"+username+".xml";
////		"http://cuc1010-htmlcode.stor.sinaapp.com/"+
//		  String lessonUrl=" tmpDir/SaeStoageDir/"+"cuc1010"+"/"+domian+"/"+username+".xml";
		  
//		 CreateNewFile createFile = new CreateNewFile();
//		 createFile.createNewFile("E:\\xmlCode\\"+username+".xml", lessonXml);
//		  String lessonUrl="E:\\xmlCode\\"+username+".xml";
		 
		 
		 
		Document DocWeek=builder.parse(lessonUrl);

		StringBuilder TodayLesson = new StringBuilder();
		 for(int i=1;i<=9;i++){
			 for(int j=1;j<=3;j++){
				 String WeekDayPath="/lessonsum/lesson["+i+"]/lessonName"+j+2;
				 String Weekday=xpath.evaluate(WeekDayPath, DocWeek);
				 Weekday=Weekday.trim();
//				 TodayLesson.append(Weekday);
//				 System.out.println(Weekday);
				 if(Weekday.equals(weekdayFormWebchat))
				 {
//					 System.out.println("锟斤拷锟�);
//					 //
					 String lessonNameToday=null;
					 lessonNameToday=xpath.evaluate("/lessonsum/lesson["+i+"]/lessonName",DocWeek);	
					 TodayLesson.append(lessonNameToday);
					 //
					 String lessonDayTime=null;
					 lessonDayTime=xpath.evaluate("/lessonsum/lesson["+i+"]/lessonName"+j+3,DocWeek);	
					 TodayLesson.append(lessonDayTime);
					 //
					 String lessonRoom=null;
					 lessonRoom=xpath.evaluate("/lessonsum/lesson["+i+"]/lessonName"+j+4,DocWeek);	
					 TodayLesson.append(lessonRoom);
					 //
					 String lessonweekTime=null;
					 lessonweekTime=xpath.evaluate("/lessonsum/lesson["+i+"]/lessonName"+j+1,DocWeek);	
					 TodayLesson.append(lessonweekTime);
					 TodayLesson.append("\n");
				 }
			 }

		 }
		 return TodayLesson.toString();
//		 return TodayLesson.toString();
//		 return "";
	}
	
	
	
//	public  String getTodayLesson(String username) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException{
//		
//		 XPathFactory xpfactory = XPathFactory.newInstance();  
//		  XPath xpath = xpfactory.newXPath();  
//		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder =factory.newDocumentBuilder();
//		
//		Document DocWeek=builder.parse("E:\\xmlCode\\"+username+".xml");
////		createFile.deleteFile(username+".xml");
//		StringBuilder TodayLesson = new StringBuilder();
//		 for(int i=1;i<=9;i++){
//			 for(int j=1;j<=3;j++){
//				 String WeekDayPath="/lessonsum/lesson["+i+"]/lessonName"+j+2;
//				 String Weekday=xpath.evaluate(WeekDayPath, DocWeek);
//				 Weekday=Weekday.trim();
////				 TodayLesson.append(Weekday);
////				 System.out.println(Weekday);
//				 if(Weekday.equals(post.getWeekDay()))
//				 {
////					 System.out.println("锟斤拷锟�);
////					 //
//					 String lessonNameToday=null;
//					 lessonNameToday=xpath.evaluate("/lessonsum/lesson["+i+"]/lessonName",DocWeek);	
//					 TodayLesson.append(lessonNameToday);
//					 //
//					 String lessonDayTime=null;
//					 lessonDayTime=xpath.evaluate("/lessonsum/lesson["+i+"]/lessonName"+j+3,DocWeek);	
//					 TodayLesson.append(lessonDayTime);
//					 //
//					 String lessonRoom=null;
//					 lessonRoom=xpath.evaluate("/lessonsum/lesson["+i+"]/lessonName"+j+4,DocWeek);	
//					 TodayLesson.append(lessonRoom);
//					 //
//					 String lessonweekTime=null;
//					 lessonweekTime=xpath.evaluate("/lessonsum/lesson["+i+"]/lessonName"+j+1,DocWeek);	
//					 TodayLesson.append(lessonweekTime);
//					 TodayLesson.append("\n");
//				 }
//			 }
//
//		 }
//		 return TodayLesson.toString();
//	}
	


	
}