package com.sportsmatch.services;


import com.sportsmatch.dtos.UserDTO;
import com.sportsmatch.dtos.UserInfoDTO;
import com.sportsmatch.models.User;

public interface UserService {

  User getUserFromContext();
  UserDTO getUserDTOFromContext();

  void updateUserInfo(UserInfoDTO userInfoDTO);
}
