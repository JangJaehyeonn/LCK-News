package com.sparta.lck_news.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileRequestDto {

  private String introduction;
  private String name;
  private String email;
  private String password;    // 현재 비밀번호
  private Boolean changeChecked;
  private String newPassword;    // 변경할 비밀번호
  private String username;
  public ProfileRequestDto(String introduction, String name, String email, String password, Boolean changeChecked, String newPassword) {
  }
}

