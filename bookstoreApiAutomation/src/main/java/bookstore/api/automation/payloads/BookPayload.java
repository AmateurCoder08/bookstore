package bookstore.api.automation.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BookPayload {
	private Integer id;
	private String name;
	private String author;
	
	@JsonProperty("published_year")
	private int publishedYear;
	
	@JsonProperty("book_summary")
	private String bookSummary;
}
