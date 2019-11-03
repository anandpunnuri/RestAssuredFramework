package tests;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import base.Base;
import base.Endpoints;
import base.MethodType;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import utilities.RestUtilities;
import static org.hamcrest.Matchers.*;

public class GetRequests {
	RequestSpecification reqspec;
	@BeforeTest
	public void setup()
	{
		Base.setup();
		Base.generateAuthToken();
	}
	
	@Test
	public void getContent()
	{
		RestUtilities.setEndpoint(Endpoints.GETCONTENT);
		reqspec = RestUtilities.getRequestSpecification();
		reqspec.header(new Header("kmauthtoken", Base.config.getString("kmauthtoken")));
		RestUtilities.getResponse(MethodType.GET, null).then().body(containsString("asp"));
	}
	
	@Test
	public void getContentByID()
	{
		RestUtilities.setEndpoint(Endpoints.GETCONTENTBYDOCID);
		reqspec = RestUtilities.getRequestSpecification();
		RestUtilities.setPathParameter("docId", "FA1", reqspec);
		reqspec.header(new Header("kmauthtoken", Base.config.getString("kmauthtoken")));
		RestUtilities.getResponse(MethodType.GET, null).then().log().all(true);
	}
}
