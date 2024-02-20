package com.sportsmatch.services;


import com.sportsmatch.models.User;

public interface UserService {

  User getUserFromTheSecurityContextHolder();
}
