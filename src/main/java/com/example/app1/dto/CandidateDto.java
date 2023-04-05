package com.example.app1.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level= AccessLevel.PRIVATE)
public class CandidateDto {
    Long id;
    String firstName;
    String lastName;
    String image;
    Boolean winner;
    Integer votes;
}
