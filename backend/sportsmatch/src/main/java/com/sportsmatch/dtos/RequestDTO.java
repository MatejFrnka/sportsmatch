package com.sportsmatch.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDTO {

    // register & login response
    // todo rename this class AuthRequestDTO
    // todo Create new class for status and token "AuthResponseDTO"
    private String status;
    private String token;


    // todo use spring validation @Email @NotNull
    private Long id;
    private String email;
    private String password;
    private String username;
    private String gender;
    private String role;
    private String dateOfBirth;



}
