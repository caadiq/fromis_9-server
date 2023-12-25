package com.beemer.fromis9server.service;

import com.beemer.fromis9server.dto.AlbumArtDTO;
import com.beemer.fromis9server.dto.AlbumDescriptionDTO;
import com.beemer.fromis9server.dto.AlbumListDTO;
import com.beemer.fromis9server.model.AlbumList;
import com.beemer.fromis9server.repository.AlbumListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AlbumListService {
    private final AlbumListRepository albumListRepository;

    @Autowired
    public AlbumListService(AlbumListRepository albumListRepository) {
        this.albumListRepository = albumListRepository;
    }

    public List<AlbumListDTO> getAlbumList(String part) {
        Set<String> parts = Arrays.stream(part.split(","))
                .collect(Collectors.toSet());

        return albumListRepository.findAll().stream()
                .map(albumList -> convertToDTO(albumList, parts))
                .collect(Collectors.toList());
    }

    private AlbumListDTO convertToDTO(AlbumList albumList, Set<String> parts) {
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

        return albumListDTO;
    }
}
