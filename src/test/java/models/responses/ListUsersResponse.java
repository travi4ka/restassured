package models.responses;

import lombok.Data;

@Data
public class ListUsersResponse {
    Integer page, per_page, total, total_pages;
    UserResponse[] data;
    SupportResponse support;
}
