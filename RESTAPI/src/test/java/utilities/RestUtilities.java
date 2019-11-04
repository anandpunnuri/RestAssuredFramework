package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;

import base.Base;
import base.MethodType;
import groovyjarjarantlr.LexerSharedInputState;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RestUtilities {

	public static RequestSpecBuilder REQ_SPEC_BUILDER = null;
	public static RequestSpecification REQ_SPECIFICATION = null;
	public static ResponseSpecBuilder RES_SPEC_BUILDER = null;
	public static ResponseSpecification RES_SPECIFICATION = null;
	public static String ENDPOINT = null;

	public static void setEndpoint(String endpoint) {
		ENDPOINT = endpoint;
		System.out.println("Endpoint is: " + ENDPOINT);
	}

	public static RequestSpecification getRequestSpecification() {
		REQ_SPEC_BUILDER = new RequestSpecBuilder();
		System.out.println("Base URI: " + Base.config.getString("baseURI"));
		REQ_SPEC_BUILDER.setBaseUri(Base.config.getString("baseURI"));
		REQ_SPEC_BUILDER.setBasePath(Base.config.getString("basePath"));
		REQ_SPEC_BUILDER.setAccept(Base.config.getString("Accept"));
		REQ_SPEC_BUILDER.setContentType(Base.config.getString("ContentType"));
		REQ_SPEC_BUILDER.setRelaxedHTTPSValidation();
		REQ_SPECIFICATION = REQ_SPEC_BUILDER.build();
		return REQ_SPECIFICATION;
	}

	public static ResponseSpecification getResponseSpecification()
	{
		RES_SPEC_BUILDER = new ResponseSpecBuilder();
		RES_SPEC_BUILDER.expectStatusCode(200);
		RES_SPEC_BUILDER.expectResponseTime(lessThan(5000L), TimeUnit.MILLISECONDS);
		RES_SPEC_BUILDER.expectStatusLine(containsStringIgnoringCase("OK"));
		RES_SPECIFICATION=RES_SPEC_BUILDER.build();
		return RES_SPECIFICATION;
	}
	
public static void setRequestHeader(HashMap<String, String> hm, RequestSpecification spec) {
		REQ_SPECIFICATION = REQ_SPECIFICATION.spec(spec);
		REQ_SPECIFICATION.headers(hm);
		System.out.println("Request Headers " + REQ_SPECIFICATION.log().headers());
	}

	public static void setQueryparams(HashMap<String, String> hm, RequestSpecification spec) {
		REQ_SPECIFICATION = REQ_SPECIFICATION.spec(spec);
		REQ_SPECIFICATION.queryParams(hm);
	}

	public static void setQueryparam(String key, String value, RequestSpecification spec) {
		REQ_SPECIFICATION = REQ_SPECIFICATION.spec(spec);
		REQ_SPECIFICATION.queryParam(key, value);
	}

	public static void setQueryparams(String key, ArrayList<String> values, RequestSpecification spec) {
		REQ_SPECIFICATION = REQ_SPECIFICATION.spec(spec);
		REQ_SPECIFICATION.queryParam(key, values);
	}

	public static void setPathParameter(String key, String value, RequestSpecification spec) {
		REQ_SPECIFICATION = REQ_SPECIFICATION.spec(spec);
		REQ_SPECIFICATION.pathParam(key, value);
	}

	public static void setPathParameter(String key, ArrayList<String> values, RequestSpecification spec) {
		REQ_SPECIFICATION = REQ_SPECIFICATION.spec(spec);
		REQ_SPECIFICATION.pathParam(key, values);
	}

	public static void setPathParameter(String key, HashMap<String, String> values, RequestSpecification spec) {
		REQ_SPECIFICATION = REQ_SPECIFICATION.spec(spec);
		REQ_SPECIFICATION.pathParams(values);
	}

	public static String getConfigValue(String key) {
		return Base.config.getString(key);
	}

	public static Response getResponse() {
		Response response = given().spec(REQ_SPECIFICATION).when().get(ENDPOINT);
		return response;
	}

	public static Response getResponse(MethodType methodType, String payload) {
		Response response = null;
		switch (methodType) {
		case POST:
			response = given().spec(REQ_SPECIFICATION).log().all(true).body(payload).when().post(ENDPOINT);
			System.out.println("Response body is: ");
			System.out.print(response.body().asString());
			break;

		case GET:
			response = given().spec(REQ_SPECIFICATION).log().all(true).when().get(ENDPOINT);
			System.out.println("Response body is: ");
			System.out.print(response.body().asString());
			break;

		case PUT:
			response = given().spec(REQ_SPECIFICATION).when().put(ENDPOINT);
			break;

		case DELETE:
			response = given().spec(REQ_SPECIFICATION).when().delete(ENDPOINT);
			break;

		default:
			System.out.println("Method Type is not proper.");
			break;

		}
		return response;
	}

	public static XmlPath getXmlPath(Response response) {
		return response.xmlPath();
	}

	public static JsonPath getJsonPath(Response response) {
		return response.jsonPath();
	}

	public static String getStringValuefromXMLResponse(XmlPath xmlPath, String path) {
		return xmlPath.get(path).toString();
	}

	public static int getIntValuefromXMLResponse(XmlPath xmlPath, String path) {
		return xmlPath.getInt(path);
	}

	public static void setConfigValue(String key, String value) throws ConfigurationException {
		Base.config.setDelimiterParsingDisabled(true);
		Base.config.setProperty(key, value);
		Base.config.save();
	}

}
