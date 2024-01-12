package com.beemer.fromis9server.introduction.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IntroductionDTO {
    private List<BannerImagesDTO> bannerImages;
    private DebutDateDTO debutDate;
    private List<MembersDTO> members;
}