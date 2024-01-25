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
    private String status;
    private String token;


    private Long id;
    private String email;
    private String password;
    private String username;
    private String gender;
    private String role;
    private String dateOfBirth;



}
