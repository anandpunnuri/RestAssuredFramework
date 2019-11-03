package base;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.RestUtilities;
import java.io.File;
import java.util.HashMap;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.json.simple.JSONObject;

public class Base {

	public static XMLConfiguration config;

	public static void setup() {
		try {
			config = new XMLConfiguration(new File("./src/test/resources/config/config.xml"));
		} catch (ConfigurationException ce) {
			System.out.println("Configuration exception in Base Class: " + ce.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static void generateAuthToken() {
		try
		{
		// Generate integration token and store it in config.xml
		RequestSpecification intreqSpec = RestUtilities.getRequestSpecification();
		intreqSpec.contentType("application/json");
		RestUtilities.setEndpoint(Endpoints.INTEGRATIONTOKEN);
		HashMap<String, String> hmint = new HashMap<String, String>();
		
//		{"siteName":\"config.getString("site_name")\","localeId":"config.getString("locale")"}"
//		
//		//kmauthtoken: {"siteName":"day103_20200_sql_95h","localeId":"en_US"}

		
		hmint.put("kmauthtoken", "{\"siteName\":\""+config.getString("site_name")+"\",\"localeId\":\""+config.getString("locale")+"\"}");
		RestUtilities.setRequestHeader(hmint, intreqSpec);
		
		JSONObject intpayload = new JSONObject();
		intpayload.put("login", Base.config.getString("apiusername"));
		intpayload.put("password", Base.config.getString("apiuserpwd"));
		intpayload.put("siteName", Base.config.getString("site_name"));
		
		Response intResp = RestUtilities.getResponse(MethodType.POST, intpayload.toJSONString());
//		System.out.println("inttoken resp: "+intResp.asString());
		String intToken = RestUtilities.getStringValuefromXMLResponse(RestUtilities.getXmlPath(intResp),
				"IntegrationAuthInfo.authenticationToken");
		System.out.println("inttoken: "+intToken);
		RestUtilities.setConfigValue("integrationToken", intToken);

		// Generate userauth token and store it in config.xml
		RestUtilities.setEndpoint(Endpoints.USERAUTHTOKEN);
		RequestSpecification userreqSpec = RestUtilities.getRequestSpecification();
		userreqSpec.contentType("application/x-www-form-urlencoded");
		HashMap<String, String> hmuser = new HashMap<String, String>();
		
		
		hmuser.put("kmauthtoken", "{\"siteName\":\""+config.getString("site_name")+"\",\"localeId\":\""+config.getString("locale")+"\",\"interfaceId\":\""+config.getString("interfaceID")+"\",\"integrationUserToken\":\""+config.getString("integrationToken")+"\"}");
		
//		hmuser.put("siteName", config.getString("site_name"));
//		hmuser.put("localeId", config.getString("locale"));
//		hmuser.put("interfaceId", config.getString("interfaceID"));
//		hmuser.put("integrationUserToken", config.getString("integrationToken"));
		RestUtilities.setRequestHeader(hmuser, userreqSpec);
		
		String userauthpayload = "userName="+Base.config.getString("accountusername")+"&password="+Base.config.getString("accountuserpwd")+"&siteName="+Base.config.getString("site_name")+"&userExternalType=ACCOUNT";
		Response userResp = RestUtilities.getResponse(MethodType.POST,userauthpayload);
		String initialUserToken = RestUtilities.getStringValuefromXMLResponse(RestUtilities.getXmlPath(userResp),
				"AuthInfo.authenticationToken");
		System.out.println("inttttt: "+initialUserToken);
		RestUtilities.setConfigValue("userauthtoken", initialUserToken);

		// Generate the final kmauthtoken
		String kmauthtoken = initialUserToken.replace("\"integrationUserToken\":null",
				"\"integrationUserToken\":\"" + config.getString("integrationToken")+"\"");
		System.out.println("kmauthtoken: "+kmauthtoken);
		RestUtilities.setConfigValue("kmauthtoken", kmauthtoken);
		}
		catch(ConfigurationException ce)
		{
			System.out.println("Configuration Exception while generating kmauthtoken: "+ce.getMessage());
		}
	}

}
