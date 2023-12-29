package com.beemer.fromis9server.album.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongDTO {
    private String songName;
    private String albumName;
    private String lyricist;
    private String composer;
    private String arranger;
    private String lyrics;
    private String songLength;
    private boolean titleTrack;
    private int trackNumber;
}
