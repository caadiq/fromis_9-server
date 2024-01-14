package com.beemer.fromis9server.appinfo.controller;

import com.beemer.fromis9server.appinfo.service.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fromis9")
public class AppInfoController {
    private final AppInfoService appInfoService;

    @Autowired
    public AppInfoController(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    @GetMapping("/appinfo")
    public ResponseEntity<?> getAppInfo() {
        return ResponseEntity.ok(appInfoService.getAppInfo());
    }
}
