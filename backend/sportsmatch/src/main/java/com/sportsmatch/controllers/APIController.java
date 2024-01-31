package com.sportsmatch.controllers;

import com.sportsmatch.models.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class APIController {

  @GetMapping("/hello")
  public String hello(Authentication authentication) {
    User user = (User) authentication.getPrincipal();
    return "Welcome " + user.getName() + " to Secured Endpoint ";
  }
}
