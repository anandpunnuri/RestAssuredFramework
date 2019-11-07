package utilities;

import java.io.File;
import java.sql.SQLException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class SetPayload {

	public static void setContentType(String filepath) throws SQLException, ClassNotFoundException {
		XMLConfiguration cf = null;
		String[] CTDetails = DBUtility.getContentTypeDetails("FAQ");
		try {
			cf = new XMLConfiguration(new File(filepath));
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cf.setDelimiterParsingDisabled(true);
			cf.setProperty("Content.contentType.recordId", CTDetails[0]);
			cf.setProperty("Content.contentType.referenceKey", CTDetails[1]);
			cf.setProperty("Content.contentType.name", "FAQ");
			cf.save();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void setView(String filepath) throws SQLException, ClassNotFoundException {
		XMLConfiguration cf = null;
		String[] CTDetails = DBUtility.getContentTypeDetails("FAQ");
		try {
			cf = new XMLConfiguration(new File(filepath));
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			cf.setDelimiterParsingDisabled(true);
			cf.setProperty("Content.contentType.recordId", CTDetails[0]);
			cf.setProperty("Content.contentType.referenceKey", CTDetails[1]);
			cf.setProperty("Content.contentType.name", "FAQ");
			cf.save();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
