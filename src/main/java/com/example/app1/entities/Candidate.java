package com.example.app1.entities;

import com.example.app1.enums.CandidateStatus;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "candidates")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String image;

    @NotNull
    @Builder.Default
    @Enumerated(EnumType.STRING)
    CandidateStatus status=CandidateStatus.ACTIVE;

    @Builder.Default
    Boolean winner=false;
}
