package com.example.app1.entities;


import com.example.app1.enums.UserRoles;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    @Email
    String email;

    @NotNull
    @Builder.Default
    @Enumerated(EnumType.STRING)
    UserRoles role= UserRoles.USER;

    @NotBlank
    String password;

    @Builder.Default
    private Boolean enabled=true;

    @ManyToOne
    Candidate candidate;

}
