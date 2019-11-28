package tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import base.Base;
import base.Endpoints;
import base.MethodType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utilities.DBUtility;
import utilities.RestUtilities;
import utilities.SetPayload;
import utilities.updateXML;

import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class GetRequests {
	RequestSpecification reqspec;
	ResponseSpecification resSpec;
	updateXML updatexml;
	
	@BeforeTest
	public void setup() {
		Base.setup();
		Base.generateAuthToken();
		updatexml = new updateXML();
	}

	 //@Test
	public void getContent() {
		RestUtilities.setEndpoint(Endpoints.GETCONTENT);
		reqspec = RestUtilities.getRequestSpecification();
		reqspec.header(new Header("kmauthtoken", Base.config.getString("kmauthtoken")));
		Response resp = RestUtilities.getResponse(MethodType.GET, null);
		resp.then().spec(RestUtilities.getResponseSpecification());
		resp.then().assertThat().header("Access-Control-Allow-Origin", "*");
//		resp.then().assertThat().statusCode(200);
		resp.then().assertThat().body(containsStringIgnoringCase("FAQ"));
		resp.then().assertThat().body("ResultList.count", equalTo("20"));
	}

	// @Test
	public void getContentByID() {
		RestUtilities.setEndpoint(Endpoints.GETCONTENTBYDOCID);
		reqspec = RestUtilities.getRequestSpecification();
		RestUtilities.setPathParameter("docId", "FA1", reqspec);
		reqspec.header(new Header("kmauthtoken", Base.config.getString("kmauthtoken")));
		RestUtilities.getResponse(MethodType.GET, null).then().log().all(true);
	}

//	@Test
	public void postContent() throws ClassNotFoundException, DOMException, SAXException, ParserConfigurationException, TransformerException {
		RestUtilities.setEndpoint(Endpoints.POSTCONTENT);
		reqspec = RestUtilities.getRequestSpecification();
		reqspec.header(new Header("kmauthtoken", Base.config.getString("kmauthtoken")));
		try {
			SetPayload.preparePayload("./src/test/resources/payloads/postContent.xml");
			RestUtilities.getResponse(MethodType.POST,
					new String(Files.readAllBytes(Paths.get("./src/test/resources/payloads/postContent.xml"))));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterTest
	public void teardown() {
		
	}
}
