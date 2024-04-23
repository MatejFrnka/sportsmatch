package com.sportsmatch.controllers;

import com.sportsmatch.dtos.UserInfoDTO;
import com.sportsmatch.models.Image;
import com.sportsmatch.services.UserService;
import com.sportsmatch.services.ValidationService;
import com.sportsmatch.util.ImageUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

  private final ValidationService validationService;
  private final UserService userService;

  @GetMapping("/get/{id}")
  public ResponseEntity<?> getUser(@PathVariable Long id) {
    try {
      return ResponseEntity.ok().body(userService.getUserById(id));
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }

  @PostMapping("/update")
  public ResponseEntity<?> updateInfo(
      @RequestBody @Valid UserInfoDTO userInfoDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(validationService.getAllErrors(bindingResult));
    }
    try {
      userService.updateUserInfo(userInfoDTO);
      return ResponseEntity.ok().build();
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }

  @PostMapping("/image")
  public ResponseEntity<Long> uploadProfileImage(@RequestParam("image") MultipartFile file) throws IOException {
    return ResponseEntity.status(HttpStatus.CREATED).body(userService.uploadProfileImage(file));
  }

  @DeleteMapping("/image/{id}")
  public void deleteProfileImage(@PathVariable Long id) {
    userService.deleteProfileImage(id);
  }

  @GetMapping("/image/{id}")
  public ResponseEntity<ByteArrayResource> downloadProfileImage(@PathVariable Long id) {
    Image image = userService.downloadProfileImage(id);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(image.getType()))
        .header(HttpHeaders.CONTENT_DISPOSITION,
            "image; filename=\"" + image.getName() + "\"")
        .body(new ByteArrayResource(ImageUtils.decompressImage(image.getImageData())));
  }
}
