package login;
import getLession.*;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;
import org.apache.*;

@WebServlet("/servlet/login")
public class loginForm extends HttpServlet {
	private static final long serialVersionUID =-3646490724035878521L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
	    response.setContentType("text/html;charset=utf-8");
	    PrintWriter out=response.getWriter();
	    out.println("<html>");
	    out.println("<head><title>login form page</title></head>"); 
	    out.println("<body>"); 
	    out.println("<h2>绑定</h2>"); 
	    out.println("<form method=\"post\" action=\""+request.getContextPath()+"/servlet/login\">");
	    out.println("<table border=\"1\"><tr><td>");
	    out.println("账号</td><td>");
	    out.println("<input type=\"text\" name=\"username\" size=\"20\">");
	    out.println("</td></tr><tr><td>");
	    out.println("密码</td><td>");
	    out.println("<input type=\"password\" name=\"password\" size=\"20\">");
	    out.println("</td></tr><tr><td>&nbsp;</td>");
	    out.println("<td><input type=\"submit\" value=\"登陆\"></td></tr>");
	    out.println("</table></form>");
	    out.println("</body>");
	    out.println("</html>");
	    out.flush();
	    out.close();
	     
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	    throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
	    PrintWriter out=response.getWriter();
	    request.setCharacterEncoding("utf-8");
	    out.println("<html>");
	    out.println("<head><title>display login information</title></head>");
	    out.println("<body>");
	    String username=request.getParameter("username");
	    String password=request.getParameter("password");
	    getLession.post  getLesson=new post();
	    String lesson=null;
	    String lesson1=null;
	    try {
	    	
			lesson=getLesson.getLessons(username, password);
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if (username==null||username.length()==0||password==null||password.length()==0){
	    	out.println("<h2>有问题啦！/h2>");
	    	out.println("<br><a href=\""+request.getContextPath()+"/servlet/login\">有问题啦！</a>");
	    }else{
	    	out.println(lesson1);
	    	out.println(lesson);
	    	
	    }
	    out.println("</body>");
	    out.println("</html>");
	    out.flush();
	    out.close();
	    
	}

}
