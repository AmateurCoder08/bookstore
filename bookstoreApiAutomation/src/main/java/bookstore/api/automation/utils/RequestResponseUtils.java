package bookstore.api.automation.utils;

import static io.restassured.RestAssured.given;

import bookstore.api.automation.reports.ReportLogging;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RequestResponseUtils {
	
	public static void logTheResponse(Response response){
		response.then().log().all();
		ReportLogging.info("Response Received: " + response.getBody().asPrettyString());
	}
	
	public static Response getRequest(String endPoint) {
		Response response = given()
								.log().all()
							.when()
								.get(endPoint);
		
		logTheResponse(response);
		
		return response;				
	}
	
	public static Response getRequest(String endPoint, String accessToken) {
		Response response = given()
								.header("Authorization", "Bearer " + accessToken)
								.log().all()
							.when()
								.get(endPoint);
		
		logTheResponse(response);
		
		return response;				
	}
	
	public static Response postRequest(String endPoint, Object payload) {
		ReportLogging.info(payload.toString());
		Response response = given()
								.contentType(ContentType.JSON)
								.body(payload)
								.log().all()
							.when()
								.post(endPoint);
		
		logTheResponse(response);
		
		return response;				
	}
	
	public static Response postRequest(String endPoint, Object payload, String accessToken) {
		ReportLogging.info(payload.toString());
		Response response = given()
								.header("Authorization", "Bearer " + accessToken)
								.contentType(ContentType.JSON)
								.body(payload)
								.log().all()
							.when()
								.post(endPoint);
		
		logTheResponse(response);
		
		return response;				
	}
	
	public static Response deleteRequest(String endPoint, String accessToken) {
		Response response = given()
								.header("Authorization", "Bearer " + accessToken)
								.log().all()
							.when()
								.delete(endPoint);

		logTheResponse(response);

		return response;
		
	}
	
	public static Response putRequest(String endPoint, Object payload, String accessToken) {
		Response response = given()
								.header("Authorization", "Bearer " + accessToken)
								.contentType(ContentType.JSON)
								.body(payload)
								.log().all()
							.when()
								.put(endPoint);

		logTheResponse(response);

		return response;		
	}

}
