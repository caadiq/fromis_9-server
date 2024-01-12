package com.beemer.fromis9server.introduction.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BannerDTO {
    private int imageCount;
    private List<BannerImagesDTO> bannerImages;
}
