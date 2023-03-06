package models.responses;

import lombok.Data;

@Data
public class UserResponse {
    Integer id;
    String email, first_name, last_name, avatar;
}
