package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostAndGetResponseData {
    private String email;
    private String password;
    private String token;
    private String name;
    private String job;
    private int id;
    private String createdAt;
    private int userId;;
    private String title;
    private String body;
}
