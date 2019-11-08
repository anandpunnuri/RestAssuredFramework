package utilities;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.activation.CommandInfo;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class SetPayload {
	
	
	public static void preparePayload(String filepath) throws DOMException, ClassNotFoundException, SAXException, IOException, ParserConfigurationException, TransformerException, SQLException
	{
		updateXML updatexml = new updateXML();
		updatexml.updateContentType(filepath);
		updatexml.updateViewinXML(filepath);
	}
//	
//	public static void setContentType(String filepath) throws SQLException, ClassNotFoundException {
//		XMLConfiguration cf = null;
//		String[] CTDetails = DBUtility.getContentTypeDetails("FAQ");
//		try {
//			cf = new XMLConfiguration(new File(filepath));
//		} catch (ConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			cf.setDelimiterParsingDisabled(true);
//			cf.setProperty("Content.contentType.recordId", CTDetails[0]);
//			cf.setProperty("Content.contentType.referenceKey", CTDetails[1]);
//			cf.setProperty("Content.contentType.name", "FAQ");
//			cf.save();
//		} catch (ConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	
//	public static void setView(String filepath) throws SQLException, ClassNotFoundException, SAXException, IOException, ParserConfigurationException, TransformerException {
//		XMLConfiguration cf = null;
//		updateXML.updateViewinXML(filepath);
//		String[] ViewDetails = DBUtility.getViewDetails("enview");
//		try {
//			cf = new XMLConfiguration(new File(filepath));
//		} catch (ConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			cf.setDelimiterParsingDisabled(true);
//			cf.setProperty("Content.contentType.recordId", ViewDetails[0]);
//			cf.setProperty("Content.contentType.referenceKey", ViewDetails[1]);
//			cf.setProperty("Content.contentType.name", ViewDetails[2]);
//			cf.save();
//		} catch (ConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	
//	public static void addView(String filepath)
//	{
//		XMLConfiguration cf = null;
//		try {
//			cf = new XMLConfiguration(new File(filepath));
//		} catch (ConfigurationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		cf.get
//		
//		String viewxml = "<ViewKey><recordId>171515A0EB7E453089019A4D350B168E</recordId><referenceKey>TENANT</referenceKey><name>day103_20200_sql_95h</name></ViewKey>";
//	}
	
}
