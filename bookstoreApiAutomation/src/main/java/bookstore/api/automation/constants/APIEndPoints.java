package bookstore.api.automation.constants;

public class APIEndPoints {
	private static final String BASEURL = "http://127.0.0.1:8000";
    private static final String SIGNUP = BASEURL + "/signup";
    private static final String LOGIN = BASEURL + "/login";
    private static final String BOOKS = BASEURL + "/books/";
    private static final String HEALTH = BASEURL + "/health"; 
    
	public static String getSignupEndPoint() {
		return SIGNUP;
	}
	public static String getLoginEndPoint() {
		return LOGIN;
	}
	public static String getBooksEndPoint() {
		return BOOKS;
	}
	public static String getHealthEndPoint() {
		return HEALTH;
	}
}
