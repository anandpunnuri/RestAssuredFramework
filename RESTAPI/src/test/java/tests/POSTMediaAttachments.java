package tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.Base;
import base.Endpoints;
import base.MethodType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import utilities.DBUtility;
import utilities.RestUtilities;
import utilities.updateXML;

public class POSTMediaAttachments {

	updateXML updatexml;
	
	@BeforeTest
	public void setup() {
		Base.setup();
		Base.generateAuthToken();
		updatexml = new updateXML();
	}
	
	@Test
	public void postContentImport() throws IOException
	{
		RestUtilities.setEndpoint(Endpoints.POSTCONTENTIMPORT);
		RequestSpecification reqspec = RestUtilities.getRequestSpecification();
		reqspec.contentType("multipart/form-data");
		reqspec.header(new Header("kmauthtoken", Base.config.getString("kmauthtoken")));
		updatexml.updateContentType("./src/test/resources/payloads/POSTContentwithFilePayload.xml");
		updatexml.updateViewinXML("./src/test/resources/payloads/POSTContentwithFilePayload.xml");
		reqspec.multiPart("filesToUpload", new File("./src/test/resources/filesToUpload/IphoneCamera.jpg"));
//		reqspec.multiPart("compositeContentBO", new String(Files.readAllBytes(Paths.get("./src/test/resources/payloads/POSTContentwithFilePayload.xml"))));
		reqspec.multiPart("compositeContentBO", new File("./src/test/resources/payloads/POSTContentwithFilePayload.xml"));
		RestUtilities.getResponse(MethodType.POST,"");
	}
	
	@AfterTest
	public void teardown() {
		try {
			DBUtility.con.close();
			System.out.println("Is DB Connection closed after test complete: " + DBUtility.con.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
