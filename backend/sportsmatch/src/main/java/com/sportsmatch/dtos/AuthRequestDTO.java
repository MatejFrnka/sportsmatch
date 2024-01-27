package com.sportsmatch.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthRequestDTO {

  private Long id;

  @NotNull(message = "Email address is required.")
  @Email(message = "Please provide a valid email address")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  @NotNull(message = "Please provide a password")
  private String password;

  private String name;
  private String gender;
  private String role;
  private String dateOfBirth;
}
