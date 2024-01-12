package com.beemer.fromis9server.introduction.controller;

import com.beemer.fromis9server.introduction.service.IntroductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fromis9")
public class IntroductionController {
    private final IntroductionService introductionService;

    @Autowired
    public IntroductionController(IntroductionService introductionService) {
        this.introductionService = introductionService;
    }

    @GetMapping("/introduction")
    public ResponseEntity<?> getIntroduction() {
        return ResponseEntity.ok(introductionService.getIntroduction());
    }
}