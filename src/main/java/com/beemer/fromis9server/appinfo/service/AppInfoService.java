package com.beemer.fromis9server.appinfo.service;

import com.beemer.fromis9server.appinfo.dto.AppInfoDTO;
import com.beemer.fromis9server.appinfo.model.AppInfo;
import com.beemer.fromis9server.appinfo.repository.AppInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppInfoService {
    private final AppInfoRepository appInfoRepository;

    @Autowired
    public AppInfoService(AppInfoRepository appInfoRepository) {
        this.appInfoRepository = appInfoRepository;
    }

    public List<AppInfoDTO> getAppInfo() {
        List<AppInfo> appInfos = appInfoRepository.findAll();

        return appInfos.stream()
                .sorted(Comparator.comparing(AppInfo::getRelease).reversed())
                .map(appInfo -> {
            AppInfoDTO appInfoDTO = new AppInfoDTO();
            appInfoDTO.setVersion(appInfo.getVersion());
            appInfoDTO.setRelease(appInfo.getRelease());
            appInfoDTO.setChangelog(appInfo.getChangelog());
            return appInfoDTO;
        }).collect(Collectors.toList());
    }
}
