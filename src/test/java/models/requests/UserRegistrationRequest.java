package models.requests;

import lombok.Data;

@Data
public class UserRegistrationRequest {
    String email, password;
}
