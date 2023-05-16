package models;

import lombok.Data;

@Data
public class CreateUserBody {
    private String name, job;
}
