package com.sparta.lck_news.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeactivateRequestDto {

  private String password;

  public void setPassword(String currentPassword) {

  }
}
