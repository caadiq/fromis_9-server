package com.beemer.fromis9server.album.service;

import com.beemer.fromis9server.album.dto.*;
import com.beemer.fromis9server.album.model.AlbumList;
import com.beemer.fromis9server.album.model.Song;
import com.beemer.fromis9server.album.repository.AlbumListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AlbumListService {
    private final AlbumListRepository albumListRepository;

    @Autowired
    public AlbumListService(AlbumListRepository albumListRepository) {
        this.albumListRepository = albumListRepository;
    }

    public List<AlbumListDTO> getAlbumList(String part, Optional<String> albumName) {
        Set<String> parts = Arrays.stream(part.split(","))
                .collect(Collectors.toSet());

        List<AlbumList> albumLists;
        if (albumName.isPresent()) {
            // 특정 앨범 이름으로 조회
            albumLists = albumListRepository.findByAlbumName(albumName.get());
        } else {
            // 모든 앨범 조회
            albumLists = albumListRepository.findAll();
        }

        return albumLists.stream()
                .map(albumList -> convertAlbumListToDTO(albumList, parts))
                .collect(Collectors.toList());
    }

    private AlbumListDTO convertAlbumListToDTO(AlbumList albumList, Set<String> parts) {
        AlbumListDTO albumListDTO = new AlbumListDTO();

        if (parts.contains("album")) {
            albumListDTO.setAlbumName(albumList.getAlbumName());
            albumListDTO.setAlbumType(albumList.getAlbumType());
            albumListDTO.setReleaseDate(albumList.getReleaseDate());
            albumListDTO.setColorPrimary(albumList.getColorPrimary());
            albumListDTO.setColorSecondary(albumList.getColorSecondary());
        }

        if (parts.contains("albumArt")) {
            AlbumArtDTO albumArtDTO = new AlbumArtDTO();
            albumArtDTO.setAlbumName(albumList.getAlbumArt().getAlbumName());
            albumArtDTO.setImageUrl(albumList.getAlbumArt().getImageUrl());
            albumListDTO.setAlbumArt(albumArtDTO);
        }

        if (parts.contains("albumDescription")) {
            AlbumDescriptionDTO albumDescriptionDTO = new AlbumDescriptionDTO();
            albumDescriptionDTO.setAlbumName(albumList.getAlbumDescription().getAlbumName());
            albumDescriptionDTO.setDescription(albumList.getAlbumDescription().getDescription());
            albumListDTO.setAlbumDescription(albumDescriptionDTO);
        }

        if (parts.contains("songList")) {
            SongListDTO songListDTO = new SongListDTO();
            List<SongDTO> songDTOs = albumList.getSongList().stream()
                    .flatMap(songList -> songList.getSong().stream())
                    .map(this::convertSongToDTO)
                    .sorted(Comparator.comparingInt(SongDTO::getTrackNumber)) // 트랙 번호 순으로 정렬
                    .collect(Collectors.toList());
            songListDTO.setSongList(songDTOs);
            albumListDTO.setSongList(songListDTO);
        }

        return albumListDTO;
    }

    private SongDTO convertSongToDTO(Song song) {
        SongDTO songDTO = new SongDTO();
        songDTO.setSongName(song.getSongListId().getSongName());
        songDTO.setAlbumName(song.getSongListId().getAlbumName());
        songDTO.setLyricist(song.getLyricist());
        songDTO.setComposer(song.getComposer());
        songDTO.setArranger(song.getArranger());
        songDTO.setLyrics(song.getLyrics());
        songDTO.setSongLength(song.getSongLength());
        songDTO.setIsTitle(song.isTitle());
        songDTO.setTrackNumber(song.getTrackNumber());
        return songDTO;
    }
}
