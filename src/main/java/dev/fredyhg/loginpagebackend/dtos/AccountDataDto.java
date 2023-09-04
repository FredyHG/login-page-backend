package dev.fredyhg.loginpagebackend.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AccountDataDto {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Column(name = "logradouro")
    private String logradouro;

    @Column(name = "state")
    private String state;

    @Column(name = "city")
    private String city;

}
