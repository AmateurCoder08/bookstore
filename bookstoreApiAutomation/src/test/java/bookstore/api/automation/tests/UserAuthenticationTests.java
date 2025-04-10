package bookstore.api.automation.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import bookstore.api.automation.constants.APIEndPoints;
import bookstore.api.automation.payloads.BookPayload;
import bookstore.api.automation.payloads.UserCredentialsPayload;
import bookstore.api.automation.utils.RequestResponseUtils;
import io.restassured.response.Response;

public class UserAuthenticationTests {
	
	private static UserCredentialsPayload payload;
	private static String accessToken;
	private static Integer id;
	
	@Test(priority = 1, description = "")
	public void checkAPIHealth() {
		Response response = RequestResponseUtils.getRequest(APIEndPoints.getHealthEndPoint());
		response.then()
			.statusCode(200)
			.body("status", Matchers.equalTo("up"));
	}
	
	@Test(priority = 2)
	public void verifySignUp() {
		String email = "randomEmail" + System.currentTimeMillis() + "@email.com";
		String password = "password";
		payload = UserCredentialsPayload.builder()
											.email(email)
											.password(password)
											.build();
		Response response = RequestResponseUtils.postRequest(APIEndPoints.getSignupEndPoint(), payload);
		response.then()
				.statusCode(200)
				.body("message", Matchers.equalToIgnoringCase("User created successfully"));
	}
	
	@Test(priority = 3)
	public void verifyLogin() {
		Response response = RequestResponseUtils.postRequest(APIEndPoints.getLoginEndPoint(), payload);
		accessToken = response.jsonPath().get("access_token");
		response.then()
				.statusCode(200)
				.body(
						"token_type",Matchers.equalTo("bearer"),
						"access_token", Matchers.notNullValue()
				);
		
	}
	
	@Test(priority = 4)
	public void verifyAddingBooks() {
		String bookName = "Harry Potter";
		String author = "JK Rowling";
		Integer publishedYear = 1997;
		String bookSummary = "Magic and wizards";
		BookPayload bookPayload = BookPayload.builder()
									.name(bookName)
									.author(author)
									.publishedYear(publishedYear)
									.bookSummary(bookSummary)
									.build();
		Response response = RequestResponseUtils.postRequest(APIEndPoints.getBooksEndPoint(), bookPayload, accessToken);
		id = response.jsonPath().getInt("id");
		response.then()
				.statusCode(200)
				.body(
						"id", Matchers.notNullValue(),
						"name", Matchers.equalTo(bookName),
						"author", Matchers.equalTo(author),
						"published_year", Matchers.equalTo(publishedYear),
						"book_summary", Matchers.equalTo(bookSummary)
				);
	}
	
	@Test(priority = 5)
	public void verifyAllBooksRetrieval() {
		Response response = RequestResponseUtils.getRequest(APIEndPoints.getBooksEndPoint() + id, accessToken);
		response.then()
				.statusCode(200)
				.body("id", Matchers.equalTo(id));
	}
	
	@Test(priority = 6)
	public void verifyBookRetrieval() {
		Response response = RequestResponseUtils.getRequest(APIEndPoints.getBooksEndPoint() + id, accessToken);
		response.then()
				.statusCode(200)
				.body("id", Matchers.equalTo(id));
	}
	
	@Test(priority = 7)
	public void verifyUpdatingBookDetails() {
		String bookName = "Harry Potter";
		String author = "JK Rowling";
		Integer publishedYear = 1997;
		String bookSummary = "Harry potter, magic and wizards";
		BookPayload bookPayload = BookPayload.builder()
									.name(bookName)
									.author(author)
									.publishedYear(publishedYear)
									.bookSummary(bookSummary)
									.id(id)
									.build();
		Response response = RequestResponseUtils.putRequest(APIEndPoints.getBooksEndPoint() + id, bookPayload, accessToken);
		response.then()
			.statusCode(200);		
	}
	
	@Test(priority = 8)
	public void verifyDeletingBooks() {
		Response response = RequestResponseUtils.deleteRequest(APIEndPoints.getBooksEndPoint() + id, accessToken);
		response.then()
				.statusCode(200)
				.body("message", Matchers.equalToIgnoringCase("Book deleted successfully"));
	}

}
