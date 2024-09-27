package com.sportsmatch.services;

import com.sportsmatch.dtos.UserDTO;
import com.sportsmatch.dtos.UserInfoDTO;
import com.sportsmatch.models.Image;
import com.sportsmatch.models.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

  User getUserFromContext();

  UserDTO getUserDTOFromContext();

  void updateUserInfo(UserInfoDTO userInfoDTO);

  UserDTO getUserById(Long id);

  UserDTO getMyRank();

  Long uploadProfileImage(MultipartFile file) throws IOException;

  void deleteProfileImage(Long id);

  Image downloadProfileImage(Long id);

}
