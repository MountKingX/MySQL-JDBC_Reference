package com.kangmin.app.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private UserRole userRole;
    private String nickName;

    public User() {
        super();
    }

    public User(
        final String username,
        final String password,
        final UserRole userRole,
        final String nickName
    ) {
        super();
        this.password = password;
        this.username = username;
        this.userRole = userRole;
        this.nickName = nickName;
    }

    public User(
        final String username,
        final String password,
        final UserRole userRole
    ) {
        this(username, password, userRole, "Unknown");
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", userRole=" + userRole +
            ", nickName='" + nickName + '\'' +
            '}';
    }
}
