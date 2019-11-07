package tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

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

import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

public class GetRequests {
	RequestSpecification reqspec;
	ResponseSpecification resSpec;

	@BeforeTest
	public void setup() {
		Base.setup();
		Base.generateAuthToken();
	}

	// @Test
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

	@Test
	public void postContent() throws ClassNotFoundException {
		RestUtilities.setEndpoint(Endpoints.POSTCONTENT);
		reqspec = RestUtilities.getRequestSpecification();
		reqspec.header(new Header("kmauthtoken", Base.config.getString("kmauthtoken")));
		try {
			SetPayload.setContentType("./src/test/resources/payloads/postContent.xml");
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
		try {
			DBUtility.con.close();
			System.out.println("Is DB Connection closed after test complete: " + DBUtility.con.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
