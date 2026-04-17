package com.ionix.domain.users.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResponse(
        Integer id,
        String name,
        String username,
        String email
) {}