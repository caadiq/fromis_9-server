package com.beemer.fromis9server.album.service;

import com.beemer.fromis9server.album.dto.*;
import com.beemer.fromis9server.album.model.AlbumList;
import com.beemer.fromis9server.album.model.Photo;
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

    public List<AlbumListDTO> getAlbumList(String part, Optional<String> albumName, Optional<String> songName) {
        Set<String> parts = Arrays.stream(part.split(",")).collect(Collectors.toSet());
        List<AlbumList> albumLists;

        if (albumName.isPresent() && songName.isPresent()) {
            albumLists = albumListRepository.findByAlbumNameAndSongName(albumName.get(), songName.get()); // 노래 및 앨범 이름에 해당하는 값
        } else if (albumName.isPresent()) {
            albumLists = albumListRepository.findByAlbumName(albumName.get()); // 앨범 이름에 해당하는 값
        } else {
            albumLists = albumListRepository.findAll(); // 모든 앨범
        }

        return albumLists.stream()
                .map(albumList -> convertAlbumListToDTO(albumList, parts, songName))
                .collect(Collectors.toList());
    }

    private AlbumListDTO convertAlbumListToDTO(AlbumList albumList, Set<String> parts, Optional<String> songName) {
        AlbumListDTO albumListDTO = new AlbumListDTO();

        if (parts.contains("album")) {
            albumListDTO.setAlbumName(albumList.getAlbumName());
            albumListDTO.setAlbumType(albumList.getAlbumType());
            albumListDTO.setColorMain(albumList.getColorMain());
            albumListDTO.setReleaseDate(albumList.getReleaseDate());
            albumListDTO.setColorPrimary(albumList.getColorPrimary());
            albumListDTO.setColorSecondary(albumList.getColorSecondary());
        }

        if (parts.contains("art")) {
            AlbumArtDTO albumArtDTO = new AlbumArtDTO();
            albumArtDTO.setAlbumName(albumList.getAlbumArt().getAlbumName());
            albumArtDTO.setImageUrl(albumList.getAlbumArt().getImageUrl());
            albumListDTO.setAlbumArt(albumArtDTO);
        }

        if (parts.contains("description")) {
            AlbumDescriptionDTO albumDescriptionDTO = new AlbumDescriptionDTO();
            albumDescriptionDTO.setAlbumName(albumList.getAlbumDescription().getAlbumName());
            albumDescriptionDTO.setDescription(albumList.getAlbumDescription().getDescription());
            albumListDTO.setAlbumDescription(albumDescriptionDTO);
        }

        if (parts.contains("tracklist")) {
            TrackListDTO trackListDTO = new TrackListDTO();
            List<SongDTO> songDTO;

            songDTO = songName.map(s -> albumList.getTrackList().stream()
                    .flatMap(trackList -> trackList.getSong().stream())
                    .filter(song -> song.getTrackListId().getSongName().equals(s))
                    .map(this::convertSongToDTO)
                    .collect(Collectors.toList())).orElseGet(() -> albumList.getTrackList().stream()
                    .flatMap(trackList -> trackList.getSong().stream())
                    .map(this::convertSongToDTO)
                    .sorted(Comparator.comparingInt(SongDTO::getTrackNumber))
                    .collect(Collectors.toList()));

            trackListDTO.setSong(songDTO);
            albumListDTO.setTrackList(trackListDTO);
        }

        if (parts.contains("photolist")) {
            PhotoListDTO photoListDTO = new PhotoListDTO();
            List<PhotoDTO> photoDTO;

            photoDTO = albumList.getPhotoList().stream()
                    .flatMap(photoList -> photoList.getPhoto().stream())
                    .map(this::convertPhotoToDTO)
                    .collect(Collectors.toList());

            photoListDTO.setPhoto(photoDTO);
            albumListDTO.setPhotoList(photoListDTO);
        }

        return albumListDTO;
    }

    private SongDTO convertSongToDTO(Song song) {
        SongDTO songDTO = new SongDTO();
        songDTO.setSongName(song.getTrackListId().getSongName());
        songDTO.setAlbumName(song.getTrackListId().getAlbumName());
        songDTO.setLyricist(song.getLyricist());
        songDTO.setComposer(song.getComposer());
        songDTO.setArranger(song.getArranger());
        songDTO.setLyrics(song.getLyrics());
        songDTO.setSongLength(song.getSongLength());
        songDTO.setTitleTrack(song.isTitleTrack());
        songDTO.setTrackNumber(song.getTrackNumber());
        return songDTO;
    }

    private PhotoDTO convertPhotoToDTO(Photo photo) {
        PhotoDTO photoDTO = new PhotoDTO();
        photoDTO.setId(photo.getPhotoListId().getId());
        photoDTO.setAlbumName(photo.getPhotoList().getPhotoListId().getAlbumName());
        photoDTO.setConcept(photo.getConcept());
        photoDTO.setImageUrl(photo.getImageUrl());
        return photoDTO;
    }
}
