package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.Base;
import base.Endpoints;
import base.MethodType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utilities.RestUtilities;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetRequests {
	RequestSpecification reqspec;
	ResponseSpecification resSpec;
	@BeforeTest
	public void setup()
	{
		Base.setup();
		Base.generateAuthToken();
	}
	
	//@Test
	public void getContent()
	{
		RestUtilities.setEndpoint(Endpoints.GETCONTENT);
		reqspec = RestUtilities.getRequestSpecification();
		reqspec.header(new Header("kmauthtoken", Base.config.getString("kmauthtoken")));
		Response resp = RestUtilities.getResponse(MethodType.GET, null);
		resp.then().spec(RestUtilities.getResponseSpecification());
		resp.then().assertThat().header("Access-Control-Allow-Origin", "*");
//		resp.then().assertThat().statusCode(200);
		resp.then().assertThat().body(containsStringIgnoringCase("FAQ"));
		resp.then().assertThat().body("ResultList.count",equalTo("20"));
	}
	
	//@Test
	public void getContentByID()
	{
		RestUtilities.setEndpoint(Endpoints.GETCONTENTBYDOCID);
		reqspec = RestUtilities.getRequestSpecification();
		RestUtilities.setPathParameter("docId", "FA1", reqspec);
		reqspec.header(new Header("kmauthtoken", Base.config.getString("kmauthtoken")));
		RestUtilities.getResponse(MethodType.GET, null).then().log().all(true);
	}

	@Test
public void postContent()
{
	RestUtilities.setEndpoint(Endpoints.POSTCONTENT);
	reqspec = RestUtilities.getRequestSpecification();
	reqspec.header(new Header("kmauthtoken", Base.config.getString("kmauthtoken")));
	try {
		RestUtilities.getResponse(MethodType.POST, new String(Files.readAllBytes(Paths.get("./src/test/resources/payloads/postContent.xml"))));
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}
