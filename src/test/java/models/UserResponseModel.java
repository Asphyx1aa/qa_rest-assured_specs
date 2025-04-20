package models;

import lombok.Data;

@Data
public class UserResponseModel {
    Object data, support;
    String id, name, year, color, pantone_value;
}
