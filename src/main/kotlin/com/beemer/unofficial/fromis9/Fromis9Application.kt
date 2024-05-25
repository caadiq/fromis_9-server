package com.beemer.unofficial.fromis9

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class Fromis9Application

fun main(args: Array<String>) {
	runApplication<Fromis9Application>(*args)
}
