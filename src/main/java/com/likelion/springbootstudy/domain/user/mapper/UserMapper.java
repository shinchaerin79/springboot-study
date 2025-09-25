package com.likelion.springbootstudy.domain.user.mapper;

import org.springframework.stereotype.Component;

import com.likelion.springbootstudy.domain.user.dto.response.SignUpResponse;
import com.likelion.springbootstudy.domain.user.entity.User;

@Component
public class UserMapper {

  public SignUpResponse toSignUpResponse(User user) {
    return SignUpResponse.builder().userId(user.getId()).username(user.getUsername()).build();
  }
}
