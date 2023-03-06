package models.responses;

import lombok.Data;

@Data
public class SingleUserResponse {
    UserResponse data;
    SupportResponse support;
}
