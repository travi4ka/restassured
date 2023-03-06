package models.requests;

import lombok.Data;

@Data
public class PersonPutRequest {
    String name, job;
}
