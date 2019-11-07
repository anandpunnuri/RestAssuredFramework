package utilities;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class updateXML {

//	public static void main(String[] args)  throws SAXException, IOException, ParserConfigurationException, TransformerException, ConfigurationException, InterruptedException
//	{
		
	public static void updateViewinXML(String filepath) throws SAXException, IOException, ParserConfigurationException, TransformerException
	{
		// Code to get the number of views present in the testdata.xml
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(new File("./src/test/resources/testData/testdata.xml"));

		NodeList parent = document.getElementsByTagName("views");
		System.out.println("Node is: "+parent.item(0).getNodeName().toString());
		NodeList children = parent.item(0).getChildNodes();
		System.out.println("number of children views: "+children.getLength());
		int numOfChildren =0;
		for(int i=0;i<children.getLength();i++)
				{
			System.out.println("Child node name: "+children.item(i).getNodeName().toString());
			if(children.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				numOfChildren++;
			}
				}
//		int numOfChildren = children.getLength();
		
// ---------------------------------------------------------------------------------------------------------------------------------
		//Code to add views
				 
		DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder2 = factory2.newDocumentBuilder();
		Document document2 = builder2.parse(new File("./src/test/resources/payloads/postContent.xml"));
		document2.getDocumentElement().normalize();
		
		// First need to Remove all child nodes of views node
		NodeList node = document2.getElementsByTagName("views");
		removeAll(node.item(0));
		Element element = (Element) document2.getElementsByTagName("views").item(0);
        element.getParentNode().removeChild(element);
        document2.normalize();
		
		//Removed. Now add the views. 
		//Iterate through number of children times and add following:
		// ViewKey, recordId, referenceKey, name of the each view.
        
        boolean doesViewsTagExist=false;
        for(int i=0;i<numOfChildren;i++)
        {
			Element views = document2.createElement("views");
			Element ViewKey = document2.createElement("ViewKey");
			Element recordId = document2.createElement("recordId");
			recordId.appendChild(document2.createTextNode("aaa"));
			Element referenceKey = document2.createElement("referenceKey");
			referenceKey.appendChild(document2.createTextNode("aaa"));
			Element name = document2.createElement("name");
			name.appendChild(document2.createTextNode("aaa"));
			
			if(!doesViewsTagExist)
			{
			NodeList contents = document2.getElementsByTagName("Content");
			Element content = (Element) contents.item(0);
			content.appendChild(views);
			doesViewsTagExist = true;
			}
			
			document2.getElementsByTagName("views").item(0).appendChild(ViewKey);
			document2.getElementsByTagName("ViewKey").item(i).appendChild(recordId);
			document2.getElementsByTagName("ViewKey").item(i).appendChild(referenceKey);
			document2.getElementsByTagName("ViewKey").item(i).appendChild(name);
			document2.normalize();

        }
		
        doesViewsTagExist=false;
        document2.setXmlStandalone(true);
			document2.getDocumentElement().normalize();
		
			TransformerFactory tfactory = TransformerFactory.newInstance();
			Transformer transformer = tfactory.newTransformer();
			DOMSource source = new DOMSource(document2);
			File resultfile = new File("./src/test/resources/payloads/postContent.xml");
			StreamResult result = new StreamResult(resultfile);
			transformer.setOutputProperty(OutputKeys.INDENT, "no");
			transformer.setOutputProperty(OutputKeys.METHOD, "html");
			transformer.transform(source, result);
//			Thread.sleep(5000);
//			XMLConfiguration cf = new XMLConfiguration(new File("./src/test/resources/payloads/postContent.xml"));
//			cf.setDelimiterParsingDisabled(true);
//			System.out.println(cf.getProperty("Content.contentType.recordId"));
//			cf.setProperty("Content.views.ViewKey.recordId", "123");
//			cf.save();
			System.out.println("XML File updated");
			transformer.clearParameters();
//	}
	}
	
	public static void removeAll(Node node) 
	{
		NodeList nodelist = node.getChildNodes();
    for(int i=0;i<nodelist.getLength();i++)
    {
    	Node n = nodelist.item(i);
        if(n.hasChildNodes()) //edit to remove children of children
        {
          removeAll(n);
          node.removeChild(n);
        }
        else
          node.removeChild(n);
    }
	}
}
