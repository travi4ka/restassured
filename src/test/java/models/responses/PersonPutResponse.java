package models.responses;

import lombok.Data;

@Data
public class PersonPutResponse {
    String name, job, updatedAt;
}
