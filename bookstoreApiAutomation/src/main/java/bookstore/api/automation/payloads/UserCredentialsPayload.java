package bookstore.api.automation.payloads;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCredentialsPayload {
	private String email;
	private String password;
}
