package models;

import lombok.Data;

@Data
public class CreateUserResponse {
    public String name;
    public String job;
    public Integer id;
    public String createdAt;
}
