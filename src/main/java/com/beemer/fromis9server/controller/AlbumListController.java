package com.beemer.fromis9server.controller;

import com.beemer.fromis9server.service.AlbumListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fromis_9")
public class AlbumListController {
    private final AlbumListService albumListService;

    @Autowired
    public AlbumListController(AlbumListService albumListService) {
        this.albumListService = albumListService;
    }

    @GetMapping("/albumlist")
    public ResponseEntity<?> getAlbumList(@RequestParam(required = false) String part) {
        if (part == null || part.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("'part' 파라미터가 필요합니다.");
        }
        return ResponseEntity.ok(albumListService.getAlbumList(part));
    }
}
