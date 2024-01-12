package com.beemer.fromis9server.introduction.Scheduler;

import com.beemer.fromis9server.introduction.service.IntroductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IntroductionScheduler {
    private final IntroductionService introductionService;

    @Autowired
    public IntroductionScheduler(IntroductionService introductionService) {
        this.introductionService = introductionService;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateMemberProfileImage() {
        introductionService.updateMemberProfileImage();
    }
}