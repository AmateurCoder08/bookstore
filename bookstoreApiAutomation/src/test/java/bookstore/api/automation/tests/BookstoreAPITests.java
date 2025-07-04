package bookstore.api.automation.tests;

import static bookstore.api.automation.constants.APIEndPoints.getBooksEndPoint;
import static bookstore.api.automation.constants.APIEndPoints.getHealthEndPoint;
import static bookstore.api.automation.constants.APIEndPoints.getLoginEndPoint;
import static bookstore.api.automation.constants.APIEndPoints.getSignupEndPoint;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import bookstore.api.automation.payloads.BookPayload;
import bookstore.api.automation.payloads.UserCredentialsPayload;
import bookstore.api.automation.utils.DataUtils;
import bookstore.api.automation.utils.RequestResponseUtils;
import io.restassured.response.Response;

public class BookstoreAPITests {
	
	private static UserCredentialsPayload payload;
	private static String accessToken;
	private static Integer bookId;
	private static Integer anotherBookId;
	
	@Test(priority = 1, description = "To verify if the Bookstore API is up and running")
	public void checkAPIHealth() {
		Response response = RequestResponseUtils.getRequest(getHealthEndPoint());
		
		response.then()
			.statusCode(200)
			.body("status", equalTo("up"));
	}
	
	@Test(priority = 2, description = "To verify user sign up with the Bookstore API")
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
	
	@Test(priority = 3, dependsOnMethods = "verifySignUp", description = "To verify user login with the Bookstore API")
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
	
	@Test(priority = 4, description = "To verify the Bookstore API behavior with invalid login credentials") 
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
	
	@Test(priority = 5, description = "To verify adding books by hitting the Bookstore API")
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
		
		bookId = response.jsonPath().getInt("id");
		
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
	
	@Test(priority = 6, dependsOnMethods = "verifyAddingBooks", description = "To verify all books retrieval with Bookstore API")
	public void verifyAllBooksRetrieval() {
		
		String bookName = "Mortal instruments";
		String author = "Cassandra Clare";
		Integer publishedYear = 2011;
		String bookSummary = "Demon hunters and stuff";
		
		BookPayload bookPayload = BookPayload.builder()
									.name(bookName)
									.author(author)
									.publishedYear(publishedYear)
									.bookSummary(bookSummary)
									.build();
		anotherBookId = RequestResponseUtils.postRequest(getBooksEndPoint(), bookPayload, accessToken).jsonPath().get("id");
		
		Response responseForGetRequest = RequestResponseUtils.getRequest(getBooksEndPoint(), accessToken);
		
		responseForGetRequest.then()
				.statusCode(200)
				.body("id", hasItems(bookId, anotherBookId));
	}
	
	@Test(priority = 7, dependsOnMethods = "verifyAddingBooks", description = "To verify book retrieval with Bookstore API")
	public void verifyBookRetrieval() {
		Response response = RequestResponseUtils.getRequest(getBooksEndPoint() + bookId, accessToken);
		
		response.then()
				.statusCode(200)
				.body("id", equalTo(bookId));
	}
	
	@Test(priority = 8, dependsOnMethods = "verifyAddingBooks", description = "To verify updating book details with Bookstore API")
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
									.id(bookId)
									.build();
		Response response = RequestResponseUtils.putRequest(getBooksEndPoint() + bookId, bookPayload, accessToken);
		
		response.then()
			.statusCode(200)
			.body(
					"id", equalTo(bookId),
					"name", equalTo(bookName),
					"author", equalTo(author),
					"published_year", equalTo(publishedYear),
					"book_summary", equalTo(bookSummary)
			);
	}
	
	@Test(priority = 9, dependsOnMethods = "verifyAllBooksRetrieval", description = "To verify book deletion with Bookstore API")
	public void verifyDeletingBooks() {
		Response response = RequestResponseUtils.deleteRequest(getBooksEndPoint() + bookId, accessToken);
		
		response.then()
				.statusCode(200)
				.body("message", equalToIgnoringCase("Book deleted successfully"));
		
		response = RequestResponseUtils.deleteRequest(getBooksEndPoint() + anotherBookId, accessToken);
		
		response.then()
			.statusCode(200)
			.body("message", equalToIgnoringCase("Book deleted successfully"));
	}
	
	@Test(priority = 10, dependsOnMethods = "verifyDeletingBooks", description = "To verify invalid book retrieval with Bookstore API") 
	public void verifyInvalidBookRetrieval() {
		Response response = RequestResponseUtils.getRequest(getBooksEndPoint() + bookId, accessToken);
		
		response.then()
				.statusCode(404)
				.body("detail", equalToIgnoringCase("Book not found"));		
	}

}
