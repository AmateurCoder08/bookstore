package bookstore.api.automation.tests;

import static bookstore.api.automation.constants.APIEndPoints.getBooksEndPoint;
import static bookstore.api.automation.constants.APIEndPoints.getHealthEndPoint;
import static bookstore.api.automation.constants.APIEndPoints.getLoginEndPoint;
import static bookstore.api.automation.constants.APIEndPoints.getSignupEndPoint;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import bookstore.api.automation.payloads.BookPayload;
import bookstore.api.automation.payloads.UserCredentialsPayload;
import bookstore.api.automation.utils.DataUtils;
import bookstore.api.automation.utils.RequestResponseUtils;
import io.restassured.response.Response;

public class UserAuthenticationTests {
	
	private static UserCredentialsPayload payload;
	private static String accessToken;
	private static Integer id;
	
	@Test(priority = 1, description = "")
	public void checkAPIHealth() {
		Response response = RequestResponseUtils.getRequest(getHealthEndPoint());
		response.then()
			.statusCode(200)
			.body("status", Matchers.equalTo("up"));
	}
	
	@Test(priority = 2)
	public void verifySignUp() {
		String email = DataUtils.generateRandomEmail();
		String password = DataUtils.generateRandomPassword();
		payload = UserCredentialsPayload.builder()
											.email(email)
											.password(password)
											.build();
		Response response = RequestResponseUtils.postRequest(getSignupEndPoint(), payload);
		response.then()
				.statusCode(200)
				.body("message", equalToIgnoringCase("User created successfully"));
	}
	
	@Test(priority = 3)
	public void verifyLogin() {
		Response response = RequestResponseUtils.postRequest(getLoginEndPoint(), payload);
		accessToken = response.jsonPath().get("access_token");
		response.then()
				.statusCode(200)
				.body(
						"token_type", equalTo("bearer"),
						"access_token", notNullValue()
				);
		
	}
	
	@Test(priority = 4) 
	public void verifyWithInvalidCredentials() {		
		String email = DataUtils.generateRandomEmail();
		String password = DataUtils.generateRandomPassword();
		payload = UserCredentialsPayload.builder()
											.email(email)
											.password(password)
											.build();
		Response response = RequestResponseUtils.postRequest(getLoginEndPoint(), payload);
		response.then()
				.statusCode(400)
				.body("detail", equalToIgnoringCase("Incorrect email or password"));
	}
	
	@Test(priority = 5)
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
		Response response = RequestResponseUtils.postRequest(getBooksEndPoint(), bookPayload, accessToken);
		id = response.jsonPath().getInt("id");
		response.then()
				.statusCode(200)
				.body(
						"id", notNullValue(),
						"name", equalTo(bookName),
						"author", equalTo(author),
						"published_year", equalTo(publishedYear),
						"book_summary", equalTo(bookSummary)
				);
	}
	
	@Test(priority = 6)
	public void verifyAllBooksRetrieval() {
		Response response = RequestResponseUtils.getRequest(getBooksEndPoint() + id, accessToken);
		response.then()
				.statusCode(200)
				.body("id", equalTo(id));
	}
	
	@Test(priority = 7)
	public void verifyBookRetrieval() {
		Response response = RequestResponseUtils.getRequest(getBooksEndPoint() + id, accessToken);
		response.then()
				.statusCode(200)
				.body("id", equalTo(id));
	}
	
	@Test(priority = 8)
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
		Response response = RequestResponseUtils.putRequest(getBooksEndPoint() + id, bookPayload, accessToken);
		response.then()
			.statusCode(200);		
	}
	
	@Test(priority = 9)
	public void verifyDeletingBooks() {
		Response response = RequestResponseUtils.deleteRequest(getBooksEndPoint() + id, accessToken);
		response.then()
				.statusCode(200)
				.body("message", equalToIgnoringCase("Book deleted successfully"));
	}

}
