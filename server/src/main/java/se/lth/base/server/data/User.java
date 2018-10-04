package se.lth.base.server.data;

import java.security.Principal;

public class User implements Principal {

    public static User NONE = new User(0, "-", Role.NONE, "-");

    private final int id;
    private final Role role;
    private final String username;
    private final String email;

    public User(int id, String email, Role role, String username) {
        this.id = id;
        this.role = role;
        this.username = username;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
