package com.sportsmatch.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDTO {

  @NotNull(message = "Username cannot be null.")
  private String userName;

  @Pattern(
      regexp = "\\d{2}-\\d{2}-\\d{4}",
      message = "Date of birth must be in the format dd-MM-yyyy")
  private String dateOfBirth;

  @NotNull(message = "Gender cannot be null.")
  private String gender;

  private List<SportDTO> sports = new ArrayList<>();
}
