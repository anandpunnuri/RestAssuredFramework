package utilities;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class updateXML {

//	public static void main(String[] args)  throws SAXException, IOException, ParserConfigurationException, TransformerException, ConfigurationException, InterruptedException
//	{


	private Document getDocumentObject(String filepath) {
		DocumentBuilderFactory factory;
		DocumentBuilder builder;
		Document document = null;
		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.parse(new File(filepath)); // ./src/test/resources/testData/testdata.xml
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}

	public void updateContentType(String filepath)
	{
		try
		{
		Document testdata = getDocumentObject("./src/test/resources/testData/testdata.xml");
		System.out.println("CTname: "+testdata.getElementsByTagName("contentType").item(0).getFirstChild().getNodeValue());
		String[] CTdetails = DBUtility.getContentTypeDetails(testdata.getElementsByTagName("contentType").item(0).getFirstChild().getNodeValue());
		Document document = getDocumentObject(filepath);
		document.getDocumentElement().normalize();
//		Node contentType = document.getElementsByTagName("contentType").item(0).getFirstChild();
		System.out.println("CT0 "+CTdetails[0]);
		ArrayList<Node> childnodes = getChildNodes(document, "contentType");
		for(int i=0;i<childnodes.size();i++)
		{
			childnodes.get(i).setTextContent(CTdetails[i]);
		}
//		contentType.getFirstChild().setNodeValue(CTdetails[0]);
//		System.out.println("content");
//		contentType.item(0).getFirstChild().setNodeValue(CTdetails[0]);
//		document.getElementsByTagName("recordId").item(0).getFirstChild().setNodeValue(CTdetails[1]);
//		document.getElementsByTagName("referenceKey").item(0).getFirstChild().setNodeValue(CTdetails[1]);
		
//		contentType.getElementsByTagName("referenceKey").item(0).getFirstChild().setNodeValue(CTdetails[1]);
//		contentType.getElementsByTagName("name").item(0).getFirstChild().setNodeValue(CTdetails[1]);
		XMLtransform(document, filepath);
		}
catch(DOMException e)
		{
			System.out.println("DOMException in updateContent type: "+e.getMessage());
		}
		catch(SQLException e)
		{
			System.out.println("SQLException in updateContent type: "+e.getMessage());
		}
		catch(ClassNotFoundException e)
		{
			System.out.println("ClassNotFoundException in updateContent type: "+e.getMessage());
		}
		
		
	}

	public ArrayList<Node> getChildNodes(Document document, String Nodename)
	{
		NodeList parent = document.getElementsByTagName(Nodename);
		System.out.println("Node is: " + parent.item(0).getNodeName().toString());
		NodeList children = parent.item(0).getChildNodes();
		ArrayList<Node> childnodes = new ArrayList<Node>();
		for (int i = 0; i < children.getLength(); i++) {
			System.out.println("Child node name: " + children.item(i).getNodeName().toString());
			if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
				childnodes.add(children.item(i));
			}
		}
		return childnodes;
	}

	public void updateViewinXML(String filepath)
	{
		// Code to get the number of views present in the testdata.xml
//		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder = factory.newDocumentBuilder();
//		Document document = builder.parse(new File("./src/test/resources/testData/testdata.xml"));
try
{
		Document document = getDocumentObject("./src/test/resources/testData/testdata.xml");
		ArrayList<Node> children = getChildNodes(document, "views");
		
//		NodeList parent = document.getElementsByTagName("views");
//		System.out.println("Node is: " + parent.item(0).getNodeName().toString());
//		NodeList children = parent.item(0).getChildNodes();
//		System.out.println("number of children views: " + children.getLength());
//		int numOfChildren = 0;
//		for (int i = 0; i < children.size(); i++) {
//			System.out.println("Child node name: " + children.item(i).getNodeName().toString());
//			if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
//				numOfChildren++;
//			}
//		}
//		int numOfChildren = children.getLength();

// ---------------------------------------------------------------------------------------------------------------------------------
		// Code to add views
		Document document2 = getDocumentObject(filepath); // ./src/test/resources/payloads/postContent.xml
//		DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
//		DocumentBuilder builder2 = factory2.newDocumentBuilder();
//		Document document2 = builder2.parse(new File(filepath)); 
		document2.getDocumentElement().normalize();

		// First need to Remove all child nodes of views node
		NodeList node = document2.getElementsByTagName("views");
		removeAll(node.item(0));
		Element element = (Element) document2.getElementsByTagName("views").item(0);
		element.getParentNode().removeChild(element);
		document2.normalize();

		// Removed. Now add the views.
		// Iterate through number of children times and add following:
		// ViewKey, recordId, referenceKey, name of the each view.

		boolean doesViewsTagExist = false;
		for (int i = 0; i < children.size(); i++) {
			System.out.println("view name is: "+children.get(i).getFirstChild().getNodeValue());
			String[] viewdetails = DBUtility.getViewDetails(children.get(i).getFirstChild().getNodeValue());
			Element views = document2.createElement("views");
			Element ViewKey = document2.createElement("ViewKey");
			Element recordId = document2.createElement("recordId");
			recordId.appendChild(document2.createTextNode(viewdetails[0]));
			Element referenceKey = document2.createElement("referenceKey");
			referenceKey.appendChild(document2.createTextNode(viewdetails[1]));
			Element name = document2.createElement("name");
			name.appendChild(document2.createTextNode(viewdetails[2]));

			if (!doesViewsTagExist) {
				NodeList contents = document2.getElementsByTagName("Content");
				Element content = (Element) contents.item(0);
				content.appendChild(views);
				doesViewsTagExist = true;
			}

			document2.getElementsByTagName("views").item(0).appendChild(ViewKey);
			document2.getElementsByTagName("ViewKey").item(i).appendChild(recordId);
			document2.getElementsByTagName("ViewKey").item(i).appendChild(referenceKey);
			document2.getElementsByTagName("ViewKey").item(i).appendChild(name);
			document2.getDocumentElement().normalize();

		}

		doesViewsTagExist = false;
//		document2.setXmlStandalone(true);
//		document2.getDocumentElement().normalize();
		
		XMLtransform(document2, filepath);
//		TransformerFactory tfactory = TransformerFactory.newInstance();
//		Transformer transformer = tfactory.newTransformer();
//		DOMSource source = new DOMSource(document2);
//		File resultfile = new File("./src/test/resources/payloads/postContent.xml");
//		StreamResult result = new StreamResult(resultfile);
//		transformer.setOutputProperty(OutputKeys.INDENT, "no");
//		transformer.setOutputProperty(OutputKeys.METHOD, "html");
//		transformer.transform(source, result);
//		System.out.println("XML File updated");
}
catch(DOMException e)
{
	System.out.println("DOMException in updateContent type: "+e.getMessage());
}
catch(SQLException e)
{
	System.out.println("SQLException in updateContent type: "+e.getMessage());
}
//catch(ClassNotFoundException e)
//{
//	System.out.println("ClassNotFoundException in updateContent type: "+e.getMessage());
//}
//	}
	}

	public void XMLtransform(Document docsource, String targetfile)
	{
		try
		{
		docsource.setXmlStandalone(true);
		docsource.getDocumentElement().normalize();
		TransformerFactory tfactory = TransformerFactory.newInstance();
		Transformer transformer = tfactory.newTransformer();
		DOMSource source = new DOMSource(docsource);
		File resultfile = new File(targetfile); //"./src/test/resources/payloads/postContent.xml"
		StreamResult result = new StreamResult(resultfile);
		transformer.setOutputProperty(OutputKeys.INDENT, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "html");
		transformer.transform(source, result);
		System.out.println("XML File updated");
		}
		catch(TransformerException e)
		{
			System.out.println("TransformerException in XMLTransform: "+e.getMessage());
		}
	}
	
	public static void removeAll(Node node) 
	{
		NodeList nodelist = node.getChildNodes();
		for (int i = 0; i < nodelist.getLength(); i++) 
		{
			Node n = nodelist.item(i);
			if (n.hasChildNodes()) // edit to remove children of children
			{
				removeAll(n);
				node.removeChild(n);
			} else
				node.removeChild(n);
		}
	}
}
