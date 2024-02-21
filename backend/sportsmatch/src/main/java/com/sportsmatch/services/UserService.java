package com.sportsmatch.services;

import com.sportsmatch.dtos.UserInfoDTO;
import com.sportsmatch.models.User;

public interface UserService {

  User getUserFromTheSecurityContextHolder();

  void updateUserInfo(UserInfoDTO userInfoDTO);
}
