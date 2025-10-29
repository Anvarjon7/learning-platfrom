package com.example.aiacademy.services;

import java.util.Set;

public interface UserAuthService {

    public String register(String email, String password, String fullName, Set<String> roleNames);

    public String login(String email, String password);
}
