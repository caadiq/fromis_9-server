package com.beemer.fromis9server.service;

import com.beemer.fromis9server.dto.AlbumArtDto;
import com.beemer.fromis9server.dto.AlbumDto;
import com.beemer.fromis9server.model.Album;
import com.beemer.fromis9server.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    public List<AlbumDto> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albums.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private AlbumDto convertToDto(Album album) {
        AlbumDto dto = new AlbumDto();
        dto.setId(album.getId());
        dto.setDate(album.getDate());
        dto.setTilte(album.getTitle());
        dto.setClassify(album.getClassify());
        dto.setColorPrimary(album.getColorPrimary());
        dto.setColorSecondary(album.getColorSecondary());
        if (album.getAlbumArt() != null) {
            AlbumArtDto albumArtDto = new AlbumArtDto();
            albumArtDto.setAlbum(album.getAlbumArt().getAlbum());
            albumArtDto.setImageUrl(album.getAlbumArt().getImageUrl());
            dto.setAlbumArt(albumArtDto);
        }
        return dto;
    }
}