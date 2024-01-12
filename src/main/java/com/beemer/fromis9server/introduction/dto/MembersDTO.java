package com.beemer.fromis9server.introduction.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MembersDTO {
    private String name;
    private String imageUrl;
    private LocalDate birth;
    private String position;
    private String instagram;
}
