package models;

import lombok.Data;

@Data
public class UserResponse {
    Object data, support;
    String id, name, year, color, pantone_value;
}
