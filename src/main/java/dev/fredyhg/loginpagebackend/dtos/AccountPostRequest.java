package dev.fredyhg.loginpagebackend.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountPostRequest {

    @Size(min = 2, message = "Name size too short")
    @Size(max = 20, message = "Name size too long ")
    @NotBlank
    private String name;

    @Size(min = 2, message = "Surname size too short")
    @Size(max = 20, message = "Surname size too long")
    @NotBlank
    private String surname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Size(min = 2, message = "Logradouro size too short")
    @Size(max = 255, message = "Logradouro size too long ")
    @NotBlank
    private String logradouro;

    @Size(min = 2, message = "State size too short")
    @Size(max = 30, message = "State size too long ")
    @NotBlank
    private String state;

    @Size(min = 2, message = "City size too short")
    @Size(max = 50, message = "City size too long ")
    @NotBlank
    private String city;
}
