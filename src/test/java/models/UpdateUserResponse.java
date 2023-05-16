package models;

import lombok.Data;

@Data
public class UpdateUserResponse {
    public String name;
    public String job;
    public String updatedAt;
}
