package com.example.demo.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @author ZhangJP
 */
@Alias("User")
@Data
public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private String phone;

    public User(String email) {
        this.email = email;
        this.id = 1;
    }

}
