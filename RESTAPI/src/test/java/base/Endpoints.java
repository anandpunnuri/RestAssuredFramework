package base;

public class Endpoints {

	/*Add resource parameters, query parameters seperately in the Request Specification object */
	public static final String INTEGRATIONTOKEN = "/auth/integration/authorize";
	public static final String USERAUTHTOKEN = "/auth/authorize";
	public static final String GETCONTENT = "/content";
	public static final String GETCONTENTBYDOCID = "/content/docId/{docId}";
	public static final String POSTCONTENT = "/content";
	public static final String POSTCONTENTIMPORT = "/content/import";
	public static final String POSTMEDIA = "/media";
}
