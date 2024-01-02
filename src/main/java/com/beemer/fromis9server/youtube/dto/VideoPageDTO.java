package com.beemer.fromis9server.youtube.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoPageDTO {
    private Integer previousPage;
    private int currentPage;
    private Integer nextPage;
}