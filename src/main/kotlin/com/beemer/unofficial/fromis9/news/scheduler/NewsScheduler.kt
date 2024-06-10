package com.beemer.unofficial.fromis9.news.scheduler

import com.beemer.unofficial.fromis9.news.service.NewsService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NewsScheduler(private val newsService: NewsService) {

    @Scheduled(cron = "0 0,20,40 * * * *")
    fun fetchWeverseLive() {
        newsService.fetchWeverseLive()
    }
}