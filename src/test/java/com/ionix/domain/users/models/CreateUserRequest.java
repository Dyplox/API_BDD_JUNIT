package com.ionix.domain.users.models;

public record CreateUserRequest(
        String name,
        String username,
        String email
) {}