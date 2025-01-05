package com.example.shroomsmanager.data;

public class Profile {

    private final String email;
    private final String password;

    public Profile(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
