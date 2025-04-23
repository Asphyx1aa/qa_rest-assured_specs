package models;

import lombok.Data;

@Data
public class UserCreateResponse {
    String name, job, id, createdAt;
}
