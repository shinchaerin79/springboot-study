package com.likelion.springbootstudy.domain.auth.mapper;

import org.springframework.stereotype.Component;

import com.likelion.springbootstudy.domain.auth.dto.response.LoginResponse;
import com.likelion.springbootstudy.domain.user.entity.Role;
import com.likelion.springbootstudy.domain.user.entity.User;

@Component
public class AuthMapper {

  public LoginResponse toLoginResponse(User user, String accessToken, Long expirationTime) {
    return LoginResponse.builder()
        .accessToken(accessToken)
        .userId(user.getId())
        .username(user.getUsername())
        .role(user.getRole())
        .expirationTime(expirationTime)
        .build();
  }

  public static User fromOAuth(String email, String provider) {
    return User.builder().username(email).provider(provider).role(Role.USER).build();
  }
}
