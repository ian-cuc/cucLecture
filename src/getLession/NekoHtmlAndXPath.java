package getLession;


import java.io.IOException;
 
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;  
import java.io.InputStream;  

import javax.xml.parsers.DocumentBuilder;  
import javax.xml.parsers.DocumentBuilderFactory;  
import javax.xml.parsers.ParserConfigurationException;  
import javax.xml.xpath.XPath;  
import javax.xml.xpath.XPathConstants;  
import javax.xml.xpath.XPathExpression;  
import javax.xml.xpath.XPathExpressionException;  
import javax.xml.xpath.XPathFactory;  

import org.w3c.dom.Document;  
import org.w3c.dom.NodeList;  
import org.xml.sax.SAXException;  

import org.apache.xpath.XPathAPI;
import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
 
public class NekoHtmlAndXPath {
    // 通过url，将相应的html解析为DOM文档
    public static Document getDocument(String url) {
        DOMParser parser = new DOMParser();
        try {
            parser.parse(url);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document doc = parser.getDocument();
        return doc;
    }
 
    // 通过XPath定位具体的节�?
    public static NodeList getExactNode(Document doc, String xp) {
        NodeList list = null;
        try {
            list = XPathAPI.selectNodeList(doc, xp);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return list;
    }

}