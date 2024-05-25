package com.beemer.unofficial.fromis9.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {
    @Bean
    fun webClient(): WebClient {
        val exchangeStrategies = ExchangeStrategies.builder()
            .codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024) }
            .build()

        return WebClient.builder()
            .exchangeStrategies(exchangeStrategies)
            .build()
    }
}